package com.git.yanlei.persistence.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.git.yanlei.persistence.dao.DepartmentRepository;
import com.git.yanlei.persistence.dao.EmployeeRepository;
import com.git.yanlei.persistence.entity.Department;
import com.git.yanlei.persistence.entity.Employee;

@Service(value = "employeeService")
public class EmployeeServiceImpl implements EmployeeService {

    @Resource
    private EmployeeRepository employeeRepository;

    @Resource
    private DepartmentRepository departmentRepository;

    @Transactional
    @Override
    public void saveEmployees() {
        Department department = new Department("software");

        Employee employee1 = new Employee("Manisha", 40000, "接待员", department);
        Employee employee2 = new Employee("Masthanvali", 40000, "技术作家", department);
        Employee employee3 = new Employee("Satish", 30000, "技术作家", department);
        Employee employee4 = new Employee("Krishna", 30000, "技术作家", department);
        Employee employee5 = new Employee("Kiran", 35000, "接待员", department);

        departmentRepository.save(department);
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        employeeRepository.save(employee3);
        employeeRepository.save(employee4);
        employeeRepository.save(employee5);
    }

    @Override
    public List<Employee> findByConditionOrderBySalary(String startingWithEmployeeName,
            String departmentName) {
        return employeeRepository.findByEnameStartingWithAndDepartment_NameOrderBySalary(
                startingWithEmployeeName, departmentName);
    }

    @Override
    public List<Employee> findByConditionOrderBy(String startingWithEmployeeName,
            String departmentName, String orderByColumnName) {
        List<Order> orders = new ArrayList<Order>();
        Order order = new Order(Direction.ASC, orderByColumnName);
        orders.add(order);
        Sort sort = new Sort(orders);
        return employeeRepository.findByEnameStartingWithAndDepartment_Name(
                startingWithEmployeeName, departmentName, sort);
    }

    @Override
    public Page<Employee> findByConditionOrderBySalary(String startingWithEmployeeName,
            String departmentName, int page, int size) {

        Pageable pageable = new PageRequest(page, size, Direction.ASC, "salary");
        return employeeRepository.findByEnameStartingWithAndDepartment_Name(
                startingWithEmployeeName, departmentName, pageable);
    }
}
