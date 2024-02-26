package com.hibernate.model;

import jakarta.persistence.*;

@Entity(name = "persona")
// Inherintance signigca que la clase padre es abstracta
// y que las clases hijas son concretas
// InheritanceType.JOINED significa que se creara una tabla por cada clase
// y que se relacionaran por medio de llaves foraneas
@Inheritance(strategy = InheritanceType.JOINED)
public class Persona {

    @Id
    @Column(name = "nss")
    private String nss;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "direccion")
    private String direccion;

    public Persona() {
    }

    public Persona(String nss, String nombre, String direccion) {
        this.nss = nss;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "nss='" + nss + '\'' +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }

}
