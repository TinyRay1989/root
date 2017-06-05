package com.git.yanlei.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.junit.Test;

import com.git.yanlei.jpa.entity.Employee;

public class JPQLTest {

    @Test
    public void scalarandFunctions() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entitymanager = emfactory.createEntityManager();
        Query query = entitymanager.createQuery("select UPPER(e.ename) from Employee e");
        @SuppressWarnings("unchecked")
        List<String> list = query.getResultList();
        for (String e : list) {
            System.out.println("Employee NAME :" + e);
        }
        entitymanager.close();
        emfactory.close();
    }

    @Test
    public void aggregateFunctions() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entitymanager = emfactory.createEntityManager();
        TypedQuery<Double> query = entitymanager.createQuery(
                "select MAX(e.salary) from Employee e", Double.class);
        Double result = query.getSingleResult();
        System.out.println("Max Employee Salary :" + result);
        entitymanager.close();
        emfactory.close();
    }

    @Test
    public void betweenAndFunctions() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entitymanager = emfactory.createEntityManager();
        Query query = entitymanager
                .createQuery("select e from Employee e where e.salary between 30000 and 40000 ");
        @SuppressWarnings("unchecked")
        List<Employee> result = query.getResultList();
        for (Employee e : result) {
            System.out.println(e);
        }
        entitymanager.close();
        emfactory.close();
    }

    @Test
    public void likeFunctions() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entitymanager = emfactory.createEntityManager();
        Query query = entitymanager
                .createQuery("select e from Employee e where e.ename like 'K%' ");
        @SuppressWarnings("unchecked")
        List<Employee> result = query.getResultList();
        for (Employee e : result) {
            System.out.println(e);
        }
        entitymanager.close();
        emfactory.close();
    }

    @Test
    public void Ordering() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entitymanager = emfactory.createEntityManager();
        Query query = entitymanager.createQuery("select e from Employee e order by e.salary");
        @SuppressWarnings("unchecked")
        List<Employee> result = query.getResultList();
        for (Employee e : result) {
            System.out.println(e);
        }
        entitymanager.close();
        emfactory.close();
    }
    
    @Test
    public void createNamedQuery() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entitymanager = emfactory.createEntityManager();
        TypedQuery<Employee> query = entitymanager.createNamedQuery("findEmployeeByName", Employee.class);
        query.setParameter("name", "Satish");
        Employee result = query.getSingleResult();
        System.out.println(result);
        entitymanager.close();
        emfactory.close();
    }

}
