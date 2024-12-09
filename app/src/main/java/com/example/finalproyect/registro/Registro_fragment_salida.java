package com.example.finalproyect.registro;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalproyect.Espacio.Espacio;
import com.example.finalproyect.R;
import com.example.finalproyect.RetrofitClient;
import com.example.finalproyect.WebService;
import com.google.gson.Gson;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class Registro_fragment_salida extends Fragment {
    Retrofit retrofit = new RetrofitClient().getRetrofit();
    WebService webService = retrofit.create(WebService.class);

    RegistroTarifa registroEncontrado= new RegistroTarifa();
    private Button btnBuscar,btnSalida; // referencia al botón
    private EditText etPatente,etIngreso,etPlaca2,etTarifaPrecio,etTotal,etEspacio,etFecha;

    private double total;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public Registro_fragment_salida() {
        // Required empty public constructor
    }

    public static Registro_fragment_salida newInstance(String param1, String param2) {
        Registro_fragment_salida fragment = new Registro_fragment_salida();
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registro_salida, container, false);
        btnBuscar = view.findViewById(R.id.btnBuscar); // obtiene la referencia del botón
        btnSalida = view.findViewById(R.id.btnSalida);
        etPatente = view.findViewById(R.id.etPatente);
        etIngreso = view.findViewById(R.id.etIngreso);
        etPlaca2 = view.findViewById(R.id.etPlaca2);
        etTarifaPrecio = view.findViewById(R.id.etTarifaPrecio);
        etTotal = view.findViewById(R.id.etTotal);
        etEspacio = view.findViewById(R.id.etEspacio);
        //etFecha = view.findViewById(R.id.editTextTime);
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // llama al método obtenerRegistro cuando se presiona el botón
                String patente = etPatente.getText().toString();
                if (patente.isEmpty()) {
                    FancyToast.makeText(getContext(),"INGRESE UNA PATENTE!",FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();
                    return;
                }
                obtenerRegistroPorPatente(patente);
            }
        });


        btnSalida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String patente = etPatente.getText().toString().trim();
                String ingreso = etIngreso.getText().toString().trim();
                String placa2 = etPlaca2.getText().toString().trim();
                String tarifaPrecio = etTarifaPrecio.getText().toString().trim();
                String espacio = etEspacio.getText().toString().trim();

                if (TextUtils.isEmpty(patente)) {
                    etPatente.setError("Debe ingresar una patente");
                    return;
                }

                if (TextUtils.isEmpty(ingreso)) {
                    etIngreso.setError("Debe ingresar una fecha de ingreso");
                    return;
                }

                if (TextUtils.isEmpty(placa2)) {
                    etPlaca2.setError("Debe ingresar una placa");
                    return;
                }

                if (TextUtils.isEmpty(tarifaPrecio)) {
                    etTarifaPrecio.setError("Debe ingresar una tarifa");
                    return;
                }

                if (TextUtils.isEmpty(espacio)) {
                    etEspacio.setError("Debe ingresar un espacio");
                    return;
                }
                Calendar calendar = Calendar.getInstance(); // Crea una instancia de Calendar con la hora y fecha actual del sistema
                Date fechaActual = calendar.getTime(); // Obtiene la fecha y hora actual del sistema como objeto Date
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());

                String fechaFormateada = formatter.format(fechaActual);

                try {
                    int min=calcularDiferenciaMinutos(registroEncontrado.getFecha_ingreso(), fechaFormateada);
                    total = calcularTotal(min);
                    total = Math.round(total * 10.0) / 10.0;
                    etTotal.setText(String.valueOf(total));
                    Log.d("DIFERENCIA DE MINUTOS", String.valueOf(total));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                Registro registroAc = new Registro();
                registroAc.setFecha_salida(fechaFormateada);
                registroAc.setFecha_ingreso(registroEncontrado.getFecha_ingreso());
                registroAc.setId(registroEncontrado.getId());
                registroAc.setId_espacio(registroEncontrado.getId_espacio());
                //registroAc.setId_tarifa(registroEncontrado.getId_tarifa());
                registroAc.setId_usuario(registroEncontrado.getId_usuario());
                registroAc.setPatente_vehiculo(registroEncontrado.getPatente_vehiculo());
                //registroAc.setCosto_total(total);
                Log.e("ACTUALIZAR REGISTRO", registroAc.toString());

                actualizarRegistro(registroAc);
                actualizarEspacio(registroEncontrado.getId_espacio());
            }
        });
        return view;
    }


    private void obtenerRegistroPorPatente(String patente) {
        Call<List<RegistroTarifa>> call = webService.obtenerRegistro(patente);
        call.enqueue(new Callback<List<RegistroTarifa>>() {
            @Override
            public void onResponse(Call<List<RegistroTarifa>> call, Response<List<RegistroTarifa>> response) {
                if (response.isSuccessful()) {
                    List<RegistroTarifa> registros = response.body();
                    if (registros != null && registros.size() > 0) {
                        registroEncontrado = registros.get(0);
                        String fechaString = registroEncontrado.getFecha_ingreso();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                        Date fecha = null;
                        try {
                            fecha = sdf.parse(fechaString);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }

                    // Restar 5 horas a la fecha
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(fecha);
                        calendar.add(Calendar.HOUR_OF_DAY, -5);

                    // Convertir la fecha resultante a String
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                        String fechaFormateada = formatter.format(calendar.getTime());

                // Setear la fecha en el objeto registroEncontrado
                        registroEncontrado.setFecha_ingreso(fechaFormateada);
                        Log.d("REGISTRO_ENCONTRADO", registroEncontrado.toString());
                        actualizarCamposTexto();

                    } else {
                        Log.d("REGISTRO_NO_ENCONTRADO", "No se encontró registro para la patente " + patente);
                    }
                } else {
                    Log.d("ERROR_RESPONSE", "Error al obtener el registro para la patente " + patente + " - Código de error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<RegistroTarifa>> call, Throwable t) {
                Log.e("ERROR_CONEXION", "Error al obtener el registro para la patente " + patente + " - " + t.getMessage());
            }
        });
    }

    private void actualizarCamposTexto() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Date date = Date.from(Instant.parse(registroEncontrado.getFecha_ingreso()));
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).plusHours(5);
                String fecha = dateFormatter.format(localDateTime);
                String hora = timeFormatter.format(localDateTime);

                etIngreso.setText(fecha+" - "+hora);
                etPlaca2.setText(registroEncontrado.getPatente_vehiculo());
                etTarifaPrecio.setText(String.valueOf(registroEncontrado.getCosto_hora()));
                etEspacio.setText(String.valueOf(registroEncontrado.getId_espacio()));

            }
        });
    }

    public int calcularDiferenciaMinutos(String fecha1, String fecha2) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        Date date1 = sdf.parse(fecha1);
        Date date2 = sdf.parse(fecha2);
        long diferenciaMs = date2.getTime() - date1.getTime();
        return (int) TimeUnit.MINUTES.convert(diferenciaMs, TimeUnit.MILLISECONDS);
    }

    public double calcularTotal(int minutos){

        return (registroEncontrado.getCosto_hora()/60)*minutos;
    }

    public void actualizarRegistro(Registro registroActualizado){

        Call<String> call = webService.actualizarRegistro(registroActualizado.getId(),registroActualizado);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                FancyToast.makeText(requireContext(),"Registro actualizado correctamente!",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                //Toast.makeText(requireContext(), response.body().toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                FancyToast.makeText(requireContext(),"ERROR ADD!",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                //Toast.makeText(requireContext(), "ERROR ADD", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void actualizarEspacio(int idEspacio) {
        // Crear objeto espacio con el atributo disponible actualizado
        Espacio espacioActualizado = new Espacio();
        espacioActualizado.setDisponible(1);
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
                    FancyToast.makeText(getActivity(),"Espacio actualizado correctamente!",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                    //Toast.makeText(getActivity(), "Espacio actualizado correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    // Actualización fallida
                    FancyToast.makeText(getActivity(),"Error al actualizar el espacio!",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                    //Toast.makeText(getActivity(), "Error al actualizar el espacio", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Manejar error en la llamada al servicio web
                FancyToast.makeText(getActivity(),"Error en la llamada al servicio web!",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                //Toast.makeText(getActivity(), "Error en la llamada al servicio web", Toast.LENGTH_SHORT).show();
            }
        });
    }

}