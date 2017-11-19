package com.posmobile;

import android.app.ProgressDialog;
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
import com.posmobile.adapters.TransaccionesAdapter;
import com.posmobile.modelo.Transaccion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReporteCompras extends AppCompatActivity {
    private static final String url = "http://192.168.1.14:8080/tortas/consulta.php";
    private RecyclerView recyclerView;
    private TransaccionesAdapter adapter;
    private ArrayList<Transaccion> transaccionServicio = new ArrayList<>();
    ProgressDialog progressDialog;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_compras);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        mostrarInfo();
    }

    private void mostrarInfo() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando datos...");
        recyclerView = (RecyclerView) findViewById(R.id.listaTransacciones);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new TransaccionesAdapter(this, transaccionServicio);
        recyclerView.setAdapter(adapter);

        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject objeto = response.getJSONObject(i);
                        int tipoTransaccion = objeto.getInt("TipoTransaccion");
                        if ((EnumTipoTransaccion.values()[tipoTransaccion]) == EnumTipoTransaccion.Compra) {
                            String id = objeto.getString("Id");
                            String fecha = objeto.getString("Fecha");
                            double neto = objeto.getDouble("Neto");
                            double bruto = objeto.getDouble("Bruto");
                            double descuento = objeto.getDouble("Descuento");
                            String usuario = objeto.getString("Usuario");
                            transaccionServicio.add(new Transaccion(id, usuario, fecha, tipoTransaccion, neto, bruto, descuento));
                        }
                    } catch (JSONException e) {
                        Toast.makeText(ReporteCompras.this, "" + e, Toast.LENGTH_LONG).show();
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
