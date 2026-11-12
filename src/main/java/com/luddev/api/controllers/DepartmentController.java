package com.luddev.api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.luddev.api.dto.DepartmentRequestDto;
import com.luddev.api.dto.DepartmentResponseDto;
import com.luddev.api.services.DepartmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentResponseDto create(@Valid @RequestBody DepartmentRequestDto dto) {
        return departmentService.createDepartment(dto);
    }

    @GetMapping
    public List<DepartmentResponseDto> getAll() {
        return departmentService.getAllDepartments();
    }

    @GetMapping("/{id}")
    public DepartmentResponseDto getById(@PathVariable Long id) {
        return departmentService.getDepartmentById(id);
    }

    @PutMapping("/{id}")
    public DepartmentResponseDto update(
        @PathVariable Long id,
        @Valid @RequestBody DepartmentRequestDto dto
    ) {
        return departmentService.updateDepartment(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
    }
}
