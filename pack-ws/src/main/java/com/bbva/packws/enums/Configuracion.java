package com.bbva.packws.enums;

public enum Configuracion {

    PB_HORA_JOB_GENERAR_ARCHIVO("PB_HORA_JOB_GENERAR_ARCHIVO"),
    PB_HORA_JOB_DEPURAR_ARCHIVO("PB_HORA_JOB_DEPURAR_ARCHIVO"),
    PB_RUTA_ARCHIVO("PB_RUTA_ARCHIVO"),
    PB_NOMBRE_ARCHIVO("PB_NOMBRE_ARCHIVO"),
    PB_FORMATO_FECHA_SUFIJO("PB_FORMATO_FECHA_SUFIJO"),
    PB_APAGAR_APLICACION_PLD("PB_APAGAR_APLICACION_PLD"),
    PB_NRO_REGISTRO("PB_NRO_REGISTRO"),
    PB_DIAS_ESPERA("PB_DIAS_ESPERA"),
    PB_HOST_MAIL("PB_HOST_MAIL"),
    PB_PORT_MAIL("PB_PORT_MAIL"),
    PB_USER_MAIL("PB_USER_MAIL"),
    PB_PASSWD_MAIL("PB_PASSWD_MAIL"),
    PB_LIST_SEND("PB_LIST_SEND"),
    PB_ROOT_CATEGORY("PB_ROOT_CATEGORY"),
    PB_FILE("PB_FILE"),
    PB_MAX_FILE_SIZE("PB_MAX_FILE_SIZE"),
    PB_MAX_BACKUP_INDEX("PB_MAX_BACKUP_INDEX"),
    PB_MESES_ANTERIORES("PB_MESES_ANTERIORES");

    private String key;

    Configuracion(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
