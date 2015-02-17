package com.bbva.packws.enums;

public enum Respuesta {

    SI('S'), NO('N');

    private Character respuesta;

    Respuesta(Character respuesta) {
        this.respuesta = respuesta;
    }

    public Character toCharacter() {
        return respuesta;
    }

}
