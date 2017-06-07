package com.git.yanlei.persistence.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.git.yanlei.persistence.entity.Employee;


public interface EmployeeService{
    void saveEmployees();
    
    List<Employee> findByConditionOrderBySalary(String startingWithEmployeeName, String departmentName);

    List<Employee> findByConditionOrderBy(String startingWithEmployeeName, String departmentName,
            String orderByColumnName);

    Page<Employee> findByConditionOrderBySalary(String startingWithEmployeeName, String departmentName,
            int page, int size);

    
}
