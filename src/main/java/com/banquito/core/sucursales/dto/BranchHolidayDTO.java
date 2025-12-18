package com.banquito.core.sucursales.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor; 
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor; 

@Data
@Builder
@NoArgsConstructor 
@AllArgsConstructor 
public class BranchHolidayDTO {

    @NotNull
    @Schema(description = "Fecha del feriado", example = "2025-12-25")
    private LocalDate date;

    @NotBlank
    @Schema(description = "Nombre del feriado", example = "Navidad")
    private String name;
}