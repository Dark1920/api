package com.luddev.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DepartmentRequestDto(
    @NotBlank(message = "Le type de département ne peut pas être vide")
    String type,

    @NotNull(message = "L'identifiant du chef de département est obligatoire")
    @Positive(message = "L'identifiant du chef de département doit être positif")
    Long chefDepartementId
) {
}
