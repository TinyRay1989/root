package com.git.yanlei.persistence;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.git.yanlei.persistence.entity.Employee;
import com.git.yanlei.persistence.entity.Staff;
import com.git.yanlei.persistence.service.EmployeeService;
import com.git.yanlei.persistence.service.StaffService;

@RunWith(SpringRunner.class)
@Transactional(rollbackFor = Exception.class)
// @TransactionConfiguration(defaultRollback = false) 4.2弃用了
@Commit
@SpringBootTest
public class PersistenceSpringDataJpaApplicationTests {
    
    @Resource
    EmployeeService employeeService;
    
    @Resource
    StaffService staffService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void createEmployees() {
        employeeService.saveEmployees();
    }
    
    @Test
    public void findEmployeeByName(){
        Employee employee1 = employeeService.findByNameWithNameQuery("Matish");
        System.out.println(employee1);
        
        Employee employee2 = employeeService.findByNameWithQuery("Matish");
        System.out.println(employee2);
        
        Employee employee3 = employeeService.findByNameWithExample("Matish");
        System.out.println(employee3);
        
        Employee employee4 = employeeService.findByNameWithJpaSpecificationExecutor("Matish");
        System.out.println(employee4);
    }
    
    @Test
    public void findEmployeeByNameUseCache(){
        List<Employee> employees1 = employeeService.findByConditionOrderBySalary("Ma", "software");
        System.out.println(employees1);
        System.out.println("----------------------");
        List<Employee> employees2 = employeeService.findByConditionOrderBySalary("Ma", "software");
        System.out.println(employees2);
        System.out.println("----------------------");
        List<Employee> employees3 = employeeService.findByConditionOrderBy("Ma", "software", "salary");
        System.out.println(employees3);
        System.out.println("----------------------");
        List<Employee> employees4 = employeeService.findByConditionOrderBy("Ma", "software", "salary");
        System.out.println(employees4);
        System.out.println("----------------------");
        
        Page<Employee> pageEmployees =employeeService.findByConditionOrderBySalary("Ma", "software", 1, 2);
        System.out.println(pageEmployees);
        Page<Employee> pageEmployees1 =employeeService.findByConditionOrderBySalary("Ma", "software", 1, 2);
        System.out.println(pageEmployees1);
    }
    
    @Test
    public void findEmployees(){
        List<Employee> employees1 = employeeService.findByConditionOrderBySalary("Ma", "software");
        System.out.println(employees1);
        System.out.println("----------------------");
        List<Employee> employees2 = employeeService.findByConditionOrderBy("Ma", "software", "salary");
        System.out.println(employees2);
        System.out.println("----------------------");
        Page<Employee> pageEmployees =employeeService.findByConditionOrderBySalary("Ma", "software", 1, 2);
        System.out.println(pageEmployees);
        System.out.println(pageEmployees.getNumber());
        System.out.println(pageEmployees.getNumberOfElements());
        System.out.println(pageEmployees.getContent());
    }

    @Test
    public void createStaffs(){
        staffService.saveStaffs();
    }
    
    @Test
    public void findStaffs(){
        List<Staff> StaffList = staffService.findStaffBySnameStartingWith("G");
        System.out.println(StaffList);
    }
}
