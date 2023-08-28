package com.example.finalproyect.tarifa;

public class Tarifa {
    private int id;
    private String descripcion;
    private double costo_hora;

    public String toString() {
        return "Tarifa{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", costo_hora'" + costo_hora +  +
                '}';
    }

    public Tarifa(int id, String descripcion, double costo_hora) {
        this.id = id;
        this.descripcion = descripcion;
        this.costo_hora = costo_hora;
    }

    public Tarifa() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getCosto_hora() {
        return costo_hora;
    }

    public void setCosto_hora(double costo_hora) {
        this.costo_hora = costo_hora;
    }
}
