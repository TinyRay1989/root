package com.git.yanlei.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.Test;

import com.git.yanlei.jpa.entity.table_per_class.NonTeachingStaff;
import com.git.yanlei.jpa.entity.table_per_class.Staff;
import com.git.yanlei.jpa.entity.table_per_class.TeachingStaff;


public class TablePerConcreteTest {
    @Test
    public void pk() throws InterruptedException {
        Thread.sleep(1000);
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();
        Staff s1 = new Staff(1, "Gopal");
        Staff s2 = new Staff(2, "Manisha");
        Staff s3 = new Staff(3, "Satish");
        Staff s4 = new Staff(4, "Krishna");
        entitymanager.persist(s1);
        entitymanager.persist(s2);
        entitymanager.persist(s3);
        entitymanager.persist(s4);
        entitymanager.getTransaction().commit();
        entitymanager.close();
        emfactory.close();
    }
    
    @Test
    public void fk() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();
        TeachingStaff ts1 = new TeachingStaff(1, "Gopal", "MSc MEd", "Maths");
        TeachingStaff ts2 = new TeachingStaff(2, "Manisha", "BSc BEd", "English");
        NonTeachingStaff nts1 = new NonTeachingStaff(3, "Satish", "Accounts");
        NonTeachingStaff nts2 = new NonTeachingStaff(4, "Krishna", "Office Admin");
        
        entitymanager.persist(ts1);
        entitymanager.persist(ts2);
        entitymanager.persist(nts1);
        entitymanager.persist(nts2);
        entitymanager.getTransaction().commit();
        entitymanager.close();
        emfactory.close();
    }

    @Test
    public void select() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entitymanager = emfactory.createEntityManager();
        Query query = entitymanager.createQuery("select s from NonTeachingStaffJoin s");
        @SuppressWarnings("unchecked")
        List<Staff> result = query.getResultList();
        for (Staff s : result) {
            System.out.println(s);
        }
        entitymanager.close();
        emfactory.close();
    }
}
