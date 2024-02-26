package com.hibernate;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import com.hibernate.model.*;


public class EntityTest {

    @Test
    void test() {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        var address1 = session.find(Address.class, 1L);
        System.out.println(address1);

        // Para cuando la propiedad del xml(hibernate.hbm2ddl.auto) sea create-drop
        // hay que cerrar la sesion de Hibernate para que se ejecute el drop
        session.close();
        HibernateUtil.shutdown();
    }

    void insertData() {
        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        var employee1 = new Employee("12345678E", "e1@employee.com", "11111", 2500.0, 25, 
                LocalTime.of(9, 0), LocalTime.of(17, 0));
        var employee2 = new Employee("87654321X", "e2@employee.com", "22222", 3000.0, 30,
                LocalTime.of(9, 0), LocalTime.of(17, 0));

        session.persist(employee1);
        session.persist(employee2);

        session.getTransaction().commit();
    }

}
