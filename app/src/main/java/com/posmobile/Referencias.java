package com.posmobile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.posmobile.adapters.ReferenciasAdapter;
import com.posmobile.modelo.Referencia;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Referencias extends AppCompatActivity {

    private static final String url = "http://poswebapi.azurewebsites.net/api/Producto";
    private RecyclerView recyclerView;
    private ReferenciasAdapter adapter;
    private ArrayList<Referencia> Referenciaservicio = new ArrayList<>();
    ProgressDialog progressDialog;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referencias);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CrearReferencias.class);
                startActivity(intent);
            }
        });


        requestQueue = Volley.newRequestQueue(getApplicationContext());
        mostrarInfo();

    }

    private void mostrarInfo() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando datos...");
        progressDialog.show();
        recyclerView = (RecyclerView) findViewById(R.id.listaReferencias);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new ReferenciasAdapter(this, Referenciaservicio, new IData() {
            @Override
            public void onItemSelected(Referencia referenciaFrag) {

            }
        }, false, false);
        recyclerView.setAdapter(adapter);

        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject objeto = response.getJSONObject(i);
                        String id = objeto.getString("IdProducto");
                        String nombre = objeto.getString("Nombre");
                        String descripcion = objeto.getString("Descripcion");
                        double precioCompra = objeto.getDouble("PrecioCompra");
                        double precioVenta = objeto.getDouble("PrecioVenta");
                        double cantidadDisponible = objeto.getDouble("CantidadDisponible");

                        Referenciaservicio.add(new Referencia(id, nombre, descripcion, precioCompra, precioVenta, cantidadDisponible));
                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        Toast.makeText(Referencias.this, "" + e, Toast.LENGTH_LONG).show();
                    }
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        });

        requestQueue.add(request);

    }

}
