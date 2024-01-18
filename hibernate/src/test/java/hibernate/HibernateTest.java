package hibernate;

import hibernate.model.Employee;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

public class HibernateTest {
    
    // Test de persistencia
    @Test
    void persist() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        var employee1 = new Employee("Juan", 25);
        var employee2 = new Employee("Pedro", 30);

        session.persist(employee1);
        session.persist(employee2);

        // Confirmacion de escritura en la base de datos (en disco)
        session.getTransaction().commit();

        session.close();

    }
}
