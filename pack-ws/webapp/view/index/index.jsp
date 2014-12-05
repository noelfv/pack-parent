<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<tiles:insertDefinition name="default">
	<tiles:putAttribute name="body">
		<script type="text/javascript">
			var iniciarOperacion = function() {
				var _row = {},
					_sms = "",
					_post = $.ajax({
						url:  obtenerContexto("scheduler/obtenerJob.html")
						dataType: 'json',
					    accepts: {
					        xml: 'text/xml',
					        text: 'text/plain',
					        json: 'application/json'
					    }
					});
				
				$("#tblJobExecutions").jqGrid('GridUnload');
				
				_sms = "<thead><tr>";
				_sms += "<th>Trabajo</th>";
				_sms += "<th>Inicio</th>";
				_sms += "<th>Creado</th>";
				_sms += "<th>Termino</th>";
				_sms += "<th>Ultima Ejecución</th>";
				_sms += "<th>Estado</th>";
				_sms += "<th></th>";
				_sms += "</tr></thead>";				
				$("#tblJobExecutions").append(_sms);
				
				_post.success(function(request){
				console.log(request);
					if(request.tipoResultado == 'EXITO') {
						_sms = "<tr>";
						_sms += "<td>" + request.runningJobInstances[0].jobName + "</td>";
						_sms += "<td>" + request.runningJobInstances[0].startTime + "</td>";
						_sms += "<td>" + request.runningJobInstances[0].createTime + "</td>";
						_sms += "<td>" + request.runningJobInstances[0].endTime + "</td>";
						_sms += "<td>" + request.runningJobInstances[0].lastUpdated + "</td>";
						_sms += "<td>" + request.runningJobInstances[0].exitStatus.exitCode + "</td>";
						_sms += "<td><button id=\"btnEjecutar\" title=\"Iniciar Operación\">Ejecutar</button></td>";
						_sms += "</tr>";				
						$("#tblJobExecutions").append(_sms);
						tableToGrid("#tblJobExecutions");
					} else if(request.tipoResultado == 'ERROR_SISTEMA') {
						openJqError({type: "SYS", content: request.mensaje});
					}
				});

				$("#btnEjecutar").button({icons: {primary: "ui-icon-play"}, text: false }); 
				$("#btnEjecutar").on("click", iniciarOperacion);
			}
			
			$(document).ready(function() {
				tableToGrid("#tblJobExecutions");
				$("#btnEjecutar").button({icons: {primary: "ui-icon-play"}, text: false }); 
				$("#btnEjecutar").on("click", iniciarOperacion);				
			});
		</script>
		<div class="ui-accordion ui-widget ui-helper-reset" style="padding-bottom: 10px;">
			<label class="ui-accordion-header ui-accordion-header-active ui-corner-top ui-widget-header">Estado de las tareas programadas</label>
			<div class="ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom">
			dsfdsfds
			</div>
		</div>
		<div class="ui-accordion ui-widget ui-helper-reset">
			<label class="ui-accordion-header ui-accordion-header-active ui-corner-top ui-widget-header">Estado del ultimo trabajos</label>
			<div class="ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom">
				<table id="tblJobExecutions">
					<tr>
						<th>Trabajo</th>
						<th>Inicio</th>
						<th>Creado</th>
						<th>Termino</th>
						<th>Ultima Ejecución</th>
						<th>Estado</th>
						<th></th>
					</tr>
					<c:forEach var="job" items="${runningJobInstances}">
						<tr>
							<td><c:out value="${job.jobInstance.jobName}"/></td>
							<td><fmt:formatDate pattern="dd-MM-yyyy HH:mm:ss" value="${job.startTime}" /></td>
							<td><fmt:formatDate pattern="dd-MM-yyyy HH:mm:ss" value="${job.createTime}" /></td>
							<td><fmt:formatDate pattern="dd-MM-yyyy HH:mm:ss" value="${job.endTime}" /></td>
							<td><fmt:formatDate pattern="dd-MM-yyyy HH:mm:ss" value="${job.lastUpdated}" /></td>
							<td><c:out value="${job.exitStatus.exitCode}"/></td>
							<td><button id="btnEjecutar" title="Iniciar Operación">Ejecutar</button></td>
						</tr>
					</c:forEach>
				</table>
			
			</div>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>
