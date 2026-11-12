package com.luddev.api.services;

import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.luddev.api.dto.EmployeePatchDto;
import com.luddev.api.dto.EmployeeRequestDto;
import com.luddev.api.dto.EmployeeResponseDto;
import com.luddev.api.dto.EmployeeUpdateDto;
import com.luddev.api.mapper.EmployeeMapper;
import com.luddev.api.models.Employee;
import com.luddev.api.repository.EmployeeRepository;



@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper mapper;
    private final PasswordEncoder passwordEncoder;


    public EmployeeService(
        EmployeeRepository employeeRepository, 
        EmployeeMapper mapper,
        PasswordEncoder passwordEncoder
    ) {
        this.employeeRepository = employeeRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    // CREATE
    public EmployeeResponseDto createEmployee(EmployeeRequestDto dto) {
        if (employeeRepository.existsByMail(dto.getMail())){
            throw new RuntimeException("Un employé avec cet email existe deja");
        }
        Employee employee = mapper.toEntity(dto);
        employee.setPassword(passwordEncoder.encode(dto.getPassword()));
        Employee saved = employeeRepository.save(employee);

        return mapper.toDTO(saved);
    }

    // READ ALL
    public List<EmployeeResponseDto> getAllEmployees() {
        return StreamSupport.stream(employeeRepository.findAll().spliterator(), false)
                .map(mapper::toDTO)
                .toList();
    }

    // READ ONE
    public EmployeeResponseDto getEmployeeById(Long id) {
        return mapper.toDTO(findEntityById(id));
    }

    // UPDATE (PUT)
    public EmployeeResponseDto updateEmployee(Long id, EmployeeUpdateDto dto) {
        Employee employee = findEntityById(id);

        if (employeeRepository.existsByMailAndIdNot(dto.getMail(), id)){
            throw new RuntimeException("Un employé avec cet email existe deja");
        }

        mapper.updateEntity(employee, dto);
        if (dto.getPassword() != null && !dto.getPassword().isBlank()){
            employee.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        return mapper.toDTO(employeeRepository.save(employee));
    }

    // PATCH
    public EmployeeResponseDto patchEmployee(Long id, EmployeePatchDto dto) {

        Employee employee = findEntityById(id);

        if (
            dto.getMail() != null 
            && !dto.getMail().isBlank() 
            && employeeRepository.existsByMailAndIdNot(dto.getMail(), id)
        ){
            throw new RuntimeException("Un employé avec cet email existe deja");
        }

        mapper.patchEntity(employee, dto);
        if (dto.getPassword() != null && !dto.getPassword().isBlank()){
            employee.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        Employee saved = employeeRepository.save(employee);

        return mapper.toDTO(saved);
    }

    // DELETE
    public void deleteEmployee(Long id) {
        employeeRepository.delete(findEntityById(id));
    }

    // Méthode interne : renvoie l'entité
    private Employee findEntityById(Long id) {
        return employeeRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employé introuvable")
        );
    }
}