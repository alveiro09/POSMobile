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
import com.posmobile.adapters.UsuariosAdapter;
import com.posmobile.modelo.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Usuarios extends AppCompatActivity {

    private static final String url = "http://poswebapi.azurewebsites.net/api/Usuario";
    private RecyclerView recyclerView;
    private UsuariosAdapter adapter;
    private ArrayList<Usuario> usuarioServicio = new ArrayList<>();
    ProgressDialog progressDialog;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CrearUsuarios.class);
                startActivity(intent);
            }
        });


        requestQueue = Volley.newRequestQueue(getApplicationContext());
        mostrarInfo();

    }

    private void mostrarInfo() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando datos...");
        recyclerView = (RecyclerView) findViewById(R.id.listaUsuarios);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new UsuariosAdapter(this, usuarioServicio);
        recyclerView.setAdapter(adapter);

        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject objeto = response.getJSONObject(i);
                        String nombreUsuario = objeto.getString("NombreDeUsuario");
                        String primerNombre = objeto.getString("PrimerNombre");
                        String primerApellido = objeto.getString("PrimerApellido");

                        usuarioServicio.add(new Usuario(nombreUsuario, primerNombre, primerApellido));
                    } catch (JSONException e) {
                        Toast.makeText(Usuarios.this, "" + e, Toast.LENGTH_LONG).show();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(request);

    }

}