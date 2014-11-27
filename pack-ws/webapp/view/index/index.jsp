<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript" src="<%=request.getContextPath()%>/view/index/js/scriptIndex.js"></script>

<div>
   <table>
		<tr>
			<td><input id="rbCodigoCentral" type="radio" name="rbTipoDocumento" 
			onclick="$('#txtCodigoCentral').css({'display':'block'}); $('#cboTipoDoc').css({'display':'none'}); $('#txtBuscar').css({'display':'none'});$('#txtBuscar').val('');"/></td>
			<td><p> Código Central: &nbsp;</p></td>
<!-- 			<td style="width: 10px"></td> -->
			<td>
				<input id="txtCodigoCentral" name="txtCodigoCentral" class="ui-text"
				onkeypress="return ingresarSoloNumero(event);"
				onkeyup="return busquedaRapida(event, 'btnBuscar')"/>
			</td>
			<td style="width: 25px"></td>
			<td><input id="rbTipoDocumento" type="radio" name="rbTipoDocumento" 
			onclick="$('#txtCodigoCentral').css({'display':'none'}); $('#cboTipoDoc').css({'display':'block'}); $('#txtBuscar').css({'display':'block'});$('#txtCodigoCentral').val('');"/></td>
			<td><p> Tipo de Documento: &nbsp;</p></td>
<!-- 			<td style="width: 10px"></td> -->
			<td>
				<select id="cboTipoDoc" name="cboTipoDoc" class="sel">
					<option>DNI</option>
					<option>RUC</option>
				</select>
			</td>
			<td style="width: 10px"></td>
			<td>
				<input id="txtBuscar" name="txtBuscar" class="ui-text" placeholder="Buscar" 
				onkeypress="return ingresarSoloNumero(event);"
				onkeyup="return busquedaRapida(event, 'btnBuscar')"/>
			</td>
			<td style="width: 10px"></td>
			<td>
				<button id="btnBuscar" style="display:none;">Buscar</button>
				<a href="javascript:buscarCliente()"><img data-title="Buscar Cliente" class="menuItemBarImg" src="<%=request.getContextPath()%>/public/img/buscar.png" align="top"></a>
			</td>
		</tr>
	</table>
</div>

<br>

<div id="tabs" class="hide">
	<ul>
		<li><a href="#panelResultado" class="titulo_tabla"> Información del Cliente </a></li>
		<li><a href="#panelEstadoFinanciero" class="titulo_tabla, hide" id="hrEstadoFinanciero"> Estados Financieros </a></li>
	</ul>

  <div id="panelResultado" class="hide">
	  <table style="width:100%">
	  	<tr>
	  		<td style="width:50%">
	  			<a href="javascript:void(0)" id="hrNuevoEEFF"><img data-title="Nuevo Estado Financiero" class="menuItemBarImg" src="<%=request.getContextPath()%>/public/img/nuevo.png" align="top"></a>&nbsp;
	  			<a href="javascript:void(0)" id="hrModificarEEFF"><img data-title="Modificar Estado Financiero" class="menuItemBarImg" src="<%=request.getContextPath()%>/public/img/modificar.png" align="top"></a>&nbsp;
	  			<a href="javascript:void(0)" id="hrEliminarEEFF"><img data-title="Eliminar Estado(s) Financiero(s)" class="menuItemBarImg" src="<%=request.getContextPath()%>/public/img/eliminar.png" align="top"></a>&nbsp;			
	  		</td>
	  		<td align="right" colspan="2" style="width:50%">
	  			<button id="btnExportaPDF">Exportar PDF</button>
	  			<button id="btnExportaExcel">Exportar Excel</button>
	  		</td>
	  	</tr>
	  </table>
