package com.example.finalproyect.registro;

public class Registro {
    private int id;
    private String fecha_ingreso;
    private String fecha_salida;
    private int id_espacio;
    private String patente_vehiculo;
    private int id_usuario;



    @Override
    public String toString() {
        return "Registro{" +
                "id=" + id +
                ", fecha_ingreso='" + fecha_ingreso + '\'' +
                ", fecha_salida='" + fecha_salida + '\'' +
                ", id_espacio=" + id_espacio +
                ", patente_vehiculo='" + patente_vehiculo + '\'' +
                ", id_usuario=" + id_usuario  +
                '}';
    }

    public Registro(int id, String fecha_ingreso, String fecha_salida, int id_espacio, String patente_vehiculo, int id_usuario) {
        this.id = id;
        this.fecha_ingreso = fecha_ingreso;
        this.fecha_salida = fecha_salida;
        this.id_espacio = id_espacio;
        this.patente_vehiculo = patente_vehiculo;
        this.id_usuario = id_usuario;

    }

    public Registro() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(String fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public String getFecha_salida() {
        return fecha_salida;
    }

    public void setFecha_salida(String fecha_salida) {
        this.fecha_salida = fecha_salida;
    }

    public int getId_espacio() {
        return id_espacio;
    }

    public void setId_espacio(int id_espacio) {
        this.id_espacio = id_espacio;
    }

    public String getPatente_vehiculo() {
        return patente_vehiculo;
    }

    public void setPatente_vehiculo(String patente_vehiculo) {
        this.patente_vehiculo = patente_vehiculo;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }


}

