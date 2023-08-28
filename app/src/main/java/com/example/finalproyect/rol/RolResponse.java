package com.example.finalproyect.rol;

import com.example.finalproyect.Espacio.Espacio;
import com.example.finalproyect.registro.Registro;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RolResponse {

    @SerializedName("listaRoles")
    ArrayList<Rol> listaRoles;

    public ArrayList<Rol> getListaRoles() {
        return listaRoles;
    }
}
