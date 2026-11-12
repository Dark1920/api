package com.luddev.api.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.luddev.api.dto.EmployeePatchDto;
import com.luddev.api.dto.EmployeeRequestDto;
import com.luddev.api.dto.EmployeeResponseDto;
import com.luddev.api.dto.EmployeeUpdateDto;
import com.luddev.api.models.Employee;

@Component
public class EmployeeMapper {

    private final PasswordEncoder passwordEncoder;

    EmployeeMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    // DTO -> Entité (création)
    public Employee toEntity(EmployeeRequestDto dto) {
        Employee employee = new Employee();
        employee.setFirstname(dto.getFirstname());
        employee.setLastname(dto.getLastname());
        employee.setMail(dto.getMail());
        employee.setPosition(dto.getPosition());
        employee.setSalary(dto.getSalary());
        employee.setPassword(dto.getPassword());
        return employee;
    }

    // Entité -> DTO de réponse
    public EmployeeResponseDto toDTO(Employee employee) {
        return new EmployeeResponseDto(
            employee.getId(),
            employee.getFirstname(),
            employee.getLastname(),
            employee.getMail(),
            employee.getPosition(),
            employee.getSalary()
        );
    }

    // Applique un PUT sur une entité existante
    public void updateEntity(Employee employee, EmployeeUpdateDto dto) {
        employee.setFirstname(dto.getFirstname());
        employee.setLastname(dto.getLastname());
        employee.setMail(dto.getMail());
        employee.setPosition(dto.getPosition());
        employee.setSalary(dto.getSalary());
        employee.setPassword(dto.getPassword());
    }

    // Applique un PATCH : seuls les champs non null sont modifiés
    public void patchEntity(Employee employee, EmployeePatchDto dto) {
        if (dto.getFirstname() != null) employee.setFirstname(dto.getFirstname());
        if (dto.getLastname() != null)  employee.setLastname(dto.getLastname());
        if (dto.getMail() != null)     employee.setMail(dto.getMail());
        if (dto.getPosition() != null)  employee.setPosition(dto.getPosition());
        if (dto.getSalary() != null) employee.setSalary(dto.getSalary());
        if (dto.getPassword() != null)  employee.setPassword(dto.getPassword());
    }
}