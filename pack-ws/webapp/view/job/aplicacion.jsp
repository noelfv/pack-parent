<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<tiles:insertDefinition name="default">
	<tiles:putAttribute name="body">
		<script type="text/javascript" src="${pageContext.request.contextPath}/view/job/js/jsAplicacion.js"></script>
		<div id="layoutAplicacion" class="ui-accordion ui-widget ui-helper-reset">
			<label class="ui-accordion-header ui-accordion-header-active ui-corner-top ui-widget-header">Aplicaciones</label>
			<div class="ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom">
				<table style="width: 100%; padding-bottom: 10px;">
					<tr>
						<td>
							<table><tr>
								<td>Aplicaci&#243;n</td>
								<td><b>&nbsp;:&nbsp;</b></td>
								<td><input id="findApplicationName" type="text" style="width: 85%"/></td>
							</tr></table>
						</td>
						<td align="right">
							<button id="btnBuscar">Buscar</button>&nbsp;<button id="btnLimpiar">Limpiar</button><!-- &nbsp;<button id="btnNuevo">Nuevo</button> -->
							<!-- &nbsp;<button id="btnEliminar">Eliminar</button> -->
						</td>
					</tr>
				</table>
				<div id="pnlApplications"></div>
			</div>
		</div>
		
		<div id="dialogAplicacion">
			<input id="application.version" type="hidden"/>
			<table style="width: 95%; border-collapse: separate; border-spacing: 5px; margin: auto;">
				<tr>
					<td style="width: 80px">C&#243;digo</td>
					<td><b>:</b></td>
					<td><div id="application.id" class="ui-label" style="width: 85%"></div></td>
				</tr>
				<tr>
					<td>Nombre</td>
					<td><b>:</b></td>
					<td><div id="application.name" class="ui-label" style="width: 85%"></div><!-- <input id="application.name" type="text" style="width: 85%"/> --></td>
				</tr>
				<tr>
					<td>JNDI</td>
					<td><b>:</b></td>
					<td><input id="application.jndi" type="text" style="width: 85%" /></td>
				</tr>
				<tr>
					<td>Descripci&#243;n</td>
					<td><b>:</b></td>
					<td>
						<table style="width: 100%"><tr>
							<td><input id="application.description" type="text" style="width: 85%" /></td>
							<td style="width: 20px;">
								<img id="application.description.validity" class="hide" src="${pageContext.request.contextPath}/public/img/exclamation_red_frame.png" title="La longitud de la descripci&#243;n no debe de exceder de 1200 caracteres">
							</td>
						</tr></table>
						
					</td>
				</tr>
			</table>
		</div>

	</tiles:putAttribute>
</tiles:insertDefinition>
