package com.posmobile;

import android.app.ProgressDialog;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.posmobile.adapters.ReferenciasAdapter;
import com.posmobile.modelo.Referencia;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SeleccionProducto extends Fragment {

    private static final String url = "http://poswebapi.azurewebsites.net/api/Producto";
    private RecyclerView recyclerView;
    private ReferenciasAdapter adapter;
    private ArrayList<Referencia> Referenciaservicio = new ArrayList<>();
    ProgressDialog progressDialog;
    RequestQueue requestQueue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.activity_seleccion_producto, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        requestQueue = Volley.newRequestQueue(getContext());
        mostrarInfo();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void mostrarInfo() {

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Cargando datos...");
        progressDialog.show();
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.listaReferencias);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ReferenciasAdapter(getContext(), Referenciaservicio, data, true);
        recyclerView.setAdapter(adapter);

        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject objeto = response.getJSONObject(i);
                        String id = objeto.getString("IdProducto");
                        String nombre = objeto.getString("Nombre");
                        String descripcion = objeto.getString("Descripcion");
                        double precioCompra = objeto.getDouble("PrecioCompra");
                        double precioVenta = objeto.getDouble("PrecioVenta");
                        double cantidadDisponible = objeto.getDouble("CantidadDisponible");

                        Referenciaservicio.add(new Referencia(id, nombre, descripcion, precioCompra, precioVenta, cantidadDisponible));
                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "" + e, Toast.LENGTH_LONG).show();
                    }
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        });

        requestQueue.add(request);

    }

    IData data = new IData() {
        @Override
        public void onItemSelected(Referencia referenciaFrag) {
            Compras compras = new Compras();

            Bundle bundle = new Bundle();
            bundle.putSerializable("referenciaAdd", referenciaFrag);
            compras.setArguments(bundle);

            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.listaReferenciasSeleccionadas, compras).commit();
        }
    };
}
