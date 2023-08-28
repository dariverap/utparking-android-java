package com.example.finalproyect.Espacio;

public class Espacio {
    private int id;
    private int numero;
    private int disponible;

    public Espacio(int id, int numero, int disponible) {
        this.id = id;
        this.numero = numero;
        this.disponible = disponible;
    }

    public Espacio() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getDisponible() {
        return disponible;
    }

    public void setDisponible(int disponible) {
        this.disponible = disponible;
    }
}

