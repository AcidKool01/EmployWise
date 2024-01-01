package com.example.assignment.service;

import com.example.assignment.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {
    Employee addEmployee(Employee employee);

    List<Employee> getAllEmployees();

    String deleteById(String id);

    Employee updateById(Employee employee, String id);

    Employee getLevelManager(String employeeId, int level);

    List<Employee> sortingList(String field);

    Page<Employee> findEmployeesWithPagination(int offset, int pageSize);

    Page<Employee> findEmployeesWithPaginationAndSorting(int offset,int pageSize,String field);
}
