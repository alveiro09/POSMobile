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
import com.posmobile.modelo.Usuario;

import java.util.ArrayList;

/**
 * Created by personal on 19/11/2017.
 */

public class UsuariosAdapter extends RecyclerView.Adapter<UsuariosAdapter.MyvistaHolder> {

    private Context context;
    private ArrayList<Usuario> usuarios;

    public UsuariosAdapter(Context context, ArrayList<Usuario> usuarios) {
        this.context = context;
        this.usuarios = usuarios;
    }

    @Override
    public MyvistaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyvistaHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.listausuario, null));
    }

    @Override
    public void onBindViewHolder(MyvistaHolder holder, int position) {
        final int pos = position;
        holder.tnombreUsuario.setText(usuarios.get(position).getNombreUsuario());
        holder.tPrimerNombre.setText(usuarios.get(position).getPrimerNombre());
        holder.tPrimerApellido.setText(usuarios.get(position).getPrimerApellido());
        holder.fltEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CrearUsuarios.class);
                intent.putExtra("usuarioEdit", usuarios.get(pos));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public class MyvistaHolder extends RecyclerView.ViewHolder {
        TextView tnombreUsuario, tPrimerNombre, tPrimerApellido;
        FloatingActionButton fltEdit;

        public MyvistaHolder(View itemView) {
            super(itemView);

            tnombreUsuario = (TextView) itemView.findViewById(R.id.LblNombreUsuarioData);
            tPrimerNombre = (TextView) itemView.findViewById(R.id.LblPrimerNombreData);
            tPrimerApellido = (TextView) itemView.findViewById(R.id.LblPrimerNombreData);
            fltEdit = (FloatingActionButton) itemView.findViewById(R.id.fltAditar);
        }
    }
}
