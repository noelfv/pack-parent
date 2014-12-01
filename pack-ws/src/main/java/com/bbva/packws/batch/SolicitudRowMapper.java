package com.bbva.packws.batch;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bbva.packws.webservice.solicitud.Solicitud;

public class SolicitudRowMapper implements RowMapper<Solicitud> {
	 
		@Override
		public Solicitud mapRow(ResultSet rs, int rowNum) throws SQLException {
	 
			Solicitud solicitud = new Solicitud();
	 
			solicitud.setSolicitud(rs.getString("solicitud"));
			solicitud.setCodigoProducto(rs.getString("codigoProducto"));
			solicitud.setCodigoSubProducto(rs.getString("codigoSubProducto"));
			solicitud.setEstado(rs.getString("estado"));
			solicitud.setFechaAlta(null);
			solicitud.setImporte(rs.getDouble("importe"));
			solicitud.setDivisa(rs.getString("divisa"));
			solicitud.setTipoDOI(rs.getString("tipoDOI"));
			solicitud.setNumDOI(rs.getString("numDOI"));
			solicitud.setCodigoCliente(rs.getString("codigoCliente"));
			solicitud.setContrato(rs.getString("contrato"));
			solicitud.setPlazo(rs.getString("plazo"));
			solicitud.setOficina(rs.getString("oficina"));
			solicitud.setEjecutivo(rs.getString("ejecutivo"));
			solicitud.setTasa(rs.getDouble("tasa"));
			
			return solicitud;
		}

}
