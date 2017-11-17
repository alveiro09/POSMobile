package com.posmobile;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.posmobile.modelo.Referencia;

public class CrearReferencias extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_referencias);
        Toolbar toolbar;
        final EditText edtCodigoReferencia, edtNombreReferencia, edtDescripcionReferencia;
        final EditText edtPrecioCompra, edtPrecioVenta;
        Button btnCancelar, btnGuardar;
        Referencia referencia;

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
                referencia.setCodigoReferencia(edtCodigoReferencia.getText().toString());
                referencia.setNombreReferencia(edtNombreReferencia.getText().toString());
                referencia.setDescripcionReferencia(edtDescripcionReferencia.getText().toString());
                referencia.setPrecioCompra(Double.parseDouble(edtPrecioCompra.getText().toString()));
                referencia.setPrecioVenta(Double.parseDouble(edtPrecioVenta.getText().toString()));
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

    }

}
