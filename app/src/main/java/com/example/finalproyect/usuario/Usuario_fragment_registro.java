package com.example.finalproyect.usuario;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.databinding.DataBindingUtil;

import com.example.finalproyect.MainActivity;
import com.example.finalproyect.R;
import com.example.finalproyect.RetrofitClient;
import com.example.finalproyect.WebService;
import com.example.finalproyect.databinding.FragmentUsuarioRegistroBinding;
import com.example.finalproyect.rol.Rol;
import com.example.finalproyect.rol.RolResponse;
import com.example.finalproyect.tarifa.Tarifa;
import com.example.finalproyect.tarifa.TarifaResponse;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Usuario_fragment_registro extends Fragment {

    FragmentUsuarioRegistroBinding binding;

    UsuarioAdapter adaptador;

    ArrayList<Usuario> listaUsuarios = new ArrayList<>();
    ArrayList<Rol> listaRoles = new ArrayList<>();
    Usuario usuario = new Usuario(-1, "", "", "", "", -1);

    Boolean isEditando = false;
    Spinner sRoles;
    Retrofit retrofit = new RetrofitClient().getRetrofit();
    WebService webService = retrofit.create(WebService.class);

    public Usuario_fragment_registro(){

    }

    public void agregarUsuario(){

        this.usuario.setId(-1);
        this.usuario.setNombre(binding.etNombre.getText().toString());
        this.usuario.setApellido(binding.etApellido.getText().toString());
        this.usuario.setCorreo(binding.etEmail.getText().toString());
        this.usuario.setContrasena(binding.etContrasena.getText().toString());
        //this.usuario.setId_rol(Integer.valueOf(binding.etIdRol.getText().toString()));
        Log.i("Etiqueta", (this.usuario.toString()));

        Call<String> call = webService.agregarUsuario(this.usuario);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                FancyToast.makeText(requireContext(),response.body(),FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                //Toast.makeText(requireContext(), response.body().toString(), Toast.LENGTH_LONG).show();
                //obtenerRegistros();
                limpiarCampos();
                limpiarObjeto();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                FancyToast.makeText(requireContext(),"Error al agregar usuario !",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                //Toast.makeText(requireContext(), "ERROR ADD", Toast.LENGTH_LONG).show();
            }
        });
    }

    public Boolean validarCampos() {
        return !(binding.etNombre.getText().toString().equals("") || binding.etEmail.getText().toString().equals(""));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentUsuarioRegistroBinding.inflate(inflater, container, false);
        sRoles = binding.sRoles;
        // Obtener referencia al botón
        Button btnAddUpdate = binding.btnAddUpdate;

        obtenerRoles();
        sRoles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Obtener texto seleccionado del Spinner
                String rolSeleccionado = parent.getItemAtPosition(position).toString();
                // Buscar el rol seleccionado en la lista de roles y asignar su id a la propiedad id_rol del objeto registro
                for (Rol rol : listaRoles) {
                    if (rol.getNombre().equals(rolSeleccionado)) {
                        usuario.setId_rol(rol.getId());
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO: Implementar método necesario
            }
        });
        // Agregar OnClickListener al botón
        btnAddUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarCampos()){
                    agregarUsuario();
                }else {
                    FancyToast.makeText(getContext(),"Porfavor llena todos los campos !",FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();
                    //Toast.makeText(getContext(), "Porfavor llena todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return binding.getRoot();
    }
    public void obtenerRoles() {
        Call<RolResponse> call = webService.obtenerRoles();
        call.enqueue(new Callback<RolResponse>() {
            @Override
            public void onResponse(Call<RolResponse> call, Response<RolResponse> response) {
                if (response.isSuccessful()) {
                    RolResponse rolResponse = response.body();
                    if (rolResponse != null) {
                        listaRoles = rolResponse.getListaRoles();

                        // Actualizar las opciones del Spinner "sRoles"
                        List<String> opciones = new ArrayList<>();
                        for (Rol rol : listaRoles) {
                            opciones.add(rol.getNombre());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, opciones);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        sRoles.setAdapter(adapter);
                    }
                } else {
                    FancyToast.makeText(requireContext(),"Error al consultar todos los roles !",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                    //Toast.makeText(requireContext(), "ERROR AL CONSULTAR TODOS LOS ROLES", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RolResponse> call, Throwable t) {
                FancyToast.makeText(requireContext(),"Error al consultar todos los roles !",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                //Toast.makeText(requireContext(), "ERROR AL CONSULTAR TODOS LOS ROLES", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void limpiarCampos() {
        binding.etNombre.setText("");
        binding.etApellido.setText("");
        binding.etEmail.setText("");
        binding.etContrasena.setText("");
        //binding.etIdRol.setText("");
    }

    public void limpiarObjeto() {
        this.usuario.setId(-1);
        this.usuario.setNombre("");
        this.usuario.setApellido("");
        this.usuario.setCorreo("");
        this.usuario.setContrasena("");
        this.usuario.setId_rol(-1);
    }

}