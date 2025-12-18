package com.banquito.core.sucursales.dto;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BranchResponseDTO {

    @Schema(description = "ID único de la sucursal")
    private String id;

    @Schema(description = "Correo electrónico")
    private String emailAddress;

    @Schema(description = "Nombre de la sucursal")
    private String name;

    @Schema(description = "Teléfono")
    private String phoneNumber;

    @Schema(description = "Estado actual")
    private String state;

    @Schema(description = "Fecha de creación")
    private LocalDateTime creationDate;

    @Schema(description = "Fecha de última modificación")
    private LocalDateTime lastModifiedDate;

    @Schema(description = "Lista de feriados asociados")
    private List<BranchHolidayDTO> branchHolidays;
}