package com.example.finalproyect.Espacio;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class EspacioResponse {

    @SerializedName("listaEspacios")
    public ArrayList<Espacio> listaEspacios;

    public ArrayList<Espacio> getListaEspacios() {
        return listaEspacios;
    }
}