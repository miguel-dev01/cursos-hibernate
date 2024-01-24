package com.hibernate;

import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import com.hibernate.model.Address;
import com.hibernate.model.Author;

/* Tipos de estados de una entidad
 * - Transient --> Es cuando se crea una entidad y no se asocia a una sesion
 *   y ademas no esta persistida en la base de datos
 * - Managed --> Es cuando se asocia a una sesion y los datos estan persistidos
 *   en la base de datos
 * - Detached --> Cuando los datos han sido persistidos en la base de datos pero la entidad
 *   no esta asociada a una sesion de hibernate, bien porque se ha cerrado u otro motivo
 * - Removed --> Es cuando se elimina la entidad
 */

public class LifeCycleTest {

    @Test
    void transientState() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Address address = new Address("Calle 1", "Ciudad 1", "Pais 1");
        System.out.println(session.contains(address));

        // Cuando no es managed puede generar problema si se asocia a otra entidad
        var author1 = new Author("author1", "author1@gmail.com", null);
        author1.setAddress(address);

        session.beginTransaction();
        session.persist(author1);
        session.getTransaction().commit();

    }

    @Test
    void detached() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Address address = new Address("Calle 1", "Ciudad 1", "Pais 1");
        address.setId(1L);

        session.beginTransaction();
        // Se usa merge. Por que con persist salta una excepcion, ya que no se puede persistir 
        // una entidad detached.
        // org.hibernate.PersistentObjectException: detached entity passed to persist
        // ¿por que se usa merge? porque persist solo funciona con entidades transient
        // y merge funciona con entidades detached
        // Esto ocurre porque la entidad no esta asociada a la sesion de hibernate y ya esta guardada
        // en la base de datos, por lo tanto se usa merge para hacer una copia de la entidad y
        // asociarla a la sesion de hibernate y poder sincronizar los datos.
        session.merge(address);
        session.getTransaction().commit();
    }

    @Test
    void detached2() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Address address = new Address("street1", "city1", "country1");
        address.setId(1L);

        session.beginTransaction();
        // PersistenceException` : detached entity passed to persist:
        // session.persist(address);
        session.merge(address);
        session.getTransaction().commit();

        // Se puede usar detach para desasociar la entidad de la sesion de hibernate
        session.detach(address);

        session.beginTransaction();
        // PersistenceException` : detached entity passed to persist:
        var address1 = session.find(Address.class, 1L);
        address1.setCity("Barcelona");
        session.persist(address1);
        // session.merge(address);
        session.getTransaction().commit();
    }

    @Test
    void managed() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Address address = new Address("street1", "city1", "country1");

        session.beginTransaction();
        session.persist(address);
        session.getTransaction().commit();

        System.out.println(session.contains(address));

    }

    @Test
    void removed() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Address address = new Address("Calle 1", "Ciudad 1", "Pais 1");
        address.setId(1L);

        session.beginTransaction();
        session.remove(address);
        session.getTransaction().commit();

        System.out.println(session.contains(address));
    }

    @Test
    void differentSessions() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        // con find se obtiene el objeto si contiene el contexto de persistencia
        var address1 = session.find(Address.class, 1L);
        System.out.println(session.contains(address1)); // true
        session.close();

        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println(session.contains(address1)); // false
    }

    @Test
    void load() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        // No carga el objeto hasta que no accede a sus atributos
        var address1 = session.getReference(Address.class, 1L);
        System.out.println("************************");
        System.out.println(address1.getCountry());

    }

}
