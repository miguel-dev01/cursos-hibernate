package com.hibernate;

import org.junit.jupiter.api.Test;
import com.hibernate.model.example2.*;


public class EmbeddedIdTest {

    @Test
    void test() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        var companyPK1 = new CompanyPK("cif1", "brand1");
        var c1 = session.find(Company.class, companyPK1);
        System.out.println(c1);

        var companyPK2 = new CompanyPK("cif1", "brand2");
        var c2 = session.find(Company.class, companyPK2);
        System.out.println(c2);

    }

    void insertData() {
        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        // Marcas distintas pero de la misma empresa
        var companyPK1 = new CompanyPK("cif1", "brand1");
        var companyPK2 = new CompanyPK("cif1", "brand2");

        var company1 = new Company(companyPK1, "Madrid", 100);
        var company2 = new Company(companyPK2, "Valencia", 200);

        session.persist(company1);
        session.persist(company2);

        session.getTransaction().commit();

    }

}
