package com.banquito.core.sucursales.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.banquito.core.sucursales.dto.BranchCreateDTO;
import com.banquito.core.sucursales.dto.BranchHolidayDTO;
import com.banquito.core.sucursales.dto.BranchResponseDTO;
import com.banquito.core.sucursales.dto.BranchUpdateDTO;
import com.banquito.core.sucursales.service.BranchService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/branches")
@RequiredArgsConstructor
@Tag(name = "Branch Management", description = "API para gestionar sucursales y sus feriados")
public class BranchController {

    private final BranchService branchService;

    @Operation(summary = "Listar todas las sucursales", description = "Retorna un listado completo de sucursales registradas")
    @ApiResponse(responseCode = "200", description = "Listado obtenido exitosamente")
    @GetMapping
    public ResponseEntity<List<BranchResponseDTO>> getAllBranches() {
        log.info("REST request to get all branches");
        return ResponseEntity.ok(branchService.getAllBranches());
    }

    @Operation(summary = "Crear nueva sucursal", description = "Crea una sucursal con estado ACTIVE y sin feriados iniciales")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucursal creada"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o email duplicado")
    })
    @PostMapping
    public ResponseEntity<BranchResponseDTO> createBranch(@Valid @RequestBody BranchCreateDTO dto) {
        log.info("REST request to create branch with email: {}", dto.getEmailAddress());
        return ResponseEntity.ok(branchService.createBranch(dto));
    }

    @Operation(summary = "Obtener sucursal por ID", description = "Busca una sucursal específica por su identificador único")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucursal encontrada"),
        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BranchResponseDTO> getBranchById(@PathVariable String id) {
        log.info("REST request to get branch with ID: {}", id);
        return ResponseEntity.ok(branchService.getBranchById(id));
    }

    @Operation(summary = "Actualizar teléfono de sucursal", description = "Permite modificar únicamente el número de teléfono")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucursal actualizada"),
        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<BranchResponseDTO> updateBranch(
            @PathVariable String id, 
            @Valid @RequestBody BranchUpdateDTO dto) {
        log.info("REST request to update branch ID: {}", id);
        return ResponseEntity.ok(branchService.updateBranch(id, dto));
    }

    @Operation(summary = "Agregar feriado", description = "Añade un nuevo feriado a la lista de la sucursal")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Feriado agregado"),
        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada"),
        @ApiResponse(responseCode = "400", description = "El feriado ya existe para esa fecha")
    })
    @PostMapping("/{id}/holidays")
    public ResponseEntity<Void> addHoliday(
            @PathVariable String id, 
            @Valid @RequestBody BranchHolidayDTO holidayDTO) {
        log.info("REST request to add holiday to branch ID: {}", id);
        branchService.addHoliday(id, holidayDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Eliminar feriado", description = "Elimina un feriado basado en su fecha")
    @DeleteMapping("/{id}/holidays")
    public ResponseEntity<Void> removeHoliday(
            @PathVariable String id,
            @RequestParam LocalDate date) {
        log.info("REST request to remove holiday date: {} from branch ID: {}", date, id);
        branchService.removeHoliday(id, date);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Listar feriados", description = "Obtiene la lista de feriados de una sucursal específica")
    @GetMapping("/{id}/holidays")
    public ResponseEntity<List<BranchHolidayDTO>> getHolidays(@PathVariable String id) {
        log.info("REST request to get holidays for branch ID: {}", id);
        return ResponseEntity.ok(branchService.getHolidays(id));
    }

    @Operation(summary = "Verificar si es feriado", description = "Devuelve true si la fecha dada es feriado en la sucursal")
    @GetMapping("/{id}/isHoliday")
    public ResponseEntity<Boolean> isHoliday(
            @PathVariable String id, 
            @RequestParam LocalDate date) {
        log.info("REST request to check holiday date: {} for branch ID: {}", date, id);
        return ResponseEntity.ok(branchService.isHoliday(id, date));
    }
}