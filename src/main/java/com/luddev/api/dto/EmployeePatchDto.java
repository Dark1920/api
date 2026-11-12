package com.luddev.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeePatchDto {

    private String firstname;

    private String lastname;

    @Email(message = "L'email doit être valide")
    private String mail;

    private String position;

    @PositiveOrZero(message = "Le salaire ne peut pas être négatif")
    private Double salary;

    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
    private String password;
}