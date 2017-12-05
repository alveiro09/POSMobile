package com.posmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.posmobile.modelo.Usuario;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    MyApplication application;
    TextView user, userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        application = (MyApplication) getApplication();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        user = (TextView) findViewById(R.id.user);
        userName = (TextView) findViewById(R.id.userName);
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
/*
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
       // actualizarUsuarioActual();
        return super.onMenuOpened(featureId, menu);
    }
    */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        IniciarActividad(AcercaDe.class);

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.nav_iniciarSesion)
            IniciarActividad(IniciarSesion.class);
        else {
            if (application == null)
                application = (MyApplication) getApplication();
            if ((application != null) && (application.getUsuarioActual() != null)) {
                switch (item.getItemId()) {
                    case R.id.nav_cerrarSesion:
                        IniciarActividad(CerrarSesion.class);
                        break;
                    case R.id.nav_compra:
                        //IniciarActividad(Compras.class);
                        IniciarActividad(ComprasTabs.class);
                        break;
                    case R.id.nav_venta:
                        IniciarActividad(VentasTabs.class);
                        break;
                    case R.id.nav_referencia:
                        IniciarActividad(Referencias.class);
                        break;
                    case R.id.nav_crearReferencias:
                        IniciarActividad(CrearReferencias.class);
                        break;
                    case R.id.nav_repReferencia:
                        IniciarActividad(ReporteReferencias.class);
                        break;
                    case R.id.nav_repCompras:
                        IniciarActividad(ReporteCompras.class);
                        break;
                    case R.id.nav_repVentas:
                        IniciarActividad(ReporteVentas.class);
                        break;
                    case R.id.nav_Usuarios:
                        IniciarActividad(Usuarios.class);
                        break;
                    case R.id.nav_crearUsuarios:
                        IniciarActividad(CrearUsuarios.class);
                        break;

                    default:
                        break;
                }
            } else {
                Toast.makeText(MainActivity.this, "Por favor inicie sesión para acceder a esta opción", Toast.LENGTH_LONG).show();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void IniciarActividad(Class<?> actividad) {
        Intent intent = new Intent(this, actividad);
        startActivity(intent);
    }
    private void actualizarUsuarioActual() {
        if (application == null)
            application = (MyApplication) getApplication();
        if ((application != null) && (user!=null)) {
            user.setText(application.getUsuarioActual().getNombreUsuario());
            userName.setText(application.getUsuarioActual().getNombreUsuario());
        }
    }
}
