package com.luddev.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import com.luddev.api.dto.EmployeePatchDto;
import com.luddev.api.dto.EmployeeRequestDto;
import com.luddev.api.dto.EmployeeResponseDto;
import com.luddev.api.dto.EmployeeUpdateDto;
import com.luddev.api.mapper.EmployeeMapper;
import com.luddev.api.models.Employee;
import com.luddev.api.repository.EmployeeRepository;
import com.luddev.api.services.EmployeeService;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper mapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;
    private EmployeeResponseDto responseDto;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setId(1L);
        employee.setFirstname("Jean");
        employee.setLastname("Dupont");
        employee.setMail("jean.dupont@test.com");
        employee.setPosition("Développeur");
        employee.setSalary(45000.0);
        employee.setPassword("encodedPassword123");

        responseDto = new EmployeeResponseDto(
                1L,
                "Jean",
                "Dupont",
                "jean.dupont@test.com",
                "Développeur",
                45000.0
        );
    }

    @Test
    @DisplayName("Création d'un employé avec succès")
    void createEmployee_Success() {
        EmployeeRequestDto requestDto = new EmployeeRequestDto();
        requestDto.setFirstname("Jean");
        requestDto.setLastname("Dupont");
        requestDto.setMail("jean.dupont@test.com");
        requestDto.setPassword("plainPassword123");
        requestDto.setPosition("Développeur");
        requestDto.setSalary(45000.0);

        when(employeeRepository.existsByMail(requestDto.getMail())).thenReturn(false);
        when(mapper.toEntity(requestDto)).thenReturn(employee);
        when(passwordEncoder.encode("plainPassword123")).thenReturn("encodedPassword123");
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(mapper.toDTO(employee)).thenReturn(responseDto);

        EmployeeResponseDto result = employeeService.createEmployee(requestDto);

        assertNotNull(result);
        assertEquals("jean.dupont@test.com", result.mail());
        assertEquals("Jean", result.firstname());
        verify(employeeRepository).save(employee);
    }

    @Test
    @DisplayName("Création d'un employé échoue si email déjà existant")
    void createEmployee_DuplicateEmail_ThrowsException() {
        EmployeeRequestDto requestDto = new EmployeeRequestDto();
        requestDto.setMail("jean.dupont@test.com");

        when(employeeRepository.existsByMail(requestDto.getMail())).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            employeeService.createEmployee(requestDto);
        });

        assertEquals("Un employé avec cet email existe deja", exception.getMessage());
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    @DisplayName("Récupération de tous les employés")
    void getAllEmployees_Success() {
        when(employeeRepository.findAll()).thenReturn(List.of(employee));
        when(mapper.toDTO(employee)).thenReturn(responseDto);

        List<EmployeeResponseDto> result = employeeService.getAllEmployees();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("jean.dupont@test.com", result.get(0).mail());
    }

    @Test
    @DisplayName("Récupération d'un employé par ID avec succès")
    void getEmployeeById_Success() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(mapper.toDTO(employee)).thenReturn(responseDto);

        EmployeeResponseDto result = employeeService.getEmployeeById(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
    }

    @Test
    @DisplayName("Récupération par ID échoue si non trouvé")
    void getEmployeeById_NotFound_ThrowsException() {
        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            employeeService.getEmployeeById(99L);
        });
    }

    @Test
    @DisplayName("Mise à jour d'un employé avec succès")
    void updateEmployee_Success() {
        EmployeeUpdateDto updateDto = new EmployeeUpdateDto();
        updateDto.setFirstname("Jean Modifié");
        updateDto.setLastname("Dupont");
        updateDto.setMail("jean.modifie@test.com");
        updateDto.setPassword("newPassword123");
        updateDto.setSalary(50000.0);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.existsByMailAndIdNot(updateDto.getMail(), 1L)).thenReturn(false);
        when(passwordEncoder.encode("newPassword123")).thenReturn("newEncodedPassword");
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(mapper.toDTO(employee)).thenReturn(responseDto);

        EmployeeResponseDto result = employeeService.updateEmployee(1L, updateDto);

        assertNotNull(result);
        verify(mapper).updateEntity(employee, updateDto);
        verify(employeeRepository).save(employee);
    }

    @Test
    @DisplayName("Suppression d'un employé avec succès")
    void deleteEmployee_Success() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        employeeService.deleteEmployee(1L);

        verify(employeeRepository).delete(employee);
    }
}