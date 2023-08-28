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

public class RegistroAdapter extends RecyclerView.Adapter<RegistroAdapter.RegistroViewHolder>{

    Context context;
    ArrayList<Registro> listaRegistros;

    @NonNull
    @Override
    public RegistroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_registros, parent, false);
        return new RegistroViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull RegistroViewHolder holder, int position) {
        Registro registro = listaRegistros.get(position);

        Date date = Date.from(Instant.parse(registro.getFecha_ingreso()));
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        String fecha = dateFormatter.format(localDateTime);
        String hora = timeFormatter.format(localDateTime);

        holder.tvIdRegistro.setText(registro.getId()+"");



        holder.tvFechaIngreso.setText(fecha+"-"+hora);

        Date date2 = Date.from(Instant.parse(registro.getFecha_salida()));
        DateTimeFormatter dateFormatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter2 = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime localDateTime2 = LocalDateTime.ofInstant(date2.toInstant(), ZoneId.systemDefault());
        String fecha2 = dateFormatter2.format(localDateTime2);
        String hora2 = timeFormatter2.format(localDateTime2);

        holder.tvFechaSalida.setText(fecha2+" - "+hora2);

        holder.tvIdEspacio.setText(registro.getId_espacio()+"");
        holder.tvPatenteVehiculo.setText(registro.getPatente_vehiculo());
        //holder.tvIdUsuario.setText(String.valueOf(registro.getId_usuario()));
        //holder.tvIdTarifa.setText(String.valueOf(registro.getId_tarifa()));
        holder.tvCostoTotal.setText(String.valueOf(registro.getCosto_total()));

        holder.btnEditar.setOnClickListener(view -> {
            onClick.editarRegistro(registro);
        });

        holder.btnBorrar.setOnClickListener(view -> {
            onClick.borrarRegistro(registro.getId());
        });

    }


    public RegistroAdapter(Context context, ArrayList<Registro> listaRegistros) {
        this.context = context;
        this.listaRegistros = listaRegistros;
    }

    public OnItemClicked onClick = null;

    @Override
    public int getItemCount() {
        return listaRegistros.size();

    }

    public static class RegistroViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdRegistro;
        TextView tvFechaIngreso;
        TextView tvFechaSalida;
        TextView tvIdEspacio;
        TextView tvPatenteVehiculo;
        TextView tvIdUsuario;
        TextView tvIdTarifa;
        TextView tvCostoTotal;
        Button btnEditar;
        Button btnBorrar;
        public RegistroViewHolder(@NonNull View itemView) {

            super(itemView);

            tvIdRegistro = itemView.findViewById(R.id.tvIdRegistro);
            tvFechaIngreso = itemView.findViewById(R.id.tvFechaIngreso);
            tvFechaSalida = itemView.findViewById(R.id.tvFechaSalida);
            tvIdEspacio = itemView.findViewById(R.id.tvIdEspacio);
            tvPatenteVehiculo = itemView.findViewById(R.id.tvPatenteVehiculo);
            //tvIdUsuario = itemView.findViewById(R.id.tvIdUsuario);
            //tvIdTarifa = itemView.findViewById(R.id.tvIdTarifa);
            tvCostoTotal = itemView.findViewById(R.id.tvCostoTotal);

            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnBorrar = itemView.findViewById(R.id.btnBorrar);
        }
    }

    public interface OnItemClicked {
        void editarRegistro(Registro registro);
        void borrarRegistro(int idRegistro);
    }

    public void setOnClick(OnItemClicked onClick) {
        this.onClick = onClick;
    }
}
