package com.luddev.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luddev.api.models.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
