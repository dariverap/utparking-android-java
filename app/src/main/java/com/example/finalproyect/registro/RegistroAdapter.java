package com.example.finalproyect.registro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproyect.R;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RegistroAdapter extends RecyclerView.Adapter<RegistroAdapter.RegistroViewHolder>{
    OnItemClicked onClick;
    Context context;
    ArrayList<Registro> listaRegistros;
    public RegistroAdapter(Context context, ArrayList<Registro> listaRegistros, OnItemClicked onClick) {
        this.context = context;
        this.listaRegistros = listaRegistros;
        this.onClick = onClick; // Ahora se asigna correctamente
    }
    @NonNull
    @Override
    public RegistroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_registros, parent, false);
        return new RegistroViewHolder(vista);
    }

    public void setListaRegistros(ArrayList<Registro> nuevaLista) {
        this.listaRegistros = nuevaLista;
    }

    @Override
    public void onBindViewHolder(@NonNull RegistroViewHolder holder, int position) {
        Registro registro = listaRegistros.get(position);

        Date date = Date.from(Instant.parse(registro.getFecha_ingreso()));
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy - MM - dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        String fecha = dateFormatter.format(localDateTime);
        String hora = timeFormatter.format(localDateTime);

        holder.tvIdRegistro.setText(registro.getId()+"");
        holder.tvFechaIngreso.setText(fecha);
        holder.tvHoraIngreso.setText(hora);
        holder.tvIdEspacio.setText(registro.getId_espacio()+"");
        holder.tvPatenteVehiculo.setText(registro.getPatente_vehiculo());

        holder.btnBorrar.setOnClickListener(view -> {
            onClick.borrarRegistro(registro.getId());
        });

    }





    @Override
    public int getItemCount() {
        return listaRegistros.size();

    }

    public static class RegistroViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdRegistro;
        TextView tvFechaIngreso;
        TextView tvHoraIngreso;
        //TextView tvFechaSalida;
        //TextView tvHoraSalida;
        TextView tvIdEspacio;
        TextView tvPatenteVehiculo;

        Button btnBorrar;
        public RegistroViewHolder(@NonNull View itemView) {

            super(itemView);
            tvHoraIngreso = itemView.findViewById(R.id.tvHoraIngreso);
            tvIdRegistro = itemView.findViewById(R.id.tvIdRegistro);
            tvFechaIngreso = itemView.findViewById(R.id.tvFechaIngreso);
            //tvFechaSalida = itemView.findViewById(R.id.tvFechaSalida);
            tvIdEspacio = itemView.findViewById(R.id.tvIdEspacio);
            tvPatenteVehiculo = itemView.findViewById(R.id.tvPatenteVehiculo);
            //tvHoraSalida = itemView.findViewById(R.id.tvHoraIngreso);

            btnBorrar = itemView.findViewById(R.id.btnBorrar);
        }
    }

    public interface OnItemClicked {
        void borrarRegistro(int idRegistro);
    }

    public void setOnClick(OnItemClicked onClick) {
        this.onClick = onClick;
    }
}
