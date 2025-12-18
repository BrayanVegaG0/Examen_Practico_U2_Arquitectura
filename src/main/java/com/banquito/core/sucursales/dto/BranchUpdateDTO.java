package com.banquito.core.sucursales.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BranchUpdateDTO {

    @NotBlank
    @Schema(description = "Nuevo número de teléfono", example = "0991234567")
    private String phoneNumber;
}