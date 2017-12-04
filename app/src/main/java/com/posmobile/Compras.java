package com.posmobile;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.posmobile.adapters.ReferenciasAdapter;
import com.posmobile.adapters.TransaccionesAdapter;
import com.posmobile.adapters.UsuariosAdapter;
import com.posmobile.modelo.Referencia;
import com.posmobile.modelo.Transaccion;
import com.posmobile.modelo.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Compras extends Fragment {
    private final ArrayList<Referencia> referenciasAComprar = new ArrayList<>();
    Referencia referencia;

    ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private ReferenciasAdapter adapter;

    private Button btnGuardar, btnMostrar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_compras, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        try {
//            referencia = (Referencia) getArguments().getSerializable("referenciaAdd");
//
//            referenciasAComprar.add(referencia);
//            //     mostrarInfo();
//        } catch (Exception ex) {
//
//        }
////        referenciasAComprar.add(new Referencia("12", "Prueba", "Prueba111", 12000,
////                15000, 2,1));
//        mostrarInfo();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnGuardar = (Button) getActivity().findViewById(R.id.bntGuardar);
        btnMostrar = (Button) getActivity().findViewById(R.id.bntMostrar);
        btnMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarInfo();
            }
        });
        try {
            referencia = (Referencia) getArguments().getSerializable("referenciaAdd");

            referenciasAComprar.add(referencia);
            //     mostrarInfo();
        } catch (Exception ex) {

        }
//        referenciasAComprar.add(new Referencia("12", "Prueba", "Prueba111", 12000,
//                15000, 2,1));
//        mostrarInfo();
    }

    private void mostrarInfo() {
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.listaReferenciasSeleccionadas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        if ((referenciasAComprar != null) && (referenciasAComprar.size() > 0)) {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Actualizando datos...");
            progressDialog.show();

            adapter = new ReferenciasAdapter(getContext(), referenciasAComprar, new IData() {
                @Override
                public void onItemSelected(Referencia referenciaFrag) {

                }
            }, false);
            recyclerView.setAdapter(adapter);
            progressDialog.dismiss();

        }
    }
}
