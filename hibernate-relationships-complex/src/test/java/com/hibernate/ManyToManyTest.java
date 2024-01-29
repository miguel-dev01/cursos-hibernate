package com.hibernate;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.hibernate.model.manytomany.*;

public class ManyToManyTest {

    @Test
    void test() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = "SELECT u FROM User u JOIN u.projects p WHERE p.prefix LIKE :prefix";

        var query = session.createQuery(jpql, User.class);
        query.setParameter("prefix", "%PRJ1%");
        query.getResultList().forEach(System.out::println);
    }

    void insertData() {
        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        var project1 = new Project("project1", "PRJ1");
        var project2 = new Project("project2", "PRJ2");

        var user1 = new User("user1@email.com");
        var user2 = new User("user2@email.com");
        var user3 = new User("user3@email.com");
        var user4 = new User("user4@email.com");
        var user5 = new User("user4@email.com");

        var roleDeveloper = new Role("DEVELOPER");
        var roleScrumMaster = new Role("SCRUM_MASTER");
        var roleProdOwner = new Role("PRODUCT_OWNER");

        session.persist(project1);
        session.persist(project2);
        session.persist(user1);
        session.persist(user2);
        session.persist(user3);
        session.persist(user4);
        session.persist(user5);
        session.persist(roleDeveloper);
        session.persist(roleScrumMaster);
        session.persist(roleProdOwner);

        var projectUser1 = new UserProjects(project1, user1, roleDeveloper, LocalDate.of(2020, 1, 1), LocalDate.of(2020, 2, 15));
        var projectUser2 = new UserProjects(project1, user2, roleDeveloper, LocalDate.of(2020, 1, 1), LocalDate.of(2020, 2, 15));
        var projectUser3 = new UserProjects(project1, user3, roleScrumMaster, LocalDate.of(2020, 1, 1), LocalDate.of(2020, 2, 15));
        var projectUser4 = new UserProjects(project1, user4, roleProdOwner, LocalDate.of(2020, 1, 1), LocalDate.of(2020, 2, 15));
        var projectUser5 = new UserProjects(project2, user5, roleProdOwner, LocalDate.of(2020, 1, 1), LocalDate.of(2020, 2, 15));

        session.persist(projectUser1);
        session.persist(projectUser2);
        session.persist(projectUser3);
        session.persist(projectUser4);
        session.persist(projectUser5);

        session.getTransaction().commit();
        session.close();
    }


}
