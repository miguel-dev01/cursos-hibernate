package com.hibernate;

import org.junit.jupiter.api.Test;

import com.hibernate.model.*;

public class SessionTest {

    @Test
    void refreshTest() {

        var session = HibernateUtil.getSessionFactory().openSession();

        var address = new Address("Calle falsa", "Murcia", "Espania");

        session.beginTransaction();
        session.persist(address);
        session.getTransaction().commit();

        // session.close();

        // session = HibernateUtil.getSessionFactory().openSession();

        // Cambios de algun usuario
        address.setCity("Madrid");
        address.setCity("Other site");

        // Deshacemos los cambios
        session.refresh(address);
    }

    @Test
    void getVsGetReference() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        // Usando get - Realiza la consulta de inmediato
        var address = session.get(Address.class, 1L);
        System.out.println("=================Get==================");
        System.out.println(address);

        // Usando getReference - Realiza la consulta solo cuando se
        // accede a los datos
        address = session.getReference(Address.class, 1L);
        System.out.println("================GetReference===================");
        System.out.println(address.getCity());
    }

    @Test
    void load() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        // No esta persistida, su estado es transient
        var address = new Address("Calle falsa", "Murcia", "Espania");
        System.out.println(session.contains(address)); // false

        // Aqui se persiste, su estado es persistent.
        // Quiere decir que esta en la base de datos
        session.load(address, 1L);

        // Aqui ya es true porque pasa a ser una entidad gestionada por el contexto
        // de persistencia de hibernate, es decir , pasaria a ser estado managed
        System.out.println(session.contains(address)); // true

    }

    @Test
    void evict() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        // No esta persistida, su estado es transient
        var address = session.get(Address.class, 1L);
        // Es true porque get lo esta trayendo de la base de datos
        System.out.println(session.contains(address)); // true

        // Evict lo saca del contexto de persistencia
        session.evict(address);
        // detach es lo mismo que evict
        // session.detach(address);

    }

    @Test
    void isDirty() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        var address = session.get(Address.class, 1L);
        System.out.println(session.contains(address)); // true

        // Da false porque no hay cambios pendientes en el
        System.out.println(session.isDirty()); // false

        address.setCity("Madrid");

        // Da true porque hay cambios pendientes
        System.out.println(session.isDirty()); // true

        // Obtenemos la transaccion, persistimos el objeto con el dato cambiado
        // y commiteamos en la BD
        session.getTransaction();
        session.persist(address);
        session.getTransaction().commit();

    }

    @Test
    void byId() {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();
        session.byId(Address.class)
                .loadOptional(9999L)
                .ifPresent(System.out::println);

        session.byId(Address.class)
                .loadOptional(1L)
                .ifPresent(System.out::println);

    }

    @Test
    void insertData() {
        var session = HibernateUtil.getSessionFactory().openSession();

        var address1 = new Address("Calle falsa", "Murcia", "Espania");

        session.beginTransaction();

        session.persist(address1);

        session.getTransaction().commit();
    }

}
