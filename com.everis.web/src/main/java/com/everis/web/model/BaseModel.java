package com.everis.web.model;

import java.util.List;

import com.everis.enums.Resultado;
import com.everis.web.dto.Accion;

public class BaseModel {

    private Resultado tipoResultado;
    private String mensaje;
    private List<Accion> acciones;

    public Resultado getTipoResultado() {
        return tipoResultado;
    }

    public void setTipoResultado(Resultado tipoResultado) {
        this.tipoResultado = tipoResultado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<Accion> getAcciones() {
        return acciones;
    }

    public void setAcciones(List<Accion> acciones) {
        this.acciones = acciones;
    }
}
