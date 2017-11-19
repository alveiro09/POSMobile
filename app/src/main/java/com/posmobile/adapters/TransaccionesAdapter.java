package com.posmobile.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.posmobile.R;
import com.posmobile.modelo.Transaccion;

import java.util.ArrayList;

/**
 * Created by personal on 19/11/2017.
 */

public class TransaccionesAdapter extends RecyclerView.Adapter<TransaccionesAdapter.MyvistaHolder> {

    private Context context;
    private ArrayList<Transaccion> transacciones;

    public TransaccionesAdapter(Context context, ArrayList<Transaccion> transacciones) {
        this.context = context;
        this.transacciones = transacciones;
    }

    @Override
    public MyvistaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyvistaHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.listatransacciones, null));
    }

    @Override
    public void onBindViewHolder(MyvistaHolder holder, int position) {
        holder.tId.setText(transacciones.get(position).getId());
        holder.tFecha.setText(transacciones.get(position).getFecha());
        holder.tBruto.setText(String.valueOf(transacciones.get(position).getBruto()));
        holder.tNeto.setText(String.valueOf(transacciones.get(position).getNeto()));
        holder.tDescuento.setText(String.valueOf(transacciones.get(position).getDescuento()));
        holder.tUsuario.setText(transacciones.get(position).getusuarioId());
    }

    @Override
    public int getItemCount() {
        return transacciones.size();
    }

    public class MyvistaHolder extends RecyclerView.ViewHolder {
        TextView tId, tBruto, tNeto, tDescuento, tUsuario, tFecha;

        public MyvistaHolder(View itemView) {
            super(itemView);
            tFecha = (TextView) itemView.findViewById(R.id.LblFechaTransaccionData);
            tId = (TextView) itemView.findViewById(R.id.LblidTransaccionData);
            tBruto = (TextView) itemView.findViewById(R.id.LblBrutoData);
            tNeto = (TextView) itemView.findViewById(R.id.LblNetoData);
            tDescuento = (TextView) itemView.findViewById(R.id.LblDescuentoData);
            tUsuario = (TextView) itemView.findViewById(R.id.LblNombreUsuarioData);
        }
    }
}
