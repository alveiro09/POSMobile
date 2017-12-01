package com.posmobile;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.posmobile.modelo.Referencia;
import com.posmobile.modelo.Usuario;

import org.json.JSONObject;

import java.sql.Ref;
import java.util.HashMap;
import java.util.Map;

//import com.posmobile.modelo.Referencia;

public class CrearReferencias extends AppCompatActivity {
    //TODO cambiar por UTL del WebApi
    private static final String url = "http://poswebapi.azurewebsites.net/api/Producto";
    RequestQueue queue;
    EditText edtCodigoReferencia, edtNombreReferencia, edtDescripcionReferencia;
    EditText edtPrecioCompra, edtPrecioVenta;
    Button btnCancelar, btnGuardar;
    Boolean actualizacion;
    Toolbar toolbar;
    Referencia referenciaEdit;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_referencias);


        queue = Volley.newRequestQueue(this);
        actualizacion = false;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        edtCodigoReferencia = (EditText) findViewById(R.id.edtCodigoReferencia);
        edtNombreReferencia = (EditText) findViewById(R.id.edtNombreReferencia);
        edtDescripcionReferencia = (EditText) findViewById(R.id.edtDescripcionReferencia);
        edtPrecioCompra = (EditText) findViewById(R.id.edtPrecioCompra);
        edtPrecioVenta = (EditText) findViewById(R.id.edtPrecioVenta);

        btnGuardar = (Button) findViewById(R.id.bntGuardar);
        btnCancelar = (Button) findViewById(R.id.bntCancelar);

        setSupportActionBar(toolbar);

        btnGuardar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Referencia referencia = new Referencia();
                referencia.setId(edtCodigoReferencia.getText().toString());
                referencia.setNombre(edtNombreReferencia.getText().toString());
                referencia.setDescripcion(edtDescripcionReferencia.getText().toString());
                referencia.setPrecioCompra(Double.parseDouble(edtPrecioCompra.getText().toString()));
                referencia.setPrecioVenta(Double.parseDouble(edtPrecioVenta.getText().toString()));


                InsertarActualizarReferencia(referencia, !actualizacion);
                //  Toast.makeText(CrearReferencias.this, "Referencia Creada", Toast.LENGTH_LONG).show();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                edtCodigoReferencia.setText("");
                edtNombreReferencia.setText("");
                edtDescripcionReferencia.setText("");
                edtPrecioCompra.setText("");
                edtPrecioVenta.setText("");
            }
        });
        try {
            referenciaEdit = (Referencia) getIntent().getExtras().getSerializable("referenciaEdit");
        } catch (Exception e) {

        }

        if (referenciaEdit != null) {
            LlenarReferenciaEdit(referenciaEdit);
            actualizacion = true;
        }

    }

    private void LlenarReferenciaEdit(Referencia referenciaEdit) {
        edtCodigoReferencia.setText(referenciaEdit.getId());
        edtNombreReferencia.setText(referenciaEdit.getNombre());
        edtDescripcionReferencia.setText(referenciaEdit.getDescripcion());
        edtPrecioCompra.setText(Double.toString(referenciaEdit.getPrecioCompra()));
        edtPrecioVenta.setText(Double.toString(referenciaEdit.getPrecioVenta()));
    }

    private void InsertarActualizarReferencia(final Referencia referencia, boolean insertar) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Guardando información...");
        progressDialog.show();
        Map<String, String> params = new HashMap<String, String>();

        params.put("idProducto", referencia.getId());
        params.put("nombre", referencia.getNombre());
        params.put("descripcion", referencia.getDescripcion());
        params.put("cantidadDisponible", Double.toString(referencia.getCantidadDisponible()));
        params.put("precioVenta", Double.toString(referencia.getPrecioVenta()));
        params.put("precioCompra", Double.toString(referencia.getPrecioCompra()));
        params.put("cantidadDisponible", "0");


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(insertar ? Request.Method.POST : Request.Method.PUT,
                url, new JSONObject(params),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        Toast.makeText(CrearReferencias.this, "Referencia Creada", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                if (error.getCause().toString().equalsIgnoreCase("org.json.JSONException: Value false of type java.lang.String cannot be converted to JSONObject"))
                    Toast.makeText(CrearReferencias.this, "Referencia Creada", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(CrearReferencias.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }


        };
        jsonObjReq.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        queue.add(jsonObjReq);

    }
}