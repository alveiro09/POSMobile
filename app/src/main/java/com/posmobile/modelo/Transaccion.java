package com.posmobile.modelo;

import com.posmobile.EnumTipoTransaccion;
import com.posmobile.modelo.Referencia;
import com.posmobile.modelo.Usuario;

import java.util.List;

/**
 * Created by personal on 19/11/2017.
 */

public class Transaccion {
    private String id;
    private EnumTipoTransaccion tipoTransaccion;
    private double neto;
    private double bruto;
    private double descuento;
    private String fecha;
    private List<Referencia> detalles;
    private String usuarioId;

    public Transaccion(String id, String usuario, String fecha, int tipoTransaccion, double neto, double bruto, double descuento) {
        this.setId(id);
        this.setTipoTransaccion(EnumTipoTransaccion.values()[tipoTransaccion]);
        this.setUsuario(usuario);
        this.setFecha(fecha);
        this.setBruto(bruto);
        this.setNeto(neto);
        this.setDescuento(descuento);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EnumTipoTransaccion getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(EnumTipoTransaccion tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public double getNeto() {
        return neto;
    }

    public void setNeto(double neto) {
        this.neto = neto;
    }

    public double getBruto() {
        return bruto;
    }

    public void setBruto(double bruto) {
        this.bruto = bruto;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public List<Referencia> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<Referencia> detalles) {
        this.detalles = detalles;
    }

    public String getusuarioId() {
        return usuarioId;
    }

    public void setUsuario(String usuarioId) {
        this.usuarioId = usuarioId;
    }
}
