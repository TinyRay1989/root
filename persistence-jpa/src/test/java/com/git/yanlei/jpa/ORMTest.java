package com.git.yanlei.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Test;

import com.git.yanlei.jpa.entity.Department;
import com.git.yanlei.jpa.entity.Employee;

public class ORMTest {
    @Test
    public void manyToOne() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        // Create Department Entity
        Department department = new Department();
        department.setName("Development");
        // Store Department
        entitymanager.persist(department);

        // Create Employee1 Entity
        Employee employee1 = new Employee();
        employee1.setEname("Satish");
        employee1.setSalary(45000.0);
        employee1.setDeg("Technical Writer");
        employee1.setDepartment(department);

        // Create Employee2 Entity
        Employee employee2 = new Employee();
        employee2.setEname("Krishna");
        employee2.setSalary(45000.0);
        employee2.setDeg("Technical Writer");
        employee2.setDepartment(department);

        // Create Employee3 Entity
        Employee employee3 = new Employee();
        employee3.setEname("Masthanvali");
        employee3.setSalary(50000.0);
        employee3.setDeg("Technical Writer");
        employee3.setDepartment(department);

        // Store Employees
        entitymanager.persist(employee1);
        entitymanager.persist(employee2);
        entitymanager.persist(employee3);

        entitymanager.getTransaction().commit();
        entitymanager.close();
        emfactory.close();
    }
    
    @Test
    public void oneToMany() {
        EntityManagerFactory emfactory = Persistence.
                createEntityManagerFactory( "Eclipselink_JPA" );
        EntityManager entitymanager = emfactory.
                createEntityManager( );
        entitymanager.getTransaction( ).begin( );
        
        //Create Employee1 Entity
        Employee employee1 = new Employee();
        employee1.setEname("Satish");
        employee1.setSalary(45000.0);
        employee1.setDeg("Technical Writer");
                                
        //Create Employee2 Entity
        Employee employee2 = new Employee();
        employee2.setEname("Krishna");
        employee2.setSalary(45000.0);
        employee2.setDeg("Technical Writer");
                                
        //Create Employee3 Entity
        Employee employee3 = new Employee();
        employee3.setEname("Masthanvali");
        employee3.setSalary(50000.0);
        employee3.setDeg("Technical Writer");
        
        //Store Employee
        entitymanager.persist(employee1);
        entitymanager.persist(employee2);
        entitymanager.persist(employee3);
        
        //Create Employeelist
        List<Employee> emplist = new ArrayList<Employee>();
        emplist.add(employee1);
        emplist.add(employee2);
        emplist.add(employee3);
        
        //Create Department Entity
        Department department= new Department();
        department.setName("Development");
        department.setEmployeeList(emplist);
                
        //Store Department
        entitymanager.persist(department);
        
        entitymanager.getTransaction().commit();
        entitymanager.close();
        emfactory.close();
    }

    @Test
    public void findEmployee() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entitymanager = emfactory.createEntityManager();
        Employee employee = entitymanager.find(Employee.class, 1);
        System.out.println(employee);
    }
    
    @Test
    public void findDepartment() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entitymanager = emfactory.createEntityManager();
        Department dpt = entitymanager.find(Department.class, 1);
        System.out.println(dpt);
        entitymanager.close();
        emfactory.close();
    }
    
    @Test
    public void remove() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();
        Department dpt = entitymanager.find(Department.class, 1);
        entitymanager.remove(dpt);
        entitymanager.getTransaction().commit();
        entitymanager.close();
        emfactory.close();
    }
}
