package com.luddev.api.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.luddev.api.dto.DepartmentRequestDto;
import com.luddev.api.dto.DepartmentResponseDto;
import com.luddev.api.mapper.DepartmentMapper;
import com.luddev.api.models.Department;
import com.luddev.api.models.Employee;
import com.luddev.api.repository.DepartmentRepository;
import com.luddev.api.repository.EmployeeRepository;

@Service
@Transactional
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentMapper mapper;

    public DepartmentService(
        DepartmentRepository departmentRepository,
        EmployeeRepository employeeRepository,
        DepartmentMapper mapper
    ) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
        this.mapper = mapper;
    }

    public DepartmentResponseDto createDepartment(DepartmentRequestDto dto) {
        Employee chef = findEmployeeById(dto.chefDepartementId());
        Department department = mapper.toEntity(dto, chef);
        return mapper.toResponseDto(departmentRepository.save(department));
    }

    @Transactional(readOnly = true)
    public List<DepartmentResponseDto> getAllDepartments() {
        return departmentRepository.findAll().stream()
            .map(mapper::toResponseDto)
            .toList();
    }

    @Transactional(readOnly = true)
    public DepartmentResponseDto getDepartmentById(Long id) {
        return mapper.toResponseDto(findDepartmentById(id));
    }

    public DepartmentResponseDto updateDepartment(Long id, DepartmentRequestDto dto) {
        Department department = findDepartmentById(id);
        Employee chef = findEmployeeById(dto.chefDepartementId());
        mapper.updateEntity(department, dto, chef);
        return mapper.toResponseDto(departmentRepository.save(department));
    }

    public void deleteDepartment(Long id) {
        departmentRepository.delete(findDepartmentById(id));
    }

    private Department findDepartmentById(Long id) {
        return departmentRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Département introuvable")
        );
    }

    private Employee findEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employé chef introuvable")
        );
    }
}
