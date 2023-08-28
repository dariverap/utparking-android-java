package com.example.finalproyect.tarifa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproyect.R;

import java.util.ArrayList;

public class TarifaAdapter extends RecyclerView.Adapter<TarifaAdapter.TarifaViewHolder> {

    Context context;
    ArrayList<Tarifa> listaTarifas;

    public OnItemClicked onClick = null;

    public TarifaAdapter(Context context, ArrayList<Tarifa> listaTarifas) {
        this.context = context;
        this.listaTarifas = listaTarifas;
    }

    @NonNull
    @Override
    public TarifaAdapter.TarifaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(com.example.finalproyect.R.layout.item_rv_tarifa, parent, false);
        return new TarifaViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull TarifaAdapter.TarifaViewHolder holder, int position) {
        Tarifa tarifa = listaTarifas.get(position);

        holder.tvIdTarifa.setText(tarifa.getId()+"");
        holder.tvDescripcion.setText((tarifa.getDescripcion()));
        holder.tvCosto.setText(String.valueOf(tarifa.getCosto_hora()));

        holder.btnEditar.setOnClickListener(view -> {
            onClick.editarTarifa(tarifa);
        });

        holder.btnBorrar.setOnClickListener(view -> {
            onClick.borrarTarifa(tarifa.getId());
        });
    }

    @Override
    public int getItemCount() {
        return listaTarifas.size();
    }

    public static class TarifaViewHolder extends RecyclerView.ViewHolder {

        TextView tvIdTarifa;
        TextView tvDescripcion;
        TextView tvCosto;
        Button btnEditar;
        Button btnBorrar;
        public TarifaViewHolder(@NonNull View itemView) {
            super(itemView);

            tvIdTarifa = itemView.findViewById(R.id.tvIdTarifa);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
            tvCosto = itemView.findViewById(R.id.tvCosto);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnBorrar = itemView.findViewById(R.id.btnBorrar);
        }
    }

    public interface OnItemClicked {
        void editarTarifa(Tarifa tarifa);
        void borrarTarifa(int idTarifa);
    }

    public void setOnClick(OnItemClicked onClick) {
        this.onClick = onClick;
    }

}
