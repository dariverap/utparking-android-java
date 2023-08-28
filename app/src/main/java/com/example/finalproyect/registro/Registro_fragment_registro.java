package com.example.finalproyect.registro;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.finalproyect.Espacio.Espacio;
import com.example.finalproyect.Espacio.EspacioResponse;
import com.example.finalproyect.R;
import com.example.finalproyect.RetrofitClient;
import com.example.finalproyect.WebService;
import com.example.finalproyect.databinding.FragmentRegistroRegistroBinding;
import com.example.finalproyect.tarifa.Tarifa;
import com.example.finalproyect.tarifa.TarifaResponse;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class Registro_fragment_registro extends Fragment {

    FragmentRegistroRegistroBinding binding;

    RegistroAdapter adaptador;

    ArrayList<Registro> listaRegistros = new ArrayList<>();
    ArrayList<Tarifa> listaTarifas = new ArrayList<>();
    ArrayList<Espacio> listaEspacios = new ArrayList<>();
    private Spinner sTarifas;
    private Spinner sEspacios;
    Registro registro = new Registro();

    Boolean isEditando = false;

    Retrofit retrofit = new RetrofitClient().getRetrofit();
    WebService webService = retrofit.create(WebService.class);
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public Registro_fragment_registro() {
        // Required empty public constructor
    }


    public static Registro_fragment_registro newInstance(String param1, String param2) {
        Registro_fragment_registro fragment = new Registro_fragment_registro();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        obtenerTarifasDisponibles();
        obtenerEspaciosDisponibles();
    }

    public void agregarRegistro(){

        this.registro.setId(-1);

        Calendar calendar = Calendar.getInstance(); // Crea una instancia de Calendar con la hora y fecha actual del sistema
        Date fechaActual = calendar.getTime(); // Obtiene la fecha y hora actual del sistema como objeto Date

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());


        String fechaFormateada = formatter.format(fechaActual); // Formatea la fecha y hora actual en el formato deseado con la zona horaria UTC

        this.registro.setFecha_ingreso(fechaFormateada);
        this.registro.setFecha_salida("2000-01-01T00:00:00.000Z");


        //this.registro.setId_espacio(Integer.valueOf(binding.etIdEspacio.getText().toString()));
        this.registro.setPatente_vehiculo(binding.etPatenteVehiculo.getText().toString());
        this.registro.setId_usuario(6);
        //this.registro.setId_tarifa(Integer.valueOf(binding.etTarifa.getText().toString()));
        this.registro.setCosto_total(0);



        Call<String> call = webService.agregarRegistro(this.registro);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                FancyToast.makeText(requireContext(), response.body().toString(),FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                //Toast.makeText(requireContext(), response.body().toString(), Toast.LENGTH_LONG).show();
                //obtenerRegistros();
                limpiarCampos();
                limpiarObjeto();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                FancyToast.makeText(requireContext(),"Error add",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                //Toast.makeText(requireContext(), "ERROR ADD", Toast.LENGTH_LONG).show();
            }
        });

        actualizarEspacio(registro.getId_espacio());
        obtenerEspaciosDisponibles();
    }

    public Boolean validarCampos() {
        return !( binding.etPatenteVehiculo.getText().toString().equals(""));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRegistroRegistroBinding.inflate(inflater, container, false);

        // Obtener referencia al botón
        Button btnAddUpdate = binding.btnAddUpdate;

        sTarifas = binding.sTarifas;
        sEspacios = binding.sEspacios;
        Log.i("Etiqueta", (listaTarifas.toString()));

        List<String> opciones = new ArrayList<>();
        // Recorrer la lista de tarifas y agregar los nombres de las opciones a la lista "opciones"
        for (Tarifa tarifa : listaTarifas) {
            opciones.add(tarifa.getDescripcion());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, opciones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sTarifas.setAdapter(adapter);

// Recorrer la lista de tarifas y agregar los nombres de las opciones a la lista "opciones"
//        List<Integer> opcionesEspacio = new ArrayList<>();
//
//        for (Espacio espacio : listaEspacios) {
//            opcionesEspacio.add(espacio.getId());
//        }
//
//        ArrayAdapter<Integer> adapter2 = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, opcionesEspacio);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        sEspacios.setAdapter(adapter2);

        // Manejar selección de Spinner
        sTarifas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Obtener texto seleccionado del Spinner
                String tarifaSeleccionada = parent.getItemAtPosition(position).toString();
                // Buscar la tarifa seleccionada en la lista de tarifas y asignar su id a la propiedad id_tarifa del objeto registro
                for (Tarifa tarifa : listaTarifas) {
                    if (tarifa.getDescripcion().equals(tarifaSeleccionada)) {
                        registro.setId_tarifa(tarifa.getId());
                        break;
                    }
                }
            }



            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No se seleccionó nada, no se hace nada aquí
            }
        });

        //SELECTOR DE SPINNER DE ESPACIOS
        sEspacios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Obtener texto seleccionado del Spinner
                String espacioSeleccionado = parent.getItemAtPosition(position).toString();
                // Buscar la tarifa seleccionada en la lista de tarifas y asignar su id a la propiedad id_tarifa del objeto registro
                for (Espacio espacio : listaEspacios) {
                    if (String.valueOf(espacio.getId()).equals(espacioSeleccionado)) {
                        registro.setId_espacio(espacio.getId());
                        break;
                    }
                }
            }



            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No se seleccionó nada, no se hace nada aquí
            }
        });
        // Agregar OnClickListener al botón
        btnAddUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarCampos()){
                    agregarRegistro();
                }else{
                    FancyToast.makeText(getContext(),"Llena todos los campos",FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();
                }
            }
        });

        return binding.getRoot();

    }
    public void limpiarObjeto() {
        this.registro.setId(-1);
        this.registro.setCosto_total(-1);
        this.registro.setFecha_ingreso("");
        this.registro.setFecha_salida("");
        this.registro.setId_tarifa(-1);
        this.registro.setId_espacio(-1);
        this.registro.setPatente_vehiculo("");

    }
    private void limpiarCampos() {

        binding.etPatenteVehiculo.setText("");



    }

    public void obtenerTarifasDisponibles() {
        Call<TarifaResponse> call = webService.obtenerTarifas();
        call.enqueue(new Callback<TarifaResponse>() {
            @Override
            public void onResponse(Call<TarifaResponse> call, Response<TarifaResponse> response) {
                if (response.isSuccessful()) {
                    TarifaResponse tarifaResponse = response.body();
                    if (tarifaResponse != null) {
                        listaTarifas = tarifaResponse.getListaTarifas();

                        // Actualizar las opciones del Spinner "sEspacios"
                        List<String> opciones = new ArrayList<>();
                        for (Tarifa tarifa : listaTarifas) {
                            opciones.add(tarifa.getDescripcion());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, opciones);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        sTarifas.setAdapter(adapter);
                    }
                } else {
                    FancyToast.makeText(requireContext(),"Error al consultar la lista de tarifas",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                }
            }

            @Override
            public void onFailure(Call<TarifaResponse> call, Throwable t) {
                FancyToast.makeText(requireContext(),"Error al consultar la lista de tarifas",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
            }
        });
    }

    public void obtenerEspaciosDisponibles() {
        Call<EspacioResponse> call = webService.obtenerEspacios();
        call.enqueue(new Callback<EspacioResponse>() {
            @Override
            public void onResponse(Call<EspacioResponse> call, Response<EspacioResponse> response) {
                if (response.isSuccessful()) {
                    EspacioResponse espacioResponse = response.body();
                    if (espacioResponse != null) {
                        listaEspacios = espacioResponse.getListaEspacios();
                        List<Integer> opcionesEspacio = new ArrayList<>();
                        for (Espacio espacio : espacioResponse.getListaEspacios()) {
                            opcionesEspacio.add(espacio.getId());
                        }
                        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, opcionesEspacio);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        sEspacios.setAdapter(adapter);
                    } else {
                        FancyToast.makeText(requireContext(),"La respuesta está vacía!",FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();
                        //Toast.makeText(requireContext(), "La respuesta está vacía", Toast.LENGTH_LONG).show();
                    }
                } else {
                    FancyToast.makeText(requireContext(),"Error en la respuesta del servidor !",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                    //Toast.makeText(requireContext(), "Error en la respuesta del servidor", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<EspacioResponse> call, Throwable t) {
                FancyToast.makeText(requireContext(),"Error al obtener los espacios",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
            }
        });
    }

    private void actualizarEspacio(int idEspacio) {
        // Crear objeto espacio con el atributo disponible actualizado
        Espacio espacioActualizado = new Espacio();
        espacioActualizado.setDisponible(0);
        espacioActualizado.setNumero(idEspacio);
        espacioActualizado.setId(idEspacio);
        // Llamar al servicio web para actualizar el espacio
        Call<String> call = webService.actualizarEspacio(idEspacio, espacioActualizado);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                // Manejar respuesta del servidor (si es exitosa o no)
                if (response.isSuccessful()) {
                    // Actualización exitosa
                    FancyToast.makeText(getActivity(),"Espacio actualizado correctamente !",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                } else {
                    // Actualización fallida

                    FancyToast.makeText(getActivity(),"Error al actualizar el espacio",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Manejar error en la llamada al servicio web
                FancyToast.makeText(getActivity(),"Error en la llamada al servicio web",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
            }
        });
    }



}