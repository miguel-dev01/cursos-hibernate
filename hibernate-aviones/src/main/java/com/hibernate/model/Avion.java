package com.hibernate.model;

import java.time.LocalDate;
import java.util.Set;

import org.hibernate.annotations.NaturalId;
import jakarta.persistence.*;

@Entity
@Table(name = "aviones")
public class Avion {

    @Id
    @Column(name = "num_registro")
    private String numRegistro;

    @NaturalId
    @Column(name = "matricula")
    private String matricula;

    @Column(name = "fecha_reg")
    private LocalDate fechaReg;

    @Column(name = "fecha_construccion")
    private LocalDate fechaConstruccion;

    @ManyToOne
    @JoinColumn(name = "tipo_modelo", nullable = false)
    private Tipo tipo;

    @ManyToOne
    @JoinColumn(name = "cod_hangar", nullable = false)
    private Hangar hangar;

    @ManyToMany
    @JoinTable(name = "mantiene", 
            joinColumns = @JoinColumn(name = "num_registro_avion"), 
            inverseJoinColumns = @JoinColumn(name = "nss_mecanico"))
    private Set<Mecanico> mecanicos;
    
    public Avion(String numRegistro, String matricula, 
            LocalDate fechaReg, LocalDate fechaConstruccion, 
            Hangar hangar, Tipo tipo) {
        this.numRegistro = numRegistro;
        this.matricula = matricula;
        this.fechaReg = fechaReg;
        this.fechaConstruccion = fechaConstruccion;
        this.tipo = tipo;
        this.hangar = hangar;
    }

    public String getNumRegistro() {
        return numRegistro;
    }

    public void setNumRegistro(String numRegistro) {
        this.numRegistro = numRegistro;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public LocalDate getFechaReg() {
        return fechaReg;
    }

    public void setFechaReg(LocalDate fechaReg) {
        this.fechaReg = fechaReg;
    }

    public LocalDate getFechaConstruccion() {
        return fechaConstruccion;
    }

    public void setFechaConstruccion(LocalDate fechaConstruccion) {
        this.fechaConstruccion = fechaConstruccion;
    }

    @Override
    public String toString() {
        return "Avion [numRegistro=" + numRegistro + ", matricula=" + matricula + ", fechaReg=" + fechaReg
                + ", fechaConstruccion=" + fechaConstruccion + "]";
    }

}
