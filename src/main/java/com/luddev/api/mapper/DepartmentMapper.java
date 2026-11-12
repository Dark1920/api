package com.luddev.api.mapper;

import org.springframework.stereotype.Component;

import com.luddev.api.dto.DepartmentHeadDto;
import com.luddev.api.dto.DepartmentRequestDto;
import com.luddev.api.dto.DepartmentResponseDto;
import com.luddev.api.models.Department;
import com.luddev.api.models.Employee;

@Component
public class DepartmentMapper {

    public Department toEntity(DepartmentRequestDto dto, Employee chefDepartement) {
        Department department = new Department();
        department.setType(dto.type());
        department.setChefDepartement(chefDepartement);
        return department;
    }

    public void updateEntity(
        Department department,
        DepartmentRequestDto dto,
        Employee chefDepartement
    ) {
        department.setType(dto.type());
        department.setChefDepartement(chefDepartement);
    }

    public DepartmentResponseDto toResponseDto(Department department) {
        Employee chef = department.getChefDepartement();
        DepartmentHeadDto chefDto = new DepartmentHeadDto(
            chef.getId(),
            chef.getFirstname(),
            chef.getLastname()
        );
        return new DepartmentResponseDto(department.getId(), department.getType(), chefDto);
    }
}
