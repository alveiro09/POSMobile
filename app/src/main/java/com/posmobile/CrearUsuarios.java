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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.posmobile.modelo.Usuario;

import java.util.HashMap;
import java.util.Map;

public class CrearUsuarios extends AppCompatActivity {
    //TODO cambiar por UTL del WebApi
    private static final String url = "http://192.168.1.14:8080/tortas/consulta.php";
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
                usuario.setNumeroIdentificacion(edtNombreUsuario.getText().toString());
                usuario.setPrimerApellido(edtNombreUsuario.getText().toString());
                usuario.setSegundoNombre(edtNombreUsuario.getText().toString());
                usuario.setPrimerApellido(edtNombreUsuario.getText().toString());
                usuario.setSegundoApellido(edtNombreUsuario.getText().toString());
                usuario.setContrasena(edtNombreUsuario.getText().toString());

                InsertarActualizarUsuario(usuario, !actualizacion);

                Toast.makeText(CrearUsuarios.this, "Usuario Creado", Toast.LENGTH_LONG).show();

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
        }
        catch (Exception e)
        {

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
        StringRequest postRequest = new StringRequest(insertar ? Request.Method.POST : Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //response;
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error

                        Log.d("Error.Response", error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("NombreUsuario", usuario.getNombreUsuario());
                params.put("NumeroIdentificacion", usuario.getNumeroIdentificacion());
                params.put("PrimerNombre", usuario.getPrimerNombre());
                params.put("SegundoNombre", usuario.getSegundoNombre());
                params.put("PrimerApellido", usuario.getPrimerApellido());
                params.put("SegundoApellido", usuario.getSegundoApellido());
                params.put("Contrasena", usuario.getContrasena());

                return params;
            }
        };
        queue.add(postRequest);
    }
}

