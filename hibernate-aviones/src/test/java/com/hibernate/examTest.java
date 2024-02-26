package com.hibernate;

import com.github.javafaker.Faker;
import com.hibernate.model.*;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;

import java.util.List;
import java.util.Random;

public class examTest {

    private static Faker faker = new Faker();

    @Test
    void insertDataTest() {
        insertData();
    }

    private void insertData() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Random random = new Random();

        String tipoTurnos[] = {"Mañana", "Tarde", "Noche"};

        // Insertar 10 tipos
        for (int i = 0; i < 10; i++) {
            Tipo tipo = new Tipo(
                    faker.letterify("????"),
                    faker.number().numberBetween(100, 300),
                    faker.number().randomDouble(2,10000, 50000)
            );
            session.persist(tipo);
        }

        List<Tipo> tipos = session.createQuery("select t from Tipo t", Tipo.class).getResultList();

        // Insertar 10 mecánicos
        for (int i = 0; i < 10; i++) {
            Mecanico mecanico = new Mecanico(
                    faker.number().digits(12),
                    faker.name().fullName(),
                    faker.address().fullAddress(),
                    faker.number().randomDouble(2,1000, 2000),
                    tipoTurnos[random.nextInt(tipoTurnos.length)]
            );
            session.persist(mecanico);
        }

        List<Mecanico> mecanicos = session.createQuery("select m from Mecanico m", Mecanico.class).getResultList();

        // Insertar 10 propietarios
        for (int i = 0; i < 10; i++) {
            Propietario propietario = new Propietario(
                    faker.number().digits(12),
                    faker.name().fullName(),
                    faker.address().fullAddress(),
                    faker.phoneNumber().cellPhone()
            );
            session.persist(propietario);
        }

        List<Propietario> propietarios = session.createQuery("select p from Propietario p", Propietario.class).getResultList();

        // Insertar 10 pilotos
        for (int i = 0; i < 10; i++) {
            Piloto piloto = new Piloto(
                    faker.number().digits(12),
                    faker.name().fullName(),
                    faker.address().fullAddress(),
                    faker.number().digits(8)
            );

            for (int j = 0; j < faker.random().nextInt(1,4); j++) {
                piloto.getTipos().add(tipos.get(random.nextInt(tipos.size())));
            }

            session.persist(piloto);
        }

        List<Piloto> pilotos = session.createQuery("select p from Piloto p", Piloto.class).getResultList();

        // Insertar 10 hangares
        for (int i = 0; i < 10; i++) {
            Hangar hangar = new Hangar(
                    faker.letterify("????"),
                    faker.number().numberBetween(0, 10),
                    faker.address().cityName()
            );
            session.persist(hangar);
        }

        List<Hangar> hangares = session.createQuery("select h from Hangar h", Hangar.class).getResultList();

        // Insertar 100 aviones
        for (int i = 0; i < 100; i++) {
            Avion avion = new Avion(
                    faker.number().digits(6),
                    faker.letterify("???")+faker.numerify("###"),
                    convertToLocalDate(faker.date().birthday(1, 5)),
                    convertToLocalDate(faker.date().birthday(2, 10)),
                    hangares.get(random.nextInt(hangares.size())),
                    tipos.get(random.nextInt(tipos.size()))
            );
            session.persist(avion);
        }

        List<Avion> aviones = session.createQuery("select a from Avion a", Avion.class).getResultList();

        //Insertar 100 mantenimientos
        for (int i = 0; i < 100; i++) {
            Mantiene mantiene = new Mantiene(
                    faker.letterify("??")+faker.numerify("##"),
                    mecanicos.get(random.nextInt(mecanicos.size())).getNss(),
                    aviones.get(random.nextInt(aviones.size())).getNumRegistro(),
                    convertToLocalDate(faker.date().birthday(0, 2)),
                    faker.number().numberBetween(1, 10)
            );

            session.persist(mantiene);
        }

        //Insertar 100 compras
        aviones.forEach(avion -> {
            Compra compra = new Compra(
                    avion,
                    propietarios.get(random.nextInt(propietarios.size())),
                    convertToLocalDate(faker.date().birthday(0, 20))
            );
            session.persist(compra);
        });

        session.getTransaction().commit();
    }

    private LocalDate convertToLocalDate(java.util.Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.of("Europe/Madrid"))
                .toLocalDate();
    }

}