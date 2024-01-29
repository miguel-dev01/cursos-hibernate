package com.hibernate;

import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import com.hibernate.model.Employee;

public class Hibernate6Test {

    @Test
    void test() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        // Deprecado en Hibernate 6
        // session.createQuery("select e from Employee e");
        
        session.createQuery("select e from Employee e", Employee.class);
    }

}
