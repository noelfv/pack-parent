<%@taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/view/estadofinanciero/js/estadoFinanciero.js"></script>

<div style="width:100%">
 	<table style="width:100%">
		<tr class="tr_cabecera">
			<td colspan="8">
				<div class="cabecera_tabla">
					<span class="titulo_tabla"> Ingreso de Estados Financieros </span>
				</div>
			</td>
		</tr>
		<tr><td colspan="8">&nbsp;</td></tr>
		<tr>
			<td class="etiquetagris">Cliente:</td>
			<td style="width: 250px;"> <input id="txtCliente" class="ui-text" readonly="readonly" value="${model.cliente.nombreCliente}"/></td>
			<td style="width: 10px;"/>
			<td class="etiquetagris">Código Central:</td>
			<td><input class="ui-text" id="txtCodCentral" readonly="readonly" name="txtCodCentral" value="${model.cliente.codigoCentral}"/></td>
			<td style="width: 10px;"/>
			<td class="etiquetagris">RUC: </td>
			<td><input class="ui-text" id="txtNroDocumento" readonly="readonly" value="${model.cliente.nroDocumento}"/></td>
		</tr>
	</table>
</div>

	<br>
	
	<div id="tabs">
		<ul>
			<li><a href="#panelCliente" class="titulo_tabla"> Datos del Cliente </a></li>
			<li><a href="#panelEstadoFinanciero" class="titulo_tabla, hide" id="hrEstadoFinanciero"> Listado de Ejercicios </a></li>
		</ul>
		
		<div id="panelCliente">
		  <div>
		   <form id="formEEFF">
		    <input type="hidden" id="hdListaEjercicios" name="hdListaEjercicios">
		    <input type="hidden" id="hdCodigoCentral" name="hdCodigoCentral">
		 	<table style="width:100%">
				<tr class="tr_cabecera">
						<td colspan="15">
							<div class="cabecera_tabla">
								<span class="titulo_tabla"> Datos del Ejercicio </span>
							</div>
						</td>
				</tr>
				<tr><td colspan="15">&nbsp;</td></tr>
				<tr>
					<td class="etiquetagris" style="width:120px">Periodo Desde:</td>
					<td style="width: 180px;"> 
						<input id="txtMesInicio" name="txtMesInicio" class="ui-text" style="width:50px" maxlength="2" onkeypress="return ingresarSoloNumero(event);"/>
						<input id="txtAnioInicio" name="txtAnioInicio" class="ui-text" style="width:50px" maxlength="4" onkeypress="return ingresarSoloNumero(event);"/>
					</td>
					<td style="width: 10px;"/>
					
					
					<td class="etiquetagris" style="width:60px">Hasta:</td>
					<td style="width:180px">
						<input class="ui-text" id="txtMesFin" name="txtMesFin" style="width:50px" maxlength="2" onkeypress="return ingresarSoloNumero(event);"/>
						<input class="ui-text" id="txtAnioFin" name="txtAnioFin" style="width:50px" maxlength="4" 
								onkeypress="return ingresarSoloNumero(event);"
								onchange="javascript:calcularMeses()"/>
					</td>
					<td style="width: 10px;"/>
					
					
					<td class="etiquetagris" style="width:80px">Meses: </td>
					<td style="width:30px">
						<input id="spinner" name="meses" style="width:30px" readonly="readonly" class="ui-text">
					</td>
					<td style="width: 10px;"/>
					<td class="etiquetagris" style="width:50px">En: </td>
					<td style="width:60px">
<%-- 						<select id="cboTipoDoc" name="cboTipoDoc" class="sel"> --%>
<!-- 							<option>UNIDADES</option> -->
<!-- 							<option>MILES</option> -->
<!-- 							<option>MILLONES</option> -->
<!-- 							<option>CIEN MIL</option> -->
<%-- 						</select> --%>
						<s:select 
							headerKey="-1"
							headerValue="[Seleccione]"
							list="model.listaUnidadesMedida"
							listKey="id"
							listValue="texto"
							cssClass="sel"
							cssStyle="width: 100px;"
							id="cmbUnidadesMedida"
							name="cmbUnidadesMedida"/>
					</td>
				</tr>
				<tr><td colspan="15">&nbsp;</td></tr>
				<tr>
					<td class="etiquetagris" style="width:100px">N. Empleados:</td>
					<td style="width: 130px;"> 
						<input id="txtCantidadEmpledos" name="txtCantidadEmpledos" class="ui-text" style="width:80px" onkeypress="return ingresarSoloNumero(event);"/>
					</td>
					<td style="width: 10px;"/>
					
					<td class="etiquetagris" style="width:100px">Estructura Productiva:</td>
						<td style="width:60px">
