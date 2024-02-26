package com.hibernate.model;

import org.junit.jupiter.api.Test;

import com.hibernate.HibernateUtil;

public class QueryTest {

    @Test
    void getResultList() {
        // Obtener todos los datos
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = "select e from Employee e";

        var employees = session.createQuery(jpql, 
            Employee.class);
        employees.getResultList().forEach(System.out::println);

        System.out.println("-------------------------------------------------");

        employees.list().forEach(System.out::println);
    }

    @Test
    void getResultStream() {
        // Obtener todos los datos
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = "select e from Employee e";

        var employees = session.createQuery(jpql, 
            Employee.class);
        // Dos ejemplos de como usar el stream
        employees.getResultList().stream().map(e -> e.getEmail() + " - " + e.getSalary()).forEach(System.out::println);
        System.out.println("-------------------------------------------------");
        // Lo que se obtiene aqui ya no son empleados, son emails convertidos a mayusculas
        employees.getResultList().stream().map(Employee::getEmail).map(email -> email.toUpperCase()).forEach(System.out::println);
    }

    // Cuando sabemos que solo vamos a recuperar un dato
    @Test
    void getSingleResult() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = "select e from Employee e where e.id = :id";
        // El empleado con id 4 no existe
        var employee = session.createQuery(jpql, Employee.class)
            .setParameter("id", 4L);
        /* 
         * Si no se encuentra el dato, se lanza una excepcion
         * Existen metodos para evitar la excepcion
         * getSingleResultOrNull()
         * getSingleResultOptional()
         * uniqueResult()
         */
        employee.getSingleResultOrNull();
        System.out.println("-------------------------------------------------");
        employee.uniqueResultOptional().ifPresent(System.out::println);
        System.out.println("-------------------------------------------------");
        employee.uniqueResultOptional().map(Employee::getEmail).ifPresent(System.out::println);
    }

    @Test
    void pagination() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = "select e from Employee e";
        /*
         * setFirstResult() indica desde el primer registro que se va a recuperar
         * setMaxResults() indica cuantos registros se van a recuperar
         */
        var employee = session.createQuery(jpql, Employee.class)
            .setFirstResult(1)
            .setMaxResults(2);
        
        employee.getResultList().forEach(System.out::println);
    }

    @Test
    void dynamicPagination() {
        insertDataWithLoop();
        var session = HibernateUtil.getSessionFactory().openSession();

        // Tamanio de paginacion
        int pageSize = 20;
        // Count
        String jpql = "select count(e.id) from Employee e";
        Long numEmployees = session.createQuery(jpql, Long.class).uniqueResult();
        
        // calculate pages
        int lastPage = (int) Math.ceil(numEmployees / (double) pageSize);

        String jpql2 = "select e from Employee e";
        var employee = session.createQuery(jpql2, Employee.class);
        // A partir de qué fila va a empezar a recuperar registros hasta pageSize
        employee.setFirstResult((lastPage - 1) * pageSize);
        employee.setMaxResults(pageSize);

        System.out.println("posicion inicial: " + ((lastPage - 1) * pageSize));
        System.out.println("posicion final: " + pageSize);
        employee.getResultList().forEach(System.out::println);
    }

    void insertData() {
        var session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        var employee1 = new Employee("empleado1@email.com", 2000.0);
        var employee2 = new Employee("empleado2@email.com", 2500.0);
        var employee3 = new Employee("empleado3@email.com", 3000.0);

        session.persist(employee1);
        session.persist(employee2);
        session.persist(employee3);

        session.getTransaction().commit();
    }

    void insertDataWithLoop() {
        var session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        for (int i = 1; i < 100; i++) {
            var employee = new Employee("empleado" + i + "@email.com",
                2000.0 + (i * 100));
            session.persist(employee);
        }
        session.getTransaction().commit();
    }

}
