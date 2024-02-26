package com.hibernate.model;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity(name = "compra")
public class Compra {

    @Id
    @Column(name = "avion_num_registro")
    private String avionNumRegistro;

    @Id
    @Column(name = "propietario_nss")
    private String propietarioNss;

    @Column(name = "fecha_compra")
    private LocalDate fechaCompra;

    public Compra() {
    }

    public Compra(Avion avion, Propietario propietario, LocalDate convertToLocalDate) {
        this.avionNumRegistro = avion.getNumRegistro();
        this.propietarioNss = propietario.getNss();
        this.fechaCompra = convertToLocalDate;
    }

    public String getAvionNumRegistro() {
        return avionNumRegistro;
    }

    public void setAvionNumRegistro(String avionNumRegistro) {
        this.avionNumRegistro = avionNumRegistro;
    }

    public String getPropietarioNss() {
        return propietarioNss;
    }

    public void setPropietarioNss(String propietarioNss) {
        this.propietarioNss = propietarioNss;
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    @Override
    public String toString() {
        return "Compra{" +
                "avionNumRegistro='" + avionNumRegistro + '\'' +
                ", propietarioNss='" + propietarioNss + '\'' +
                ", fechaCompra=" + fechaCompra +
                '}';
    }

}
