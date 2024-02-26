package com.hibernate.model;

import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "mecanico")
public class Mecanico extends Persona {

    @Column(name = "salario")
    private Double salario;

    @Column(name = "turno")
    private String turno;

    @ManyToMany(mappedBy = "mecanicos")
    private Set<Avion> aviones;

    @ManyToMany
    @JoinTable(name = "cualifica",
            joinColumns = @JoinColumn(name = "nss_mecanico"),
            inverseJoinColumns = @JoinColumn(name = "modelo"))
    private Set<Tipo> tipos;

    public Mecanico() {
    }

    public Mecanico(String nss, String nombre, String direccion, 
            Double salario, String turno) {
        super(nss, nombre, direccion);
        this.salario = salario;
        this.turno = turno;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    @Override
    public String toString() {
        return "Mecanico{" +
                "salario=" + salario +
                ", turno='" + turno + '\'' +
                '}';
    }
}
