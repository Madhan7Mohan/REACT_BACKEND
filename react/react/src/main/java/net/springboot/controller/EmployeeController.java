package net.springboot.controller;

import net.springboot.model.OtpRequest;
import net.springboot.repository.OtpRepository;
import net.springboot.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.springboot.exception.ResourceNotFoundException;
import net.springboot.model.Employee;
import net.springboot.repository.EmployeeRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private OtpService otpService;

    @Autowired
    private EmployeeRepository employeeRepository;


    // Get all employees
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Create employee
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    // Get employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
        return ResponseEntity.ok(employee);
    }

    // Update employee
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));

        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmailId(employeeDetails.getEmailId());
        employee.setSalary(employeeDetails.getSalary());

        Employee updatedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    // Delete employee
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));

        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }









    // Reset auto-increment sequence for employees table
    @GetMapping("/reset-sequence")
    public ResponseEntity<String> resetSequence() {
        try {
            // Find the maximum ID and set the sequence to it + 1
            Long maxId = employeeRepository.getMaxId();
            if (maxId != null) {
                entityManager.createNativeQuery("ALTER TABLE employees AUTO_INCREMENT = " + (maxId + 1)).executeUpdate();
            }
            return ResponseEntity.ok("Sequence reset successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error resetting sequence");
        }
    }





    @PostMapping("/generate-otp")
    public ResponseEntity<String> generateOtp(@RequestBody Employee employee) {
        String username = employee.getEmailId();
        otpService.generateOtp(username);
        return ResponseEntity.ok("OTP generated successfully");
    }

    @PostMapping("/validate-otp")
    public ResponseEntity<String> validateOtp(@RequestBody OtpRequest otpRequest) {
        boolean isValid = otpService.validateOtp(otpRequest.getUsername(), otpRequest.getOtp());
        if (isValid) {
            return ResponseEntity.ok("OTP validation successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP");
        }
    }


}

