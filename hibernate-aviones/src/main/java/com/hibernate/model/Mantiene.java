package com.hibernate.model;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "mantiene")
public class Mantiene {

    @Id
    @Column(name = "cod_trabajo")
    private String codTrabajo;

    @Id
    @Column(name = "nss_mecanico", insertable = false, updatable = false)
    private String nssMecanico;

    @Id
    @Column(name = "num_registro_avion", insertable = false, updatable = false)
    private String numRegistroAvion;

    private LocalDate fecha;

    private Integer numHoras;

    public Mantiene() {
    }

    public Mantiene(String codTrabajo, String nssMecanico, String numRegistroAvion, 
            LocalDate fecha, Integer numHoras) {
        this.codTrabajo = codTrabajo;
        this.nssMecanico = nssMecanico;
        this.numRegistroAvion = numRegistroAvion;
        this.fecha = fecha;
        this.numHoras = numHoras;
    }

    public String getCodTrabajo() {
        return codTrabajo;
    }

    public void setCodTrabajo(String codTrabajo) {
        this.codTrabajo = codTrabajo;
    }

    public String getNssMecanico() {
        return nssMecanico;
    }

    public void setNssMecanico(String nssMecanico) {
        this.nssMecanico = nssMecanico;
    }

    public String getNumRegistroAvion() {
        return numRegistroAvion;
    }

    public void setNumRegistroAvion(String numRegistroAvion) {
        this.numRegistroAvion = numRegistroAvion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Integer getNumHoras() {
        return numHoras;
    }

    public void setNumHoras(Integer numHoras) {
        this.numHoras = numHoras;
    }

    @Override
    public String toString() {
        return "Mantiene [codTrabajo=" + codTrabajo + ", nssMecanico=" + nssMecanico + ", numRegistroAvion="
                + numRegistroAvion + ", fecha=" + fecha + ", numHoras=" + numHoras + "]";
    }

}
