<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<tiles:insertDefinition name="default">
	<tiles:putAttribute name="body">
		<script type="text/javascript" src="${pageContext.request.contextPath}/view/job/js/jsTrabajo.js"></script>
		<div id="layoutDetalleAplicacion">
			<div class="ui-accordion ui-widget ui-helper-reset">
				<label class="ui-accordion-header ui-accordion-header-active ui-corner-top ui-widget-header">Aplicaci&#243;n: <span id="lblAplicacion"><b>${requestScope.application.name}</b></span></label>
				<div class="ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom">
					<table style="width: 100%; padding-bottom: 5px;">
						<tr>
							<td style="width: 90px;">JNDI</td>
							<td style="width: 15px;"><b>:</b></td>
							<td>${requestScope.application.jndi}</td>
						</tr>
						<tr>
							<td>Descripci&#243;n</td>
							<td><b>:</b></td>
							<td>${requestScope.application.description}</td>
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

	</tiles:putAttribute>
</tiles:insertDefinition>
