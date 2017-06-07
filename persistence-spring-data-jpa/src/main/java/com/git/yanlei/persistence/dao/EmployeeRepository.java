package com.git.yanlei.persistence.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.git.yanlei.persistence.entity.Employee;

@Repository(value = "employeeRepository")
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findByEnameStartingWithAndDepartment_NameOrderBySalary(String employeeName,
            String departmentName);

    List<Employee> findByEnameStartingWithAndDepartment_Name(String employeeName, String departmentName,
            Sort sort);

    Page<Employee> findByEnameStartingWithAndDepartment_Name(String employeeName, String departmentName,
            Pageable pageable);
}
