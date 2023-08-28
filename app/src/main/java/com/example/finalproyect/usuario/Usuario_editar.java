package com.example.finalproyect.usuario;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.finalproyect.R;
import com.example.finalproyect.RetrofitClient;
import com.example.finalproyect.WebService;
import com.example.finalproyect.databinding.FragmentUsuarioEditarBinding;
import com.example.finalproyect.registro.Registro;
import com.example.finalproyect.registro.RegistroAdapter;
import com.example.finalproyect.rol.Rol;
import com.example.finalproyect.rol.RolResponse;
import com.example.finalproyect.tarifa.Tarifa_fragment_lista;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class Usuario_editar extends Fragment {

    FragmentUsuarioEditarBinding binding;
    UsuarioAdapter adaptador;

    Usuario usuario = new Usuario();
    ArrayList<Rol> listaRoles = new ArrayList<>();
    Retrofit retrofit = new RetrofitClient().getRetrofit();
    WebService webService = retrofit.create(WebService.class);

    EditText etNombre,etApellido,etEmail,etContrasena,etIdRol;

    Spinner sRoles2;
    Button btnAddUpdate;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public Usuario_editar() {
        // Required empty public constructor
    }



    public static Usuario_editar newInstance(String param1, String param2) {
        Usuario_editar fragment = new Usuario_editar();
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
            usuario.setId(getArguments().getInt("IdUsuario"));
            usuario.setNombre(getArguments().getString("nombre"));
            usuario.setApellido(getArguments().getString("apellido"));
            usuario.setCorreo(getArguments().getString("correo"));
            usuario.setContrasena(getArguments().getString("contrasena"));
            usuario.setId_rol(getArguments().getInt("idRol"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUsuarioEditarBinding.inflate(inflater,container,false);
        etNombre = binding.etNombre;
        etApellido = binding.etApellido;
        etEmail = binding.etEmail;
        etContrasena = binding.etContrasena;
        //etIdRol = binding.etIdRol;
        btnAddUpdate = binding.btnAddUpdate;
        sRoles2=binding.sRoles2;
        etNombre.setText(usuario.getNombre());
        etApellido.setText(usuario.getApellido());
        etEmail.setText(usuario.getCorreo());
        etContrasena.setText(usuario.getContrasena());
        //etIdRol.setText(String.valueOf(usuario.getId_rol()));

        obtenerRoles();
        sRoles2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        btnAddUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarUsuario();
            }
        });

        return binding.getRoot();
    }

    public void actualizarUsuario(){
        this.usuario.setNombre(binding.etNombre.getText().toString());
        this.usuario.setApellido(binding.etApellido.getText().toString());
        this.usuario.setCorreo(binding.etEmail.getText().toString());
        this.usuario.setContrasena(binding.etContrasena.getText().toString());
        //this.usuario.setId_rol(Integer.valueOf(binding.etIdRol.getText().toString()));

        Call<String> call = webService.actualizarUsuario(this.usuario.getId(),this.usuario);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                FancyToast.makeText(requireContext(),response.body(),FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                //Toast.makeText(requireContext(), response.body().toString(), Toast.LENGTH_LONG).show();
                irListaUsuarios();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                FancyToast.makeText(requireContext(),"Error al actualizar usuario !",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                //Toast.makeText(requireContext(), "ERROR ADD", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void irListaUsuarios(){
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame1, new Usuario_fragment_lista());
        fragmentTransaction.commit();
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
                        sRoles2.setAdapter(adapter);
                        sRoles2.setSelection(getArguments().getInt("idRol")-1);
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
}