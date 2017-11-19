package com.posmobile.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.posmobile.CrearUsuarios;
import com.posmobile.R;
import com.posmobile.modelo.Referencia;
import com.posmobile.modelo.Referencia;

import java.util.ArrayList;

/**
 * Created by personal on 19/11/2017.
 */

public class ReferenciasAdapter extends RecyclerView.Adapter<ReferenciasAdapter.MyvistaHolder> {

    private Context context;
    private ArrayList<Referencia> referencias;

    public ReferenciasAdapter(Context context, ArrayList<Referencia> referencias) {
        this.context = context;
        this.referencias = referencias;
    }

    @Override
    public MyvistaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyvistaHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.listareferencias, null));
    }

    @Override
    public void onBindViewHolder(MyvistaHolder holder, int position) {
        final int pos = position;
        holder.tNombre.setText(referencias.get(position).getNombre());
        holder.tDescripcion.setText(referencias.get(position).getDescripcion());
        holder.tPrecioCompra.setText(String.valueOf(referencias.get(position).getPrecioCompra()));
        holder.tPrecioVenta.setText(String.valueOf(referencias.get(position).getPrecioVenta()));
        holder.tCantidadDisponible.setText(String.valueOf(referencias.get(position).getCantidadDisponible()));
        holder.fltEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CrearUsuarios.class);
                intent.putExtra("referenciaEdit", referencias.get(pos));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return referencias.size();
    }

    public class MyvistaHolder extends RecyclerView.ViewHolder {
        TextView tNombre, tDescripcion, tPrecioCompra, tPrecioVenta, tCantidadDisponible;
        FloatingActionButton fltEdit;
        public MyvistaHolder(View itemView) {
            super(itemView);

            tNombre = (TextView) itemView.findViewById(R.id.LblNombreData);
            tDescripcion = (TextView) itemView.findViewById(R.id.LblDescripcionData);
            tPrecioCompra = (TextView) itemView.findViewById(R.id.LblPrecioCompraData);
            tPrecioVenta = (TextView) itemView.findViewById(R.id.LblPrecioVentaData);
            tCantidadDisponible = (TextView) itemView.findViewById(R.id.LblCantidadDisponibleData);
            fltEdit = (FloatingActionButton) itemView.findViewById(R.id.fltAditar);
        }
    }
}
