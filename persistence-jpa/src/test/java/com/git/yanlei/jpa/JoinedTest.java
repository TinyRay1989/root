package com.git.yanlei.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.Test;

import com.git.yanlei.jpa.entity.joined_table.NonTeachingStaff;
import com.git.yanlei.jpa.entity.joined_table.Staff;
import com.git.yanlei.jpa.entity.joined_table.TeachingStaff;


public class JoinedTest {
    @Test
    public void createTable() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();
        Staff s = new Staff(0, "Tom");
        // Teaching staff entity
        TeachingStaff ts1 = new TeachingStaff(1, "Gopal", "MSc MEd", "Maths");
        TeachingStaff ts2 = new TeachingStaff(2, "Manisha", "BSc BEd", "English");
        // Non-Teaching Staff entity
        NonTeachingStaff nts1 = new NonTeachingStaff(3, "Satish", "Accounts");
        NonTeachingStaff nts2 = new NonTeachingStaff(4, "Krishna", "Office Admin");

        // storing all entities
        entitymanager.persist(s);
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
