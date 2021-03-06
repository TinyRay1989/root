package com.git.yanlei.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Test;

import com.git.yanlei.jpa.entity.Employee;

public class EntityManagerTest {

    @Test
    public void createEmployee() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();
        Employee employee0 = new Employee("Gopal", 40000, "技术经理");
        Employee employee1 = new Employee("Manisha", 40000, "接待员");
        Employee employee2 = new Employee("Masthanvali", 40000, "技术作家");
        Employee employee3 = new Employee("Satish", 30000, "技术作家");
        Employee employee4 = new Employee("Krishna", 30000, "技术作家");
        Employee employee5 = new Employee("Kiran", 35000, "接待员");

        entitymanager.persist(employee0);
        entitymanager.persist(employee1);
        entitymanager.persist(employee2);
        entitymanager.persist(employee3);
        entitymanager.persist(employee4);
        entitymanager.persist(employee5);
        entitymanager.getTransaction().commit();

        entitymanager.close();
        emfactory.close();
    }

    @Test
    public void findEmployee() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entitymanager = emfactory.createEntityManager();
        Employee employee = entitymanager.find(Employee.class, 1);

        System.out.println("employee ID = " + employee.getEid());
        System.out.println("employee NAME = " + employee.getEname());
        System.out.println("employee SALARY = " + employee.getSalary());
        System.out.println("employee DESIGNATION = " + employee.getDeg());
    }

    @Test
    public void cupdateEmployee() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();
        Employee employee = entitymanager.find(Employee.class, 1);
        // before update
        System.out.println(employee);
        employee.setSalary(46110);
        entitymanager.merge(employee);
        entitymanager.getTransaction().commit();
        // after update
        System.out.println(employee);
        entitymanager.close();
        emfactory.close();
    }

    @Test
    public void dremoveEmployee() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();
        Employee employee = entitymanager.find(Employee.class, 1);
        entitymanager.remove(employee);
        entitymanager.getTransaction().commit();
        entitymanager.close();
        emfactory.close();
    }
}
