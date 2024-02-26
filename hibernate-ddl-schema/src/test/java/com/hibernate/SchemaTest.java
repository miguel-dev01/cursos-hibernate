package com.hibernate;

import org.junit.jupiter.api.Test;

import com.hibernate.model.*;

public class SchemaTest {

    @Test
    void testSchema() {
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

        var address1 = new Address("Main Street", "New York", "EEUU");
        session.persist(address1);

        session.getTransaction().commit();
    }
}
