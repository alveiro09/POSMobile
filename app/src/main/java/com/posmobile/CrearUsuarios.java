package com.posmobile;

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
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.posmobile.modelo.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CrearUsuarios extends AppCompatActivity {
    //TODO cambiar por UTL del WebApi
    private static final String url = "http://posmobileapi.azurewebsites.net/api/Usuario";
    RequestQueue queue;
    Usuario usuarioEdit;
    EditText edtNombreUsuario, edtNumeroIdentificacion, edtPrimerNombre, edtSegundoNombre;
    EditText edtPrimerApellido, edtSegundoApellido, edtContrasena;
    Button btnCancelar, btnGuardar;
    Usuario usuario;
    Toolbar toolbar;
    Boolean actualizacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuarios);

        queue = Volley.newRequestQueue(this);

        actualizacion = false;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        edtNombreUsuario = (EditText) findViewById(R.id.edtNombreUsuario);
        edtNumeroIdentificacion = (EditText) findViewById(R.id.edtNumeroIdentificacion);
        edtPrimerNombre = (EditText) findViewById(R.id.edtPrimerNombre);
        edtSegundoNombre = (EditText) findViewById(R.id.edtSegundoNombre);
        edtPrimerApellido = (EditText) findViewById(R.id.edtPrimerApellido);
        edtSegundoApellido = (EditText) findViewById(R.id.edtSegundoApellido);
        edtContrasena = (EditText) findViewById(R.id.edtContrasena);

        btnGuardar = (Button) findViewById(R.id.bntGuardar);
        btnCancelar = (Button) findViewById(R.id.bntCancelar);

        setSupportActionBar(toolbar);

        btnGuardar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Usuario usuario = new Usuario();
                usuario.setNombreUsuario(edtNombreUsuario.getText().toString());
                usuario.setNumeroIdentificacion(edtNumeroIdentificacion.getText().toString());
                usuario.setPrimerApellido(edtPrimerApellido.getText().toString());
                usuario.setSegundoApellido(edtSegundoApellido.getText().toString());
                usuario.setPrimerNombre(edtPrimerNombre.getText().toString());
                usuario.setSegundoNombre(edtSegundoNombre.getText().toString());
                usuario.setContrasena(edtNombreUsuario.getText().toString());


                InsertarActualizarUsuario(usuario, !actualizacion);

                //     Toast.makeText(CrearUsuarios.this, "Usuario Creado", Toast.LENGTH_LONG).show();

            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                edtNumeroIdentificacion.setText("");
                edtNombreUsuario.setText("");
                edtPrimerNombre.setText("");
                edtSegundoNombre.setText("");
                edtPrimerApellido.setText("");
                edtSegundoApellido.setText("");
                edtContrasena.setText("");
            }
        });

        try {
            usuarioEdit = (Usuario) getIntent().getExtras().getSerializable("usuarioEdit");
        } catch (Exception e) {

        }

        if (usuarioEdit != null) {
            LlenarUsuarioEdit(usuarioEdit);
            actualizacion = true;
        }
    }

    private void LlenarUsuarioEdit(Usuario usuarioEdit) {
        edtNumeroIdentificacion.setText(usuarioEdit.getNumeroIdentificacion());
        edtNombreUsuario.setText(usuarioEdit.getNombreUsuario());
        edtPrimerNombre.setText(usuarioEdit.getPrimerNombre());
        edtSegundoNombre.setText(usuarioEdit.getSegundoNombre());
        edtPrimerApellido.setText(usuarioEdit.getPrimerApellido());
        edtSegundoApellido.setText(usuarioEdit.getSegundoApellido());
        edtContrasena.setText(usuarioEdit.getContrasena());
    }

    private void InsertarActualizarUsuario(final Usuario usuario, boolean insertar) {


        Map<String, String> params = new HashMap<String, String>();

        params.put("nombreDeUsuario", usuario.getNombreUsuario());
        params.put("numeroIdentificacion", usuario.getNumeroIdentificacion());
        params.put("primerNombre", usuario.getPrimerNombre());
        params.put("segundoNombre", usuario.getSegundoNombre());
        params.put("primerApellido", usuario.getPrimerApellido());
        params.put("segundoApellido", usuario.getSegundoApellido());
        params.put("contrasena", usuario.getContrasena());


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, new JSONObject(params),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(CrearUsuarios.this, "Usuario Creado", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CrearUsuarios.this, error.getMessage(), Toast.LENGTH_LONG).show();
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


        // Adding request to request queue
        queue.add(jsonObjReq);

        // Cancelling request
    /* if (queue!= null) {
    queue.cancelAll(TAG);
    } */

    }


//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(insertar ? Request.Method.POST : Request.Method.PUT,
//                url, null,
//                new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Toast.makeText(CrearUsuarios.this, "Usuario Creado", Toast.LENGTH_LONG).show();
//                    }
//                }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(CrearUsuarios.this, error.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        }) {
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> paramsH = new HashMap<>();
//                paramsH.put("Content-Type", "application/json");
//                //..add other headers
//                return paramsH;
//            }
//
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("nombreDeUsuario", usuario.getNombreUsuario());
//                params.put("numeroIdentificacion", usuario.getNumeroIdentificacion());
//                params.put("primerNombre", usuario.getPrimerNombre());
//                params.put("segundoNombre", usuario.getSegundoNombre());
//                params.put("primerApellido", usuario.getPrimerApellido());
//                params.put("segundoApellido", usuario.getSegundoApellido());
//                params.put("contrasena", usuario.getContrasena());
//
//                return params;
//            }
//
//        };
//        queue.add(jsonObjReq);
//}
}