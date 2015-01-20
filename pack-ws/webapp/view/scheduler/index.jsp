<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<tiles:insertDefinition name="default">
	<tiles:putAttribute name="body">
		<script type="text/javascript" src="${pageContext.request.contextPath}/view/scheduler/js/index.js"></script>
		<div class="ui-accordion ui-widget ui-helper-reset" style="padding-bottom: 10px;">
			<label class="ui-accordion-header ui-accordion-header-active ui-corner-top ui-widget-header">Estado de las tareas programadas</label>
			<div class="ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom">
				<table style="width: 100%; padding-bottom: 5px;">
					<tr>
						<td align="right">
							<button id="btnReprogramar" title="Reprogramar">Reprogramar</button>
						</td>
					</tr>
				</table>
				<div id="pnlTrigger"></div>
			</div>
		</div>
		<div id="dialogReprogramar">
			<table style="margin: auto; width: 100%">
				<tr>
					<td>Hora de ejecuci�n: </td>
					<td style="padding-left: 10px;"><input type="text" id="txtTimer" value="" class="ui-text-time" /></td>
				</tr>
				<tr>
					<td style="height: 25px;"></td>
				</tr>
				<tr>
					<td colspan="2" align="right">
						<button id="btnAceptar">Aceptar</button>
						<button id="btnCancelar">Cancelar</button>
					</td>
				</tr>
			</table>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>