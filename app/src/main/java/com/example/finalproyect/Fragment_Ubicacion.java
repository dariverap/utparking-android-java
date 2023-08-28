package com.example.finalproyect;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.net.URI;


public class Fragment_Ubicacion extends Fragment {

    Button btn_ubicacion;

    String _url = "https://www.google.com/maps/place/Estacionamiento+SIMA/@-12.0416955,-77.2896951,12z/data=!4m10!1m2!2m1!1sSIMA+PARKING!3m6!1s0x9105cdeef8c72e01:0x1ac2d789635b98fb!8m2!3d-12.0416955!4d-77.1372598!15sCgxTSU1BIFBBUktJTkeSAQtwYXJraW5nX2xvdOABAA!16s%2Fg%2F11r8wv9835?hl=es-419";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment__ubicacion, container, false);

        btn_ubicacion = v.findViewById(R.id.btn_ubicacion);

        btn_ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri _link = Uri.parse(_url);
                Intent i = new Intent(Intent.ACTION_VIEW,_link);
                startActivity(i);

            }
        });


        return v;
    }
}