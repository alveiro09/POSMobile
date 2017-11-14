package com.posmobile;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.posmobile.modelo.Usuario;

public class CrearUsuarios extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuarios);
        Toolbar toolbar;
        final EditText edtNombreUsuario, edtNumeroIdentificacion, edtPrimerNombre, edtSegundoNombre;
        final EditText edtPrimerApellido, edtSegundoApellido, edtContrasena;
        Button btnCancelar, btnGuardar;
        Usuario usuario;

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
                edtNumeroIdentificacion.setText("");
                edtNombreUsuario.setText("");
                edtPrimerNombre.setText("");
                edtSegundoNombre.setText("");
                edtPrimerApellido.setText("");
                edtSegundoApellido.setText("");
                edtContrasena.setText("");
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {

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

                // Toast.makeText(this, usuario.getPrimerNombre(),Toast.LENGTH_LONG);
                //
            }
        });
    }
}

