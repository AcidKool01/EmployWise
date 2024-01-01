package com.example.assignment.service;

import com.example.assignment.entity.Employee;
import com.example.assignment.exception.ResourceNotFoundException;
import com.example.assignment.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${mail-receiver}")
    private String receiver;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public Employee addEmployee(Employee employee) {
        employee.setId(UUID.randomUUID().toString());
        sendEmail(employee);

        return employeeRepository.save(employee);
    }

    private void sendEmail(Employee employee) {
        SimpleMailMessage message = new SimpleMailMessage();

        String body = employee.getEmployeeName() + " will now work under you. Mobile number is " + employee.getPhoneNumber() + " and email is " + employee.getEmail();

        message.setFrom(sender);
        message.setTo(receiver);
        message.setText(body);
        message.setSubject("New Employee Added");
        mailSender.send(message);

        System.out.println("Mail Send...");
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public String deleteById(String id) {
        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Record not found with id : " + id);
        }

        employeeRepository.deleteById(id);
        return "Deleted Employee with ID: " + id;
    }

    @Override
    public Employee updateById(Employee employee, String id){
        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Record not found with id : " + employee.getId());
        }

        Employee exsistingEmployee = employeeRepository.findById(employee.getId()).get();
        exsistingEmployee.setEmployeeName(employee.getEmployeeName());
        exsistingEmployee.setEmail(employee.getEmail());
        exsistingEmployee.setPhoneNumber(employee.getPhoneNumber());
        exsistingEmployee.setReportsTo(employee.getReportsTo());
        exsistingEmployee.setProfileImage(employee.getProfileImage());

        return employeeRepository.save(exsistingEmployee);
    }

    @Override
    public Employee getLevelManager(String employeeId, int level) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new ResourceNotFoundException("Record not found with id : " + employeeId);
        }

        Employee currEmployee = employeeRepository.findById(employeeId).orElseThrow();
        while(level-- > 0) {
            currEmployee = employeeRepository.findById(currEmployee.getReportsTo()).orElseThrow();
        }
        return currEmployee;
    }

    public List<Employee> sortingList(String field) {
        return employeeRepository.findAll(Sort.by(Sort.Direction.ASC, field));
    }

    public Page<Employee> findEmployeesWithPagination(int offset, int pageSize) {
        return employeeRepository.findAll(PageRequest.of(offset, pageSize));
    }

    public Page<Employee> findEmployeesWithPaginationAndSorting(int offset,int pageSize,String field) {
        return employeeRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));

    }
}