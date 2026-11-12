package com.luddev.api.dto;

public record DepartmentResponseDto(
    Long id,
    String type,
    DepartmentHeadDto chefDepartement
) {
}
