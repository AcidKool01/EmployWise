package com.example.assignment.controller;

import com.example.assignment.data.DummyData;
import com.example.assignment.entity.Employee;
import com.example.assignment.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/initialize")
    public List<Employee> initialize() {
        Employee headEmployee = new Employee();
        headEmployee.setEmployeeName("Lorem Ipsum");
        headEmployee.setReportsTo(null);
        headEmployee.setPhoneNumber("8340559117");
        headEmployee.setEmail("tk0128@gmail.com");

        String reportingManagerId = employeeService.addEmployee(headEmployee).getId();
        Employee emp = new Employee();

        for(int i=0; i<80; i++) {
            emp.setReportsTo(reportingManagerId);
            emp.setEmployeeName(DummyData.names[i]);
            emp.setPhoneNumber(DummyData.phoneNumbers[i]);
            emp.setProfileImage(DummyData.profileImageUrls[i]);
            emp.setEmail(DummyData.emailIds[i]);

            reportingManagerId = employeeService.addEmployee(emp).getId();

        }

        return employeeService.getAllEmployees();
    }

    @PostMapping
    public Employee addEmployee(@RequestBody @Valid Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> deleteById(@PathVariable String id) {
        Map<String, Object> response = new HashMap<>();

        try {
            String result = employeeService.deleteById(id);
            response.put("success", true);
            response.put("data", result);
        } catch (Exception e) {
            response.put("success", false);
            response.put("data", null);
            response.put("errorMessage", "Exception occurred, no such ID is present.");
        }

        return response;
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@RequestBody @Valid Employee employee, @PathVariable String id) {
        return employeeService.updateById(employee, id);
    }

    @GetMapping("/{employeeId}/manager/{level}")
    public Employee getLevelManager(
            @PathVariable String employeeId,
            @PathVariable int level
    ) {
        return employeeService.getLevelManager(employeeId, level);
    }

    @GetMapping("/sort/{field}")
    public List<Employee> getSortedList(@PathVariable String field) {
        return employeeService.sortingList(field);
    }

    @GetMapping("/{offset}/{pagesize}")
    public Page<Employee> getPagination(
            @PathVariable int offset,
            @PathVariable int pagesize
    ) {
        return employeeService.findEmployeesWithPagination(offset, pagesize);
    }

    @GetMapping("/{offset}/{pagesize}/{field}")
    public Page<Employee> getPaginationAndSorted(
            @PathVariable int offset,
            @PathVariable int pagesize,
            @PathVariable String field
    ) {
        return employeeService.findEmployeesWithPaginationAndSorting(offset, pagesize, field);
    }
}
