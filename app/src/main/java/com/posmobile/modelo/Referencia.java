package com.posmobile.modelo;

import java.io.Serializable;

/**
 * Created by personal on 13/11/2017.
 */


@SuppressWarnings("serial")
public class Referencia implements Serializable {
    private String Id;
    private String nombre;
    private String descripcion;
    private double precioVenta;
    private double precioCompra;
    private double cantidadDisponible;
    private double cantidadAPedir;

    public Referencia() {

    }

    public Referencia(String id, String nombre, String descripcion, double precioCompra, double precioVenta, double cantidadDisponible) {
        this.setId(id);
        this.setNombre(nombre);
        this.setDescripcion(descripcion);
        this.setPrecioCompra(precioCompra);
        this.setPrecioVenta(precioVenta);
        this.setCantidadDisponible(cantidadDisponible);
    }

    public Referencia(String id, String nombre, String descripcion, double precioCompra,
                      double precioVenta, double cantidadDisponible, double cantidadAPedir) {
        this.setId(id);
        this.setNombre(nombre);
        this.setDescripcion(descripcion);
        this.setPrecioCompra(precioCompra);
        this.setPrecioVenta(precioVenta);
        this.setCantidadDisponible(cantidadDisponible);
        this.setCantidadAPedir(cantidadAPedir);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public double getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(double cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public double getCantidadAPedir() {
        return cantidadAPedir;
    }

    public void setCantidadAPedir(double cantidadAPedir) {
        this.cantidadAPedir = cantidadAPedir;
    }
}
