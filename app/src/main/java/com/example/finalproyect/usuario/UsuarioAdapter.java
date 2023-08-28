package com.example.finalproyect.usuario;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproyect.R;
import com.example.finalproyect.RetrofitClient;
import com.example.finalproyect.WebService;
import com.example.finalproyect.rol.Rol;
import com.example.finalproyect.rol.RolResponse;
import com.example.finalproyect.tarifa.Tarifa;
import com.example.finalproyect.tarifa.TarifaAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder> {

    Context context;
    ArrayList<Usuario> listaUsuarios;
    Retrofit retrofit = new RetrofitClient().getRetrofit();
    ArrayList<Rol> listaRoles = new ArrayList<>();
    WebService webService = retrofit.create(WebService.class);
    public UsuarioAdapter.OnItemClicked onClick = null;

    public UsuarioAdapter(Context context, ArrayList<Usuario> listaUsuarios) {
        this.context = context;
        this.listaUsuarios = listaUsuarios;
    }

    @NonNull
    @Override
    public UsuarioAdapter.UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_usuario, parent, false);
        return new UsuarioViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioAdapter.UsuarioViewHolder holder, int position) {

        Usuario usuario = listaUsuarios.get(position);

        holder.tvIdUsuario.setText(usuario.getId()+"");
        holder.tvNombre.setText(usuario.getNombre());
        holder.tvApellido.setText(usuario.getApellido());
        holder.tvEmail.setText(usuario.getCorreo());
        holder.tvContrasena.setText(usuario.getContrasena());
        if(usuario.getId_rol()==1) {
            holder.tvIdRol.setText("Administrador");
        }else {
            holder.tvIdRol.setText("Empleado");
        }
        holder.btnEditar.setOnClickListener(view -> {
            onClick.editarUsuario(usuario);
        });

        holder.btnBorrar.setOnClickListener(view -> {
            onClick.borrarUsuario(usuario.getId());
        });
    }


    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }

    public static class UsuarioViewHolder extends RecyclerView.ViewHolder {

        TextView tvIdUsuario;
        TextView tvNombre;
        TextView tvApellido;
        TextView tvEmail;
        TextView tvContrasena;
        TextView tvIdRol;
        TextView btnEditar;
        TextView btnBorrar;

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);

            tvIdUsuario = itemView.findViewById(R.id.tvIdUsuario);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvApellido = itemView.findViewById(R.id.tvApellido);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvContrasena = itemView.findViewById(R.id.tvContrasena);
            tvIdRol = itemView.findViewById(R.id.tvIdRol);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnBorrar = itemView.findViewById(R.id.btnBorrar);
        }
    }

    public interface OnItemClicked {
        void editarUsuario(Usuario usuario);
        void borrarUsuario(int idUsuario);
    }

    public void setOnClick(OnItemClicked onClick) {
        this.onClick = onClick;
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

                    }
                } else {
                    //Toast.makeText(requireContext(), "ERROR AL CONSULTAR TODOS LOS ROLES", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RolResponse> call, Throwable t) {
                //Toast.makeText(requireContext(), "ERROR AL CONSULTAR TODOS LOS ROLES", Toast.LENGTH_LONG).show();
            }
        });
    }




}
