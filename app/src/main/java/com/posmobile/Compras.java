package com.posmobile;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
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
    private static final String urlRef = "http://poswebapi.azurewebsites.net/api/Producto";
    RequestQueue queue;
    MyApplication application;
    ProgressDialog progressDialogIV;
    ProgressDialog progressDialogAR;
    ProgressDialog progressDialogM;
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

    @RequiresApi(api = Build.VERSION_CODES.M)
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
                InsertarActualizarCompra(true);
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                RecorrerDatalles();
                referenciasAComprar.clear();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void mostrarInfo() {
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.listaReferenciasSeleccionadas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        if ((referenciasAComprar != null) && (referenciasAComprar.size() > 0)) {
            progressDialogM = new ProgressDialog(getContext());
            progressDialogM.setMessage("Actualizando datos...");
            progressDialogM.show();

            adapter = new ReferenciasAdapter(getContext(), referenciasAComprar, new IData() {
                @Override
                public void onItemSelected(Referencia referenciaFrag) {

                }
            }, false, true);
            recyclerView.setAdapter(adapter);
            progressDialogM.dismiss();

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

    private void InsertarActualizarCompra(boolean insertar) {

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

        progressDialogIV = new ProgressDialog(getContext());
        progressDialogIV.setMessage("Guardando información...");
        progressDialogIV.show();
        Map<String, String> params = new HashMap<String, String>();

        params.put("idTransaccion", transaccion.getId());
        params.put("TipoTransaccion", String.valueOf(transaccion.getTipoTransaccion().ordinal()));
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
                        progressDialogIV.dismiss();
                        Toast.makeText(getContext(), "Compra Creada", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialogIV.dismiss();
                if (error.getCause().toString().equalsIgnoreCase("org.json.JSONException: Value false of type java.lang.String cannot be converted to JSONObject"))
                    Toast.makeText(getContext(), "Compra Creada", Toast.LENGTH_LONG).show();
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

    private void RecorrerDatalles() {
        for (Referencia ref : referenciasAComprar) {
            ref.setCantidadDisponible(ref.getCantidadDisponible() + ref.getCantidadAPedir());
            InsertarActualizarReferencia(ref, false);
        }
    }


    private void InsertarActualizarReferencia(final Referencia referencia, boolean insertar) {
        progressDialogAR = new ProgressDialog(getContext());
        progressDialogAR.setMessage("Guardando información...");
        progressDialogAR.show();
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", referencia.getId());
        params.put("idProducto", referencia.getIdProducto());
        params.put("nombre", referencia.getNombre());
        params.put("descripcion", referencia.getDescripcion());
        params.put("cantidadDisponible", Double.toString(referencia.getCantidadDisponible()));
        params.put("precioVenta", Double.toString(referencia.getPrecioVenta()));
        params.put("precioCompra", Double.toString(referencia.getPrecioCompra()));
        params.put("cantidadDisponible", Double.toString(referencia.getCantidadDisponible()));


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(insertar ? Request.Method.POST : Request.Method.PUT,
                urlRef + "/" + referencia.getId(), new JSONObject(params),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialogAR.dismiss();
                        Toast.makeText(getContext(), "Referencia Actualizada", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialogAR.dismiss();
                //if (error.getCause().toString().equalsIgnoreCase("org.json.JSONException: Value false of type java.lang.String cannot be converted to JSONObject"))
                Toast.makeText(getContext(), "Referencia Actualizada", Toast.LENGTH_LONG).show();
//                else
//                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
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
