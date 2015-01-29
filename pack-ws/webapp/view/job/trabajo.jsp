<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<tiles:insertDefinition name="default">
	<tiles:putAttribute name="body">
		<script type="text/javascript" src="${pageContext.request.contextPath}/view/job/js/jsTrabajo.js"></script>
		<input type="hidden" id="application.id" value="${requestScope.application.id}" />
		<div id="layoutTrabajo">
			<div class="ui-accordion ui-widget ui-helper-reset">
				<label class="ui-accordion-header ui-accordion-header-active ui-corner-top ui-widget-header">Aplicaci&#243;n: <span id="lblAplicacion"><b>${requestScope.application.name}</b></span></label>
				<div class="ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom">
					<table style="width: 100%;">
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
					<table style="width: 100%; padding-bottom: 10px;">
						<tr>
							<td>
								<table><tr>
									<td>Trabajo</td>
									<td><b>&nbsp;:&nbsp;</b></td>
									<td><input id="findJobName" type="text" style="width: 85%"/></td>
								</tr></table>
							</td>
							<td align="right">
								<button id="btnBuscar">Buscar</button>&nbsp;<button id="btnLimpiar">Limpiar</button>&nbsp;<button id="btnNuevo">Nuevo</button>
							</td>
						</tr>
					</table>
					<div id="pnlJobs"></div>
				</div>
			</div>
		</div>

		<div id="dialogTrabajo">
			<input id="job.version" type="hidden"/>
			<table style="width: 95%; border-collapse: separate; border-spacing: 5px; margin: auto;">
				<tr>
					<td style="width: 80px">C&#243;digo</td>
					<td><b>:</b></td>
					<td><div id="job.id" class="ui-label" style="width: 85%"></div></td>
				</tr>
				<tr>
					<td>Nombre</td>
					<td><b>:</b></td>
					<td><input id="job.name" type="text" style="width: 85%"/><input id="job.nameOld" type="hidden" /></td>
				</tr>
				<tr>
                    <td>Tipo</td>
                    <td><b>:</b></td>
                    <td><input id="job.type" type="text" style="width: 85%"/></td>
                </tr>
				<tr>
					<td>Cron</td>
					<td><b>:</b></td>
					<td><input id="job.cronExpression" type="text" style="width: 85%" />
					</td>
				</tr>
				<tr>
					<td>Descripci&#243;n</td>
					<td><b>:</b></td>
					<td>
						<table style="width: 100%"><tr>
							<td><input id="job.description" type="text" style="width: 85%" /></td>
							<td style="width: 20px;">
								<img id="job.description.validity" class="hide" src="${pageContext.request.contextPath}/public/img/exclamation_red_frame.png" title="La longitud de la descripci&#243;n no debe de exceder de 1200 caracteres">
							</td>
						</tr></table>
						
					</td>
				</tr>
			</table>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>
