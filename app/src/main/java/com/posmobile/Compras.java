package com.posmobile;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.posmobile.adapters.ReferenciasAdapter;
import com.posmobile.adapters.TransaccionesAdapter;
import com.posmobile.adapters.UsuariosAdapter;
import com.posmobile.modelo.Referencia;
import com.posmobile.modelo.Transaccion;
import com.posmobile.modelo.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Compras extends Fragment {
    static ArrayList<Referencia> referenciasAComprar = new ArrayList<>();
    private static final String url = "http://poswebapi.azurewebsites.net/api/Transaccion";
    RequestQueue queue;
    MyApplication application;
    ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private ReferenciasAdapter adapter;
    private Button btnGuardar, btnMostrar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_compras, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(getContext());
        application = (MyApplication) getActivity().getApplication();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        referenciasAComprar.clear();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mostrarInfo();
        btnGuardar = (Button) getActivity().findViewById(R.id.bntGuardar);
        btnMostrar = (Button) getActivity().findViewById(R.id.bntMostrar);
        btnMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarInfo();
            }
        });
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertarActualizarReferencia(true);
            }
        });
    }

    public void mostrarInfo() {
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.listaReferenciasSeleccionadas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        if ((referenciasAComprar != null) && (referenciasAComprar.size() > 0)) {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Actualizando datos...");
            progressDialog.show();

            adapter = new ReferenciasAdapter(getContext(), referenciasAComprar, new IData() {
                @Override
                public void onItemSelected(Referencia referenciaFrag) {

                }
            }, false, true);
            recyclerView.setAdapter(adapter);
            progressDialog.dismiss();

        }
    }

    private double CalcularBruto() {
        double bruto = 0;
        for (Referencia ref : referenciasAComprar) {
            bruto = bruto + ref.getPrecioCompra() * ref.getCantidadAPedir();
        }
        return bruto;
    }

    private double CalcularDescuento() {
        double descuento = 0;
        /*
        for (Referencia ref: referenciasAComprar ) {
            bruto=bruto+ref.getPrecioCompra()*ref.getCantidadAPedir();
        }*/
        return descuento;
    }

    private void InsertarActualizarReferencia(boolean insertar) {

        Transaccion transaccion = new Transaccion();
        transaccion.setBruto(CalcularBruto());
        transaccion.setNeto(transaccion.getBruto() - CalcularDescuento());
        transaccion.setFecha(new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()));
        transaccion.setTipoTransaccion(EnumTipoTransaccion.Compra);
        transaccion.setDetalles(referenciasAComprar);
        transaccion.setId(UUID.randomUUID().toString());
        application = (MyApplication) getActivity().getApplication();
        if ((application != null) && (application.getUsuarioActual() != null))
            transaccion.setUsuario(application.getUsuarioActual().getNumeroIdentificacion());
        else
            transaccion.setUsuario("");

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Guardando información...");
        progressDialog.show();
        Map<String, String> params = new HashMap<String, String>();

        params.put("idTransaccion", transaccion.getId());
        params.put("TipoTransaccion", transaccion.getTipoTransaccion().toString());
        params.put("Neto", Double.toString(transaccion.getNeto()));
        params.put("Bruto", Double.toString(transaccion.getBruto()));
        params.put("Descuento", Double.toString(transaccion.getDescuento()));
        params.put("Fecha", transaccion.getFecha());
        params.put("Detalles", transaccion.getDetalles().toString());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(insertar ? Request.Method.POST : Request.Method.PUT,
                url, new JSONObject(params),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Compra Creada", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                if (error.getCause().toString().equalsIgnoreCase("org.json.JSONException: Value false of type java.lang.String cannot be converted to JSONObject"))
                    Toast.makeText(getContext(), "Referencia Creada", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
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
