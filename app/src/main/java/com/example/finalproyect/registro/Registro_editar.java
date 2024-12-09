package com.example.finalproyect.registro;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalproyect.R;
import com.example.finalproyect.RetrofitClient;
import com.example.finalproyect.WebService;
import com.example.finalproyect.databinding.FragmentRegistroEditarBinding;
import com.example.finalproyect.databinding.FragmentRegistroRegistroBinding;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class Registro_editar extends Fragment {

    FragmentRegistroEditarBinding binding;

    RegistroAdapter adaptador;

    Registro registro = new Registro();

    Retrofit retrofit = new RetrofitClient().getRetrofit();
    WebService webService = retrofit.create(WebService.class);

    EditText etIdEspacio, etPatenteVehiculo, etIdUsuario, etIdTarifa, etCostoTotal,etFechaSalida,etFechaIngreso;
    Button btnActualizar;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public Registro_editar() {
        // Required empty public constructor
    }


    public static Registro_editar newInstance(String param1, String param2) {
        Registro_editar fragment = new Registro_editar();
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
            registro.setId(getArguments().getInt("idRegistro"));
            registro.setFecha_ingreso(getArguments().getString("fechaIngreso"));
            registro.setFecha_salida(getArguments().getString("fechaSalida"));
            registro.setId_espacio(getArguments().getInt("idEspacio"));
            registro.setPatente_vehiculo(getArguments().getString("patenteVehiculo"));
            registro.setId_usuario(getArguments().getInt("idUsuario"));

        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegistroEditarBinding.inflate(inflater, container, false);
        etFechaIngreso=binding.etFechaIngreso;
        etFechaSalida=binding.etFechaSalida;
        etIdEspacio = binding.etIdEspacio;
        etPatenteVehiculo = binding.etPatenteVehiculo;
        etIdUsuario = binding.etIdUsuario;
        etIdTarifa = binding.etTarifa;
        etCostoTotal = binding.etCostoTotal;
        btnActualizar = binding.btnActualizar;

        etFechaIngreso.setText(registro.getFecha_ingreso());
        etFechaSalida.setText(registro.getFecha_salida());
        etIdEspacio.setText(String.valueOf(registro.getId_espacio()));
        etPatenteVehiculo.setText(registro.getPatente_vehiculo());
        etIdUsuario.setText(String.valueOf(registro.getId_usuario()));



        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarRegistro();
            }
        });

        return binding.getRoot();


    }

    public void actualizarRegistro(){

        //this.registro.setId(-1);


        Calendar calendar = Calendar.getInstance(); // Crea una instancia de Calendar con la hora y fecha actual del sistema
        Date fechaActual = calendar.getTime(); // Obtiene la fecha y hora actual del sistema como objeto Date
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        String fechaFormateada = formatter.format(fechaActual); // Formatea la fecha y hora actual en el formato deseado
        this.registro.setFecha_ingreso(fechaFormateada);
        this.registro.setFecha_salida(fechaFormateada);

        this.registro.setId_espacio(Integer.valueOf(binding.etIdEspacio.getText().toString()));
        this.registro.setPatente_vehiculo(binding.etPatenteVehiculo.getText().toString());
        this.registro.setId_usuario(Integer.valueOf(binding.etIdUsuario.getText().toString()));

        Log.i("Etiqueta", String.valueOf(fechaFormateada));


        Call<String> call = webService.actualizarRegistro(this.registro.getId(),this.registro);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                FancyToast.makeText(requireContext(),"Registro actualizado correctamente!",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                //Toast.makeText(requireContext(), response.body().toString(), Toast.LENGTH_LONG).show();
                //obtenerRegistros();
                //limpiarCampos();
                //limpiarObjeto();
                irListaRegistros();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                FancyToast.makeText(requireContext(),"ERROR ADD!",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                //Toast.makeText(requireContext(), "ERROR ADD", Toast.LENGTH_LONG).show();
            }
        });

    }
    public void irListaRegistros(){
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame1, new Registro_fragment_lista());
        fragmentTransaction.commit();
    }
}