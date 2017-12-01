package com.posmobile.modelo;

import java.io.Serializable;

/**
 * Created by personal on 13/11/2017.
 */

@SuppressWarnings("serial")
public class Usuario implements Serializable {
    private String id;
    private String numeroIdentificacion;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String nombreUsuario;
    private String contrasena;

    public Usuario(){

    }

    public Usuario(String Id, String numeroIdentificacion, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido,
                   String nombreUsuario,String contrasena){
        this.setId(Id);
        this.setNumeroIdentificacion(numeroIdentificacion);
        this.setPrimerNombre(primerNombre);
        this.setSegundoNombre(segundoNombre);
        this.setPrimerApellido(primerApellido);
        this.setSegundoApellido(segundoApellido);
        this.setNombreUsuario(nombreUsuario);
        this.setContrasena(contrasena);
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