<%-- 						<select id="cboTipoDoc" name="cboTipoDoc" class="sel"> --%>
<!-- 							<option>INDUSTRIAL</option> -->
<!-- 							<option>COMERCIAL SERVICIOS</option> -->
<!-- 							<option>MIXTA</option> -->
<%-- 						</select> --%>
						<s:select 
							headerKey="-1"
							headerValue="[Seleccione]"
							list="model.listaEstructuraProductiva"
							listKey="id"
							listValue="texto"
							cssClass="sel"
							cssStyle="width: 150px;"
							id="cmbEstructuraProductiva"
							name="cmbEstructuraProductiva"/>
					</td>
					<td style="width: 10px;"/>
					<td class="etiquetagris" style="width:100px">Certificación: </td>
					<td style="width:60px">
<%-- 						<select id="cboTipoDoc" name="cboTipoDoc" class="sel"> --%>
<!-- 							<option>AUDITADA</option> -->
<!-- 							<option>FISCAL/SUNAT</option> -->
<!-- 							<option>GESTION</option> -->
<%-- 						</select> --%>
						<s:select 
							headerKey="-1"
							headerValue="[Seleccione]"
							list="model.listaCertificacion"
							listKey="id"
							listValue="texto"
							cssClass="sel"
							onchange="javascript:mostrarAuditoria()"
							cssStyle="width: 150px;"
							id="cmbCertificacion"
							name="cmbCertificacion"/>
					</td>
				</tr>
				<tr><td colspan="15">&nbsp;</td></tr>
				<tr id="filaDatosAuditor" class="hide">
					<td colspan="15">
						<table>
							<tr>
								<td class="etiquetagris" style="width:120px">Nombre del Auditor:</td>
								<td style="width: 280px;"> 
									<input id="txtNombreAuditor" name="txtNombreAuditor" class="ui-text" style="width:250px"/>
								</td>
								<td style="width: 10px;"/>
								<td class="etiquetagris" style="width:80px">Procedencia:</td>
								<td style="width: 150px;"> 
									<input id="txtProcedenciaAuditor" name="txtProcedenciaAuditor" class="ui-text" style="width:180px"/>
								</td>		
							</tr>
						</table>
					</td>
				</tr>
				<tr><td colspan="15">&nbsp;</td></tr>
				<tr id="filaOpinionAuditor" class="hide">
					<td colspan="15">
						<table>
							<tr>
								<td class="etiquetagris" style="width:120px">Opinión del Auditor:</td>
							</tr>
							<tr>
								<td>
									<textarea rows="10" cols="100" id="txtOpinionAuditor" name="txtOpinionAuditor"></textarea>
								</td>
								<td style="width:120px">&nbsp;</td>
<!-- 								<td> -->
<%-- 									<a href="javascript:buscarCliente()"><img data-title="Continuar Registro" class="menuItemBarImg" src="<%=request.getContextPath()%>/public/img/continuar.png" align="top"></a> --%>
<!-- 								</td> -->
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="15" align="right">
						<a href="#" id="hrContinuar"><img data-title="Continuar Registro" class="menuItemBarImg" src="<%=request.getContextPath()%>/public/img/continuar.png" align="top"></a>
					</td>
				</tr>
			</table>
		   </form>
		  </div>
		</div>
		
		<div id="panelEstadoFinanciero" class="hide">
			<div>
				<table style="width:100%">
					<tr class="tr_cabecera">
						<td>
							<div class="cabecera_tabla">
								<span class="titulo_tabla"> Estados Financieros </span>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<br>
			<a href="#" id="hrGuardarEEFF">
				<img data-title="Guardar Estado Financiero" class="menuItemBarImg" src="<%=request.getContextPath()%>/public/img/guardar.png" align="right">
			</a>
			<br><br>
			<div id="tblEjercicios" style="width: 100%; height: 350px; overflow: auto; margin: auto;"></div>
		</div>
	</div>
