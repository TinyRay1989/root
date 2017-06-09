package com.git.yanlei.persistence.dao;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.git.yanlei.persistence.entity.Employee;

@Repository(value = "employeeRepository")
public interface EmployeeRepository extends JpaRepository<Employee, Integer>,
        JpaSpecificationExecutor<Employee> {

    @Query(value = "select e from Employee e where ename = :ename")
    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
    List<Employee> findByEname(@Param(value = "ename") String ename);

    @Query(value = "select * from t_employee e where ename = :ename", nativeQuery = true)
    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
    List<Employee> findByEname_native(@Param(value = "ename") String ename);

    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
    List<Employee> findByEnameStartingWithAndDepartment_NameOrderBySalary(String employeeName,
            String departmentName);

    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
    List<Employee> findByEnameStartingWithAndDepartment_Name(String employeeName,
            String departmentName, Sort sort);

    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
    Page<Employee> findByEnameStartingWithAndDepartment_Name(String employeeName,
            String departmentName, Pageable pageable);
}
