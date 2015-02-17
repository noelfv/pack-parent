<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<tiles:insertDefinition name="default">
	<tiles:putAttribute name="body">
		<script type="text/javascript" src="${pageContext.request.contextPath}/view/scheduler/js/index.js"></script>
		<div id="pnlTareas" class="ui-accordion ui-widget ui-helper-reset">
			<label class="ui-accordion-header ui-accordion-header-active ui-corner-top ui-widget-header">Estado de las tareas programadas</label>
			<div class="ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom">
				<div id="pnlTrigger"></div>
			</div>
		</div>

        <div id="pnlDetalleTarea" class="ui-accordion ui-widget ui-helper-reset hide">
            <label class="ui-accordion-header ui-accordion-header-active ui-corner-top ui-widget-header">Estado de la ultima ejecuci&#243;n</label>
            <div id="pnlDesconocido" class="hide ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom">No existe informaci&#243;n sobre el estado del trabajo</div>
            <div id="pnlDetalleEjecucion" class="hide ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom">
                <div>Trabajo</div>
                <div id="pnlJobExecutions"></div>
                <br/>
                <div>Parametros</div>
                <div id="pnlParametros"></div>
                <br/>
                <div>Pasos</div>
                <div id="pnlPasos"></div>
            </div>
        </div>

	</tiles:putAttribute>
</tiles:insertDefinition>
