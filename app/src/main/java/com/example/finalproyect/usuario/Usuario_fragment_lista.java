package com.example.finalproyect.usuario;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.finalproyect.R;
import com.example.finalproyect.RetrofitClient;
import com.example.finalproyect.WebService;
import com.example.finalproyect.databinding.FragmentUsuarioListaBinding;
import com.example.finalproyect.rol.Rol;
import com.example.finalproyect.rol.RolResponse;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class Usuario_fragment_lista extends Fragment implements UsuarioAdapter.OnItemClicked {

    FragmentUsuarioListaBinding binding;
    UsuarioAdapter adaptador;
    ArrayList<Usuario> listaUsuarios = new ArrayList<>();

    Usuario usuario = new Usuario();

    Boolean isEditando = false;
    Retrofit retrofit = new RetrofitClient().getRetrofit();
    WebService webService = retrofit.create(WebService.class);
    ArrayList<Rol> listaRoles = new ArrayList<>();
    public Usuario_fragment_lista(){

    }


    public void obtenerUsuarios() {
        Call<UsuarioResponse> call = webService.obtenerUsuarios();
        call.enqueue(new Callback<UsuarioResponse>() {
            @Override
            public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
                listaUsuarios = response.body().listaUsuarios;

                setupRecyclerView();
            }

            @Override
            public void onFailure(Call<UsuarioResponse> call, Throwable t) {
                FancyToast.makeText(requireContext(),"Error al consultar todos los usuarios !",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                //Toast.makeText(requireContext(), "ERROR AL CONSULTAR TODOS LOS REGISTROS", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setupRecyclerView() {
        adaptador = new UsuarioAdapter(requireActivity(), listaUsuarios);
        adaptador.setOnClick(this);
        binding.rvUsuarios.setAdapter(adaptador);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUsuarioListaBinding.inflate(inflater,container,false);
        binding.rvUsuarios.setLayoutManager(new LinearLayoutManager(requireContext()));
        //setupRecyclerView();
//        obtenerRoles();
        obtenerUsuarios();

        return binding.getRoot();
    }

    @Override
    public void editarUsuario(Usuario usuario) {
        // Crear un nuevo fragment de formulario para editar el registro
        Usuario_editar fragment = new Usuario_editar();

        // Configurar los argumentos del fragment con los datos del registro
        Bundle args = new Bundle();
        args.putInt("IdUsuario", usuario.getId());
        args.putString("nombre", usuario.getNombre());
        args.putString("apellido", usuario.getApellido());
        args.putString("correo", usuario.getCorreo());
        args.putString("contrasena", usuario.getContrasena());
        args.putInt("idRol", usuario.getId_rol());
        fragment.setArguments(args);

        // Abrir el nuevo fragment
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame1, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void borrarUsuario(int idUsuario) {
        Call<String> call = webService.borrarUsuario(idUsuario);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                FancyToast.makeText(requireContext(),response.body(),FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                //Toast.makeText(requireContext(), response.body().toString(), Toast.LENGTH_LONG).show();
                obtenerUsuarios();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                FancyToast.makeText(requireContext(),"Error al borrar usuario !",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                //Toast.makeText(requireContext(), "ERROR DELETE", Toast.LENGTH_LONG).show();
            }
        });
    }



}