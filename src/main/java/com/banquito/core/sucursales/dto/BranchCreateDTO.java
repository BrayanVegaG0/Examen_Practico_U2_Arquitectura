package com.banquito.core.sucursales.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor; 

@Data
@Builder
@NoArgsConstructor 
@AllArgsConstructor
public class BranchCreateDTO {

    @NotBlank
    @Email
    @Schema(description = "Correo electrónico de la sucursal", example = "centro@banquito.com")
    private String emailAddress;

    @NotBlank
    @Schema(description = "Nombre comercial de la sucursal", example = "Agencia Centro")
    private String name;

    @NotBlank
    @Schema(description = "Teléfono de contacto", example = "022555666")
    private String phoneNumber;
}