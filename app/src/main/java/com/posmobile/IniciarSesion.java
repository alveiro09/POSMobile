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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class IniciarSesion extends AppCompatActivity {

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
        urlRequest = url + "?nombreUsuario=" + edtNombreUsuario.getText() + "&contrasena=" + edtContrasena.getText();

        JsonArrayRequest request = new JsonArrayRequest(urlRequest, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject objeto = response.getJSONObject(i);
                        String nombreUsuario = objeto.getString("NombreDeUsuario");
                        String primerNombre = objeto.getString("PrimerNombre");
                        String primerApellido = objeto.getString("PrimerApellido");

                        /*
                        "Id": "5a1d7c59205b9c18b439e2f6",
    "NumeroIdentificacion": "1152686129",
    "PrimerNombre": "santiago",
    "SegundoNombre": "",
    "PrimerApellido": "serna",
    "SegundoApellido": "higuita",
    "Contrasena": "sserna",
    "NombreDeUsuario": "sserna"
                         */

                        MyApplication application = (MyApplication) getApplication();

                        application.setUsuarioActual(new Usuario(nombreUsuario, primerNombre, primerApellido));
                    } catch (JSONException e) {
                        Toast.makeText(IniciarSesion.this, "" + e, Toast.LENGTH_LONG).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(IniciarSesion.this, "" + error.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(request);

    }
}
