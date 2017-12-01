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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.posmobile.adapters.UsuariosAdapter;
import com.posmobile.modelo.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class IniciarSesion extends AppCompatActivity {
    ProgressDialog progressDialog;
    EditText edtNombreUsuario, edtContrasena;
    Button BtnIniciarSesion;
    private static final String url = "http://poswebapi.azurewebsites.net/api/Usuario";
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edtNombreUsuario = (EditText) findViewById(R.id.edtNombreUsuario);
        edtContrasena = (EditText) findViewById(R.id.edtContrasena);
        BtnIniciarSesion = (Button) findViewById(R.id.bntGuardar);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        BtnIniciarSesion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mostrarInfo();
            }
        });
    }

    private void mostrarInfo() {
        String urlRequest;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando información...");
        progressDialog.show();
        urlRequest = url + "?nombreUsuario=" + edtNombreUsuario.getText() + "&contrasena=" + edtContrasena.getText();

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET ,urlRequest,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //  for (int i = 0; i < response.length(); i++) {
                try {
                    String Id = response.getString("Id");
                    String NumeroIdentificacion = response.getString("NumeroIdentificacion");
                    String PrimerNombre = response.getString("PrimerNombre");
                    String SegundoNombre = response.getString("SegundoNombre");
                    String PrimerApellido = response.getString("PrimerApellido");
                    String SegundoApellido = response.getString("SegundoApellido");
                    String Contrasena = response.getString("Contrasena");
                    String NombreDeUsuario = response.getString("NombreDeUsuario");

                    MyApplication application = (MyApplication) getApplication();

                    application.setUsuarioActual(new Usuario(Id, NumeroIdentificacion, PrimerNombre, SegundoNombre,
                            PrimerApellido, SegundoApellido, NombreDeUsuario, Contrasena));

                    progressDialog.dismiss();
                    Toast.makeText(IniciarSesion.this, "Inicio de sesión correcto" , Toast.LENGTH_LONG).show();

                    IniciarActividad(MainActivity.class);
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(IniciarSesion.this, "Error al iniciar sesión, Revise lo datos ingresados", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(IniciarSesion.this, "Error al iniciar sesión, Revise lo datos ingresados", Toast.LENGTH_LONG).show();

            }
        });
        request.setRetryPolicy(new RetryPolicy() {
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
        requestQueue.add(request);

    }
    private void IniciarActividad(Class<?> actividad) {
        Intent intent = new Intent(this, actividad);
        startActivity(intent);
    }
}
