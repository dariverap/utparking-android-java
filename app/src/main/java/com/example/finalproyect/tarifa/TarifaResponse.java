package com.example.finalproyect.tarifa;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class TarifaResponse {

    @SerializedName("listaTarifas")
    public ArrayList<Tarifa> listaTarifas;

    public ArrayList<Tarifa> getListaTarifas() {
        return listaTarifas;
    }

}
