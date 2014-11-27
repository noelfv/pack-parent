package com.bbva.packws.util;

public class Constantes {

	public final static String HABILITAR_DUMMY = "1";
	public final static String MES_INICIO_PERIODO = "01";
	public final static String AJUSTES_DEFAULT = "N";
	public final static String CODIGO_CUENTA_VENTAS_NETAS = "6010203080504022";
	public final static String MESES_PERIODO = "12";
	public final static String CODIGO_EJERCICIO = "E";
	
	public final static Integer INDICADOR_MES = 0;
	public final static Integer INDICADOR_ANIO = 1;
	public final static Integer CONTADOR_INICIAL = 1;
	
	public static class Parametro {
		public final static Long ID_PARAMETRO_DUMMY = 23L;
		public final static Long ID_PARAMETRO_WS_CLIENTE_PE21 = 24L;
		public final static Long ID_PARAMETRO_WS_CLIENTE_PEC6 = 25L;
		public final static Long ID_PARAMETRO_WS_CLIENTE_RIG8 = 26L;
		public final static Long ID_PARAMETRO_WS_CLIENTE_ITDA = 27L;
		public final static Long ID_PARAMETRO_PADRE_APLICACIONES_WS = 28L;
		public final static Long ID_PARAMETRO_CANTIDAD_CONSULTAS_PERIODO = 32L;
		public final static Long ID_PARAMETRO_WS_CLIENTE_ITUC = 33L;
		public final static Long ID_PARAMETRO_IMPORTE_BALANCE = 70L;
		public final static Long ID_ESTADO_PENDIENTE = 80L;
		public final static Long ID_PARAMETRO_RUTA_PLANTILLA_REPORTE = 81L;
		
		
		public final static Long ID_PARAMETRO_PADRE_DATOS_EJERCICIO = 34L;
		public final static Long ID_PARAMETRO_PADRE_CABECERA_WS = 35L;
		public final static Long ID_PARAMETRO_PADRE_UNIDADES_MEDIDA = 46L;
		public final static Long ID_PARAMETRO_PADRE_ESTRUCTURA_PRODUCTIVA = 51L;
		public final static Long ID_PARAMETRO_PADRE_CERTIFICACION = 55L;
		public final static Long ID_PARAMETRO_PADRE_TIPOS_BALANCE = 71L;
		public final static Long ID_PARAMETRO_PADRE_ESTADOS_EEFF = 74L;
		
		public final static String CODIGO_TIPO_BALANCE_RATING = "9998";
		public final static String CODIGO_TIPO_BALANCE_SCORATING = "9999";
		public final static String TIPO_BALANCE_RATING = "RT";
		public final static String TIPO_BALANCE_SCORATING = "SC";
		public final static String CODIGO_ESTADO_ENVIADO_HOST = "E";
		public final static String CODIGO_ESTADO_PENDIENTE = "P";
		
		
		/*** DATOS DEL EJERCICIO ***/
		public final static Integer CODIGO_PERIODO_EEFF = 1;
		public final static Integer CODIGO_NRO_MESES = 2;
		public final static Integer CODIGO_ESTUDIO_ASOCIADO = 3;
		public final static Integer CODIGO_AJUSTES_REALIZADO = 4;
		public final static Integer CODIGO_CERTIFICACION = 5;
		public final static Integer CODIGO_SITUACION = 6;
		public final static Integer CODIGO_ORIGEN = 7;
		public final static Integer CODIGO_UNIDAD_MEDIDA = 8;
		public final static Integer CODIGO_MODELO = 9;
		public final static Integer CODIGO_ULTIMA_MODIFICACION = 10;
		public final static Integer CODIGO_ULTIMO_USUARIO = 11;
		
		
		public final static Integer TIMEOUT_WS = 6000;
	}
	
	public static class Mensaje {
		public final static String MSG_NO_ENCUENTRA_CLIENTE = "Cliente No Encontrado";
	}
}
