package com.bbva.packws.batch;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bbva.packws.domain.Solicitud;
import com.bbva.packws.domain.SolicitudCONELE;

public class SolicitudRowMapper implements RowMapper<Solicitud> {
	 
	@Override
	public Solicitud mapRow(ResultSet rs, int rowNum) throws SQLException {
 
		Solicitud solicitud = new SolicitudCONELE();
 
		solicitud.setSolicitud(rs.getString("SOLICITUD"));
		solicitud.setCodigoProducto(rs.getString("PRODUCTO_PACK"));
		solicitud.setCodigoSubProducto(rs.getString("SUBPRODUCTO_PACK"));
		solicitud.setEstado(rs.getString("ESTADO_PACK"));
		solicitud.setFechaAlta(rs.getDate("FECHA_ALTA"));
		solicitud.setImporte(rs.getDouble("IMPORTE"));
		solicitud.setDivisa(rs.getString("DIVISA"));
		solicitud.setTipoDOI(rs.getString("TIPODOCUMENTO_PACK"));
		solicitud.setNumDOI(rs.getString("NUM_DOI"));
		solicitud.setCodigoCliente(rs.getString("CODIGO_CLIENTE"));
		solicitud.setContrato(rs.getString("CONTRATO"));
		solicitud.setPlazo(rs.getLong("PLAZO"));
		solicitud.setOficina(rs.getString("OFICINA"));
		solicitud.setEjecutivo(rs.getString("EJECUTIVO"));
		solicitud.setTasa(rs.getDouble("TASA"));
		
		return solicitud;
	}
}
