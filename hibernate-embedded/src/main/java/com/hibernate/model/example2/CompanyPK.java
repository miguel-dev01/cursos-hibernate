package com.hibernate.model.example2;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class CompanyPK implements Serializable {

    private static final long serialVersionUID = -5809782578272943999L;
    
    // Son claves compuestas de base de datos
    private String cif;
    private String brand;

    public CompanyPK() {
    }

    public CompanyPK(String cif, String brand) {
        this.cif = cif;
        this.brand = brand;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "CompanyPK [cif=" + cif + ", brand=" + brand + "]";
    }

}
