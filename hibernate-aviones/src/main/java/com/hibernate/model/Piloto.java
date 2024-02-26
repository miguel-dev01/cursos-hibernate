package com.hibernate.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "piloto")
public class Piloto extends Persona {

    @Column(name = "licencia")
    private String licencia;

    @ManyToMany
    @JoinTable(
            name = "pilota",
            joinColumns = @JoinColumn(name = "piloto_nss"),
            inverseJoinColumns = @JoinColumn(name = "tipo_modelo")
    )
    private Set<Tipo> tipos = new HashSet<>();

    public Piloto() {
    }

    public Piloto(String nss, String nombre, String direccion, String licencia) {
        super(nss, nombre, direccion);
        this.licencia = licencia;
    }

    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }

    public Set<Tipo> getTipos() {
        return tipos;
    }

    public void setTipos(Set<Tipo> tipos) {
        this.tipos = tipos;
    }

    @Override
    public String toString() {
        return "Piloto{" +
                "licencia='" + licencia + '\'' +
                '}';
    }

}
