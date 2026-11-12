package com.luddev.api.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.luddev.api.dto.EmployeePatchDto;
import com.luddev.api.dto.EmployeeRequestDto;
import com.luddev.api.dto.EmployeeResponseDto;
import com.luddev.api.dto.EmployeeUpdateDto;
import com.luddev.api.services.EmployeeService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authorization.method.AuthorizeReturnObject;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("api/employees")
public class EmployeeController {   // majuscule pour respecter la convention Java

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/health")
    public String health() {
        return "ok";
    }

    @PostMapping
    @ResponseStatus (HttpStatus.CREATED)
    public EmployeeResponseDto create(@Valid @RequestBody EmployeeRequestDto dto) {
        return employeeService.createEmployee(dto);
    }

    @GetMapping("/{id}")
    public EmployeeResponseDto getById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }
    


    @GetMapping
    public List<EmployeeResponseDto> getAll() {
        return employeeService.getAllEmployees();
    }

    @PutMapping("/{id}")
    public EmployeeResponseDto update(@PathVariable Long id,
                                     @Valid @RequestBody EmployeeUpdateDto dto) {
        return employeeService.updateEmployee(id, dto);
    }

    @PatchMapping ("/{id}")
    public EmployeeResponseDto patch(@PathVariable Long id,
                                    @Valid @RequestBody EmployeePatchDto dto) {
        return employeeService.patchEmployee(id, dto);
    }
    // @PreAuthorize ("hasAuthority('ROLE_ADMIN')") 
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }
}