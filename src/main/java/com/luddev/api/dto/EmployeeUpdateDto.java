package com.luddev.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeUpdateDto {

    @NotBlank(message = "Le prénom ne peut pas être vide")
    private String firstname;

    @NotBlank(message = "Le nom ne peut pas être vide")
    private String lastname;

    @NotBlank(message = "L'email ne peut pas être vide")
    @Email(message = "L'email doit être valide")
    private String mail;

    private String position;

    @PositiveOrZero(message = "Le salaire ne peut pas être négatif")
    private double salary;

    @NotBlank(message = "Le mot de passe ne peut pas être vide")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
    private String password;
}