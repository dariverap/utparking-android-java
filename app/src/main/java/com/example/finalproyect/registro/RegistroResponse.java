package com.example.finalproyect.registro;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RegistroResponse {
    @SerializedName("listaRegistros")
    ArrayList<Registro> listaRegistros;
}