<%-- 	  <a href="javascript:void(0)" id="hrExportaExcel"><img data-title="Exportar a Excel" class="menuItemBarImg" src="<%=request.getContextPath()%>/public/img/excel.png" align="top"></a>&nbsp; --%>
<%-- 	  <a href="javascript:void(0)" id="hrExportaPdf"><img data-title="Exportar a PDF" class="menuItemBarImg" src="<%=request.getContextPath()%>/public/img/pdf.png" align="top"></a>&nbsp; --%>
	  <br><br>
	<div>
		<table>
			<tr class="tr_cabecera">
				<td colspan="11">
					<div class="cabecera_tabla">
						<span class="titulo_tabla"> Datos del Cliente </span>
					</div>
				</td>
			</tr>
			<tr>
				<td class="etiquetagris">Cliente</td>
				<td style="width: 250px;"> <input id="txtCliente" class="ui-text" readonly="readonly"/></td>
				<td style="width: 10px;"/>
				<td class="etiquetagris">Código Central</td>
				<td><input class="ui-text" id="txtCodCentral" readonly="readonly"/></td>
				<td style="width: 10px;"/>
				<td class="etiquetagris">RUC</td>
				<td><input class="ui-text" id="txtNroDocumento" readonly="readonly"/></td>
				<td style="width: 10px;"/>
				<td class="etiquetagris">Tipo Persona</td>
				<td><input class="ui-text" id="txtTipoPersona" readonly="readonly"/></td>
			</tr>
			<tr>
				<td class="etiquetagris">CIUU</td>
				<td style="width:35%">
					<input style="width: 15%" class="ui-text" id="txtCodCIUU" readonly="readonly"/>
					<input class="ui-text" style="width:83%" id="txtDesCIUU" readonly="readonly"/>
				</td>
				<td style="width: 10px"></td>
				<td class="etiquetagris">Grupo Económico</td>
				<td colspan="7"><input class="ui-text" id="txtGrupoEconomico" readonly="readonly"/></td>
			</tr>
		</table>
	</div>
	
	<br>
	
	<div>
		<table id="tblRiesgo" style="width:100%">
			<tr class="tr_cabecera">
				<td colspan="22">
					<div class="cabecera_tabla">
						<span class="titulo_tabla" onclick="$('#tblRiesgo').toggleClass('hide');"> Datos de Riesgo </span>
					</div>
				</td>
			</tr>
			<tr>
				<td class="etiquetagris" colspan="2">Clasificación BBVA &nbsp;</td>
				<td><input style="width: 120px;" class="ui-text" id="txtClasificacionBBVA" readonly="readonly" />&nbsp;</td>
				<td class="etiquetagris">Bureau &nbsp;</td>
				<td><input style="width: 120px;" class="ui-text" id="txtBureau" readonly="readonly"/>&nbsp;</td>
				
				<td class="etiquetagris" colspan="7">Clasificación SBS: </td>
				<td class="etiquetagris">NOR</td>
				<td><input style="width: 40px;text-align:right;" class="ui-text" id="txtNor" readonly="readonly" /></td>

				<td class="etiquetagris">CPP</td>
				<td><input style="width: 40px;text-align:right;" class="ui-text" id="txtCpp" readonly="readonly" /></td>

				<td class="etiquetagris">DEF</td>
				<td><input style="width: 40px;text-align:right;" class="ui-text" id="txtDef" readonly="readonly" /></td>

				<td class="etiquetagris">DUD</td>
				<td><input style="width: 40px;text-align:right;" class="ui-text" id="txtDud"  readonly="readonly"/></td>

				<td class="etiquetagris">PER</td>
				<td><input style="width: 40px;text-align:right;" class="ui-text" id="txtPer" readonly="readonly" /></td>
			</tr>
			<tr>
				<td class="etiquetagris" colspan="5">
					<label class="etiquetagris"> Riesgo Total BBVA </label>&nbsp;
					<input style="width: 110px;text-align:right;" class="ui-text" id="txtRiesgoTotalBBVA" readonly="readonly"/>&nbsp;
					<label> Unidades </label>	
				</td>
				<td class="etiquetagris" colspan="17">
					<label class="etiquetagris">Riesgo Total SBS</label>&nbsp;
					<input style="width: 110px;text-align:right" class="ui-text" id="txtRiesgoTotalSBS" readonly="readonly" />&nbsp;
					<label> Unidades </label>	
				</td>
				
			</tr>
		</table>
	</div>

	<br>
	
	<input type="hidden" id="idsEstadosFinancieros">
	
	<div>
		<table id="tblEstadosFinancieros" style="width:100%">
			<tr class="tr_cabecera">
				<td colspan="22">
					<div class="cabecera_tabla">
						<span class="titulo_tabla"> Estados Financieros </span>
						<a href="javascript:consultarEEFF()" class="ref">
							<img data-title="Ver Detalle" class="menuItemBarImg" src="<%=request.getContextPath()%>/public/img/ver.png" align="middle">  
<%-- 							<span class="ref"> Ver Detalle </span> --%>
						</a>
					</div>
				</td>
			</tr>
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td colspan="22">
					<div id="pgEEFF"></div>
				</td>
			</tr>
		</table>
	</div>
  </div>
  
  <div id="panelEstadoFinanciero" class="hide">
  	<table style="width:100%">
	  	<tr>
	  		<td style="width:50%">
	  			<a href="javascript:void(0)" id="hrNuevoEEFF1"><img data-title="Nuevo Estado Financiero" class="menuItemBarImg" src="<%=request.getContextPath()%>/public/img/nuevo.png" align="top"></a>&nbsp;
	  			<a href="javascript:void(0)" id="hrModificarEEFF1"><img data-title="Modificar Estado Financiero" class="menuItemBarImg" src="<%=request.getContextPath()%>/public/img/modificar.png" align="top"></a>&nbsp;
	  			<a href="javascript:void(0)" id="hrEliminarEEFF1"><img data-title="Eliminar Estado(s) Financiero(s)" class="menuItemBarImg" src="<%=request.getContextPath()%>/public/img/eliminar.png" align="top"></a>&nbsp;			
	  		</td>
	  		<td align="right" colspan="2" style="width:50%">
	  			<button id="btnExportaPDF1">Exportar PDF</button>
	  			<button id="btnExportaExcel1">Exportar Excel</button>
	  		</td>
	  	</tr>
	</table>
<%-- 	<a href="javascript:void(0)" id="hrExportaExcel"><img data-title="Exportar a Excel" class="menuItemBarImg" src="<%=request.getContextPath()%>/public/img/excel.png" align="top"></a>&nbsp; --%>
<%-- 	<a href="javascript:void(0)" id="hrExportaPdf"><img data-title="Exportar a PDF" class="menuItemBarImg" src="<%=request.getContextPath()%>/public/img/pdf.png" align="top"></a>&nbsp; --%>
	<br><br>
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
		<br>
 		<div id="tblEEFF" style="width: 100%; height: 350px; overflow: auto; margin: auto;"></div>
 	</div>
  </div>
</div>