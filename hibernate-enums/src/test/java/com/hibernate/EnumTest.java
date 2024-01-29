package com.hibernate;

import org.junit.jupiter.api.Test;

import com.hibernate.model.Employee;
import com.hibernate.model.EmployeeCategory;

public class EnumTest {

    @Test
    void checkEnum() {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        var employee1 = session.find(Employee.class, 1L);
        System.out.println("Employee 1: " + employee1.toString());
    }

    @Test
    void findByCategory() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = "select e from Employee e where e.category = :category";
        session.createQuery(jpql, Employee.class)
            .setParameter("category", EmployeeCategory.MANAGER)
            .getResultList()
            .forEach(System.out::println);
        
    }

    void insertData() {
        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        var employee1 = new Employee("111A", "Empleado 1", 20, EmployeeCategory.JUNIOR);
        var employee2 = new Employee("222B", "Empleado 2", 30, EmployeeCategory.SENIOR);
        var employee3 = new Employee("333C", "Empleado 3", 40, EmployeeCategory.MANAGER);
        var employee4 = new Employee("444D", "Empleado 4", 50, EmployeeCategory.C_LEVEL);

        session.persist(employee1);
        session.persist(employee2);
        session.persist(employee3);
        session.persist(employee4);

        session.getTransaction().commit();
    }

}
