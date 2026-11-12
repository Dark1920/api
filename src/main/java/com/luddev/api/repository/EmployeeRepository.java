package com.luddev.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.luddev.api.models.Employee;


public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByMail(String mail);
    boolean existsByMailAndIdNot(String mail, Long id);
    Optional<Employee> findByMail(String mail);

}
