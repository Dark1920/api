package com.luddev.api.dto;

public record EmployeeResponseDto(
    Long id,
    String firstname,
    String lastname,
    String mail,
    String position,
    Double salary
) {
}