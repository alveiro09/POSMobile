package com.posmobile;

import android.app.Application;

import com.posmobile.modelo.Usuario;

/**
 * Created by amejia on 29/11/2017.
 */

public class MyApplication extends Application {
    private Usuario usuarioActual;

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }
}
