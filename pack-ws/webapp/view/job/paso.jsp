<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<tiles:insertDefinition name="default">
	<tiles:putAttribute name="body">
		<script type="text/javascript" src="${pageContext.request.contextPath}/view/job/js/index.js"></script>
		<span id="labelJSON" class="hide">${requestScope.applications}</span>
		<div id="layoutAplicacion" class="ui-accordion ui-widget ui-helper-reset">
			<label class="ui-accordion-header ui-accordion-header-active ui-corner-top ui-widget-header">Aplicaciones</label>
			<div class="ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom">
				<table style="width: 100%"><tr><td align="right">
					<button id="btnNuevo">Nuevo</button><!-- &nbsp;<button id="btnEliminar">Eliminar</button> --></td></tr>
				</table>
				<div id="pnlApplications"></div>
			</div>
		</div>
		
		<div id="layoutDetalleAplicacion" class="hide">
			<div class="ui-accordion ui-widget ui-helper-reset">
				<label class="ui-accordion-header ui-accordion-header-active ui-corner-top ui-widget-header">Aplicaci&#243;n: <span id="lblAplicacion">{requestScope.nombreAplicacion}</span></label>
				<div class="ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom">
					<table style="width: 100%; padding-bottom: 5px;">
						<tr>
							<td style="width: 100px;">JNDI:</td>
							<td>{requestScope.jndi}</td>
						</tr>
						<tr>
							<td>Descripci&#243;n:</td>
							<td>{requestScope.descripcion}</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="ui-accordion ui-widget ui-helper-reset">
				<label class="ui-accordion-header ui-accordion-header-active ui-corner-top ui-widget-header">Trabajos</label>
				<div class="ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom">
					<div id="pnlJobs"></div>
				</div>
			</div>
		</div>

		<div id="layoutDetalleTrabajo" class="hide">
			<div class="ui-accordion ui-widget ui-helper-reset">
				<label class="ui-accordion-header ui-accordion-header-active ui-corner-top ui-widget-header">Trabajo: <span id="lblTrabajo">{requestScope.nombreAplicacion} : {requestScope.nombreTrabajo}</span></label>
				<div class="ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom">
					<table style="width: 100%; padding-bottom: 5px;">
						<tr>
							<td style="width: 120px;">Expresi&#243;n Cron:</td>
							<td>{requestScope.cron}</td>
						</tr>
						<tr>
							<td>Descripci&#243;n:</td>
							<td>{requestScope.descripcion}</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="ui-accordion ui-widget ui-helper-reset">
				<label class="ui-accordion-header ui-accordion-header-active ui-corner-top ui-widget-header">Pasos</label>
				<div class="ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom">
					<div id="pnlSteps"></div>
				</div>
			</div>
		</div>
		
		<div id="layoutDetallePaso">
			<table style="width: 100%">
				<tr>
					<td>
					<div class="ui-accordion ui-widget ui-helper-reset">
						<label class="ui-accordion-header ui-accordion-header-active ui-corner-top ui-widget-header">Entrada</label>
					</div>
					</td>
					<td>
					<div class="ui-accordion ui-widget ui-helper-reset">
						<label class="ui-accordion-header ui-accordion-header-active ui-corner-top ui-widget-header">Salida</label>
					</div>
					</td>
				</tr>
			</table>
		</div>

		<div id="dialogAplicacion">
			<table style="width: 95%; border-collapse: separate; border-spacing: 5px; margin: auto;">
				<tr>
					<td style="width: 100px">C&#243;digo</td>
					<td><b>:</b></td>
					<td><span>${requestScope.id}</span></td>
				</tr>
				<tr>
					<td>Nombre</td>
					<td><b>:</b></td>
					<td><input type="text" id="txtNombre" /></td>
				</tr>
				<tr>
					<td>Descripci&#243;n</td>
					<td><b>:</b></td>
					<td><input type="text" id="txtDescripcion" /></td>
				</tr>
				<tr>
					<td>JNDI</td>
					<td><b>:</b></td>
					<td><input type="text" id="txtJNDI" /></td>
				</tr>
				<tr>
					<td colspan="3" align="right">
						<button id="btnAceptar">Aceptar</button>
						<button id="btnCancelar">Cancelar</button>
					</td>
				</tr>
			</table>
		</div>

	</tiles:putAttribute>
</tiles:insertDefinition>
