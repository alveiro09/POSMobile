package com.posmobile.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.posmobile.CrearReferencias;
import com.posmobile.CrearUsuarios;
import com.posmobile.IData;
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
    private IData data;
    private boolean seleccion;


    public ReferenciasAdapter(Context context, ArrayList<Referencia> referencias, IData data, Boolean seleccion) {
        this.context = context;
        this.referencias = referencias;
        this.data = data;
         this.seleccion = seleccion;
    }

    @Override
    public MyvistaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyvistaHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.listareferencias, null));
    }

    @Override
    public void onBindViewHolder(final MyvistaHolder holder, int position) {
        final int pos = position;
        holder.tNombre.setText(referencias.get(position).getNombre());
        holder.tDescripcion.setText(referencias.get(position).getDescripcion());
        holder.tPrecioCompra.setText(String.valueOf(referencias.get(position).getPrecioCompra()));
        holder.tPrecioVenta.setText(String.valueOf(referencias.get(position).getPrecioVenta()));
        holder.tCantidadDisponible.setText(String.valueOf(referencias.get(position).getCantidadDisponible()));

        holder.fltEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CrearReferencias.class);
                intent.putExtra("referenciaEdit", referencias.get(pos));
                context.startActivity(intent);
            }
        });
        holder.fltSeleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdiccionarReferencia(referencias.get(pos), holder.EdtCantidadAPedir.getText().toString());
            }
        });
    }

    private void AdiccionarReferencia(Referencia referencia, String cantidadAPedir) {
        Referencia referencia1 = referencia;
        referencia1.setCantidadAPedir(Integer.parseInt(cantidadAPedir));
        data.onItemSelected(referencia1);
    }

    @Override
    public int getItemCount() {
        return referencias.size();
    }

    public class MyvistaHolder extends RecyclerView.ViewHolder {
        TextView tNombre, tDescripcion, tPrecioCompra, tPrecioCompraLabel, tPrecioVenta, tPrecioVentaLabel, tCantidadDisponible;
        EditText EdtCantidadAPedir;
        FloatingActionButton fltEdit, fltSeleccionar;

        public MyvistaHolder(View itemView) {
            super(itemView);

            tNombre = (TextView) itemView.findViewById(R.id.LblNombreData);
            tDescripcion = (TextView) itemView.findViewById(R.id.LblDescripcionData);
            tPrecioCompra = (TextView) itemView.findViewById(R.id.LblPrecioCompraData);
            tPrecioCompraLabel = (TextView) itemView.findViewById(R.id.LblPrecioCompra);

            tPrecioVenta = (TextView) itemView.findViewById(R.id.LblPrecioVentaData);
            tPrecioVentaLabel = (TextView) itemView.findViewById(R.id.LblPrecioVenta);
            tCantidadDisponible = (TextView) itemView.findViewById(R.id.LblCantidadDisponibleData);
            EdtCantidadAPedir = (EditText) itemView.findViewById(R.id.EdtCantidadAPedir);
            fltEdit = (FloatingActionButton) itemView.findViewById(R.id.fltAditar);
            fltSeleccionar = (FloatingActionButton) itemView.findViewById(R.id.fltSeleccionar);

            if (seleccion)
            {
                tPrecioCompra.setVisibility(View.GONE);
                tPrecioCompraLabel.setVisibility(View.GONE);
                fltEdit.setVisibility(View.GONE);
            }
            else
            {
                fltSeleccionar.setVisibility(View.GONE);
                EdtCantidadAPedir.setVisibility(View.GONE);
            }
        }
    }
}
