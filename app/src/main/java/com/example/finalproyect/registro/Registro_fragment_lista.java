package com.example.finalproyect.registro;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.finalproyect.Primer_fragment;
import com.example.finalproyect.R;
import com.example.finalproyect.RetrofitClient;
import com.example.finalproyect.WebService;
import com.example.finalproyect.databinding.FragmentRegistroListaBinding;
import com.example.finalproyect.usuario.Usuario;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class Registro_fragment_lista extends Fragment implements RegistroAdapter.OnItemClicked {

    FragmentRegistroListaBinding binding;
    RegistroAdapter adaptador;
    Retrofit retrofit = new RetrofitClient().getRetrofit();
    WebService webService = retrofit.create(WebService.class);

    private static final String ARG_USER_ID = "user_id";
    private int userId;

    public static Registro_fragment_lista newInstance(int userId) {
        Registro_fragment_lista fragment = new Registro_fragment_lista();
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getInt(ARG_USER_ID);
        }
    }

    public void obtenerRegistros() {
        Call<RegistroResponse> call = webService.obtenerRegistros(userId); // Llama al método con el ID del usuario
        call.enqueue(new Callback<RegistroResponse>() {
            @Override
            public void onResponse(Call<RegistroResponse> call, Response<RegistroResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                    if (response.body().listaRegistros != null && !response.body().listaRegistros.isEmpty()) {
                        adaptador.setListaRegistros(response.body().listaRegistros);
                        adaptador.notifyDataSetChanged(); // Notifica al RecyclerView
                    } else {
                        FancyToast.makeText(requireContext(), "No hay registros disponibles.", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
                    }
                } else {
                    FancyToast.makeText(requireContext(), "Error al obtener registros.", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                }
            }

            @Override
            public void onFailure(Call<RegistroResponse> call, Throwable t) {
                //FancyToast.makeText(requireContext(), "ERROR AL CONSULTAR TODOS LOS REGISTROS"+userId, FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegistroListaBinding.inflate(inflater, container, false);
        binding.rvRegistro.setLayoutManager(new LinearLayoutManager(requireContext()));

        adaptador = new RegistroAdapter(requireActivity(), new ArrayList<>(), this); // Pasa "this" como OnItemClicked
        binding.rvRegistro.setAdapter(adaptador);

        obtenerRegistros(); // Cargar registros al inicio

        return binding.getRoot();
    }




    @Override
    public void borrarRegistro(int idRegistro) {
        Call<String> call = webService.actualizarRegistro2(idRegistro);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                // Mostrar el mensaje de respuesta
                FancyToast.makeText(requireContext(), response.body(), FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();

                // Después de borrar el registro, actualizamos el estado del usuario a 3
                Usuario usuario = new Usuario(userId, "", "", "", "", 0);  // Crear objeto Usuario con el idUsuario
                //usuario.(1);  // Actualizar el estado del usuario a 3 (asumiendo que estado está en id_rol o similar)

                // Llamar al servicio para actualizar el estado del usuario
                Call<String> actualizarEstadoCall = webService.actualizarEstadoUsuario(usuario);
                actualizarEstadoCall.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            FancyToast.makeText(requireContext(), "Estado del usuario actualizado a 3", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                        } else {
                            FancyToast.makeText(requireContext(), "Error al actualizar el estado del usuario", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        FancyToast.makeText(requireContext(), "Error al realizar la solicitud para actualizar el estado", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                    }
                });

                // Redirigir a los registros
                irRegistros();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // En caso de error al borrar el registro
                Usuario usuario = new Usuario(userId, "", "", "", "", 0);  // Crear objeto Usuario con el idUsuario
                //usuario.(1);  // Actualizar el estado del usuario a 3 (asumiendo que estado está en id_rol o similar)

                // Llamar al servicio para actualizar el estado del usuario
                Call<String> actualizarEstadoCall = webService.actualizarEstadoUsuario(usuario);
                actualizarEstadoCall.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            FancyToast.makeText(requireContext(), "Estado del usuario actualizado a 3", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                        } else {
                            FancyToast.makeText(requireContext(), "Error al actualizar el estado del usuario", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        FancyToast.makeText(requireContext(), "Error al realizar la solicitud para actualizar el estado", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                    }
                });
                //FancyToast.makeText(requireContext(), "Error al borrar el registro", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                irRegistros();
            }
        });
    }


    public void irRegistros(){


        Primer_fragment miFragmento = Primer_fragment.newInstance(userId);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame1, miFragmento);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


}