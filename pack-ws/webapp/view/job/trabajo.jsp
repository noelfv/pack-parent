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
								<!-- &nbsp;<button id="btnEliminar">Eliminar</button> -->
							</td>
						</tr>
					</table>
					<div id="pnlJobs"></div>
				</div>
			</div>
		</div>


<div id="update">  
    <select name="source"></select>  
    <select name="target"></select>  
</div>

<div id="tabs">  
    <ul>  
        <li><a href="#tab1">Tab 1</a></li>  
        <li><a href="#tab2">Tab 2</a></li>  
        <li><a href="#tab3">Tab 3</a></li>  
    </ul>  
    <div>  
        <div id="tab1">  
            The story begins as Don Vito Corleone, the head of a New York Mafia family, oversees his daughter's wedding. His beloved son Michael has just come home from the war, but does not intend to become part of his father's business. T hrough Michael's life the nature of the family business becomes clear. The business of the family is just like the head of the family, kind and benevolent to those who give respect, but given to ruthless violence whenever anything stands against the good of the family.  
        </div>  
        <div id="tab2">  
            Francis Ford Coppola's legendary continuation and sequel to his landmark 1972 film, The_Godfather, parallels the young Vito Corleone's rise with his son Michael's spiritual fall, deepening The_Godfather's depiction of the dark side of the American dream. In the early 1900s, the child Vito flees his Sicilian village for America after the local Mafia kills his family. Vito struggles to make a living, legally or illegally, for his wife and growing brood in Little Italy, killing the local Black Hand Fanucci after he demands his customary cut of the tyro's business. With Fanucci gone, Vito's communal stature grows.  
        </div>  
        <div id="tab3">  
            After a break of more than 15 years, director Francis Ford Coppola and writer Mario Puzo returned to the well for this third and final story of the fictional Corleone crime family. Two decades have passed, and crime kingpin Michael Corleone, now divorced from his wife Kay has nearly succeeded in keeping his promise that his family would one day be completely legitimate.  
        </div>  
    </div>  
</div> 
<br><br>
<input id="default" type="text" /><br><br>

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
					<td><input id="job.name" type="text" style="width: 85%"/></td>
				</tr>
				<tr>
					<td>Cron</td>
					<td><b>:</b></td>
					<td><input id="job.cronExpression" type="text" style="width: 85%" /><br>

<div id="pnlCronExpression"> 
    <table>
    	<tr>
	        <td><input type="radio" name="cronType"/></td><td>Cada <input type="text" style="width: 30px;"/> minuto(s)</td>
	    </tr><tr>
	        <td><input type="radio" name="cronType"/></td><td>Cada <input type="text" style="width: 30px;"/> hora(s)</td>
	    </tr><tr>
	        <td><input type="radio" name="cronType"/></td><td>A las <input type="text" style="width: 60px"/></td>
	    </tr><tr>
	    	<td><input type="checkbox" /></td>
        <tr>
    </table>  
</div>

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
