<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/view/configuracion/js/scriptParametro.js"></script>

<input type="hidden" id="hidFiltroTipo"/>
<input type="hidden" id="hidFiltroPadre"/>
<input type="hidden" id="hidFiltroEstado"/>
<input type="hidden" id="hidFiltroNombre"/>

<div>
	<table style="width: 100%">
		<tr class="tr_cabecera">
			<td colspan="7">
				<div class="cabecera_tabla" style="width:100%">
					<span class="titulo_tabla"> Configuración de Parámetros </span>
				</div>
			</td>
		</tr>
		<tr><td colspan="7">&nbsp;</td></tr>
		<tr>
<!-- 			<td>Tipo de p&aacute;rametro : </td> -->
			<td class="etiquetagris" style="width:90px">Tipo de Parámetro </td>
			<td style="width:50px">
				<s:select 
					headerKey="-1"
					headerValue="[Seleccione]"
					list="parametroModel.listaTipos"
					listKey="id"
					listValue="nombre"
					cssClass="sel"
					cssStyle="width: 100px;"
					id="cmbFiltroTipo"
					name="parametroModel.idTipo" />				
			</td>
			<td style="width: 10px;"/>
<!-- 			<td>P&aacute;rametro padre : </td> -->
			<td class="etiquetagris" style="width:80px">Parámetro Padre </td>
			<td style="width:70px">
				<s:select 
					headerKey="-1"
					headerValue="[Seleccione]"
					list="parametroModel.listaPadres"
					listKey="id"
					listValue="nombre"
					cssClass="sel"
					cssStyle="width: 200px;"
					id="cmbFiltroPadre"
					name="parametroModel.idPadre"
					disabled="true" />
			</td>
			<td class="etiquetagris" style="width:30px">Estado </td>
			<td style="width:70px">
				<s:select 
					headerKey="-1"
					headerValue="[Todos]"
					list="parametroModel.listaEstados"
					listKey="estado"
					listValue="descripcion"
					cssClass="sel"
					cssStyle="width: 100px;"
					id="cmbFiltroEstado"
					name="parametroModel.idEstado" />
			</td>
			
<!-- 			<td align="right"> -->
<!-- 							<button id="btnBuscar">Buscar</button> -->
<!-- 							<button id="btnLimpiar">Limpiar</button> -->
<!-- 							<button id="btnNuevo">Nuevo</button> -->
<!-- 							<button id="btnModificar">Modificar</button>		 -->
<!-- 			</td> -->
		</tr>
		<tr><td colspan="7">&nbsp;</td></tr>
		<tr>
			<td colspan="7">
				<table style="width:100%">
					<tr>
						<td class="etiquetagris" style="width:70px">Nombre </td>
						<td>
							<input id="txtFiltroNombre" name="parametroModel.nombre" type="text" style="width: 400px;" maxlength="100" class="ui-text"/>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr><td colspan="7">&nbsp;</td></tr>
		<tr>
			<td colspan="7">
				<table style="width:100%">
					<tr>
						<td align="right">
							<button id="btnBuscar">Buscar</button> &nbsp;
							<button id="btnLimpiar">Limpiar</button>&nbsp;
							<button id="btnNuevo">Nuevo</button>&nbsp;
 							<button id="btnModificar">Modificar</button>&nbsp;
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
<br>
<table style="width: 100%">
	<tr>
		<td>
			<div id="pnlParametria"></div>
		</td>
	</tr>
</table>

<div id="dialogParametria" style="display: none;">
	<input type="hidden" id="idParametro" />
	<table style="width: 100%;">
		<tr>
			<td style="width: 140px;" valign="middle" class="etiquetagris">Tipo Par&aacute;metro </td>
			<td>
				<s:select 
					headerKey="-1"
					headerValue="[Seleccione]"
					list="parametroModel.listaTipos"
					listKey="id"
					listValue="nombre"
					cssClass="sel"
					cssStyle="width: 100px;"
					id="cmbEdicionTipo"
					disabled="true"/>
			</td>
		</tr>		
		<tr><td colspan="2" height="5px">&nbsp;</td></tr>
		<tr id="row_padre">
			<td valign="middle" class="etiquetagris">Par&aacute;metro Padre </td>
			<td>
				<s:select 
					headerKey="-1"
					headerValue="[Seleccione]"
					list="parametroModel.listaPadresPermiteHijos"
					listKey="id"
					listValue="nombre"
					cssClass="sel"
					cssStyle="width: 200px;"
					id="cmbEdicionPadre"/>
				<input type="text" id="txtEdicionPadre" class="ui-text ui-text-disabled" style="width: 300px;" disabled="disabled"/> 
			</td>
		</tr>
		<tr><td colspan="2">&nbsp;</td></tr>		
		<tr>
			<td valign="middle" class="etiquetagris">Nombre </td>
			<td><input type="text" class="ui-text" id="txtEdicionNombre" style="width: 350px;" maxlength="100"/></td>
		</tr>
		<tr><td colspan="2">&nbsp;</td></tr>
		<tr>
			<td valign="middle" class="etiquetagris">Descripci&oacute;n </td>
			<td valign="top"><textarea rows="4" cols="60" class="ui-text" id="txtEdicionDescripcion" maxlength="500"></textarea></td>
		</tr>
		<tr><td colspan="2">&nbsp;</td></tr>
		<tr>
			<td valign="middle" class="etiquetagris">Estado </td>
			<td><s:select 
					headerKey="-1"
					headerValue="[Seleccione]"
					list="parametroModel.listaEstados"
					listKey="estado"
					listValue="descripcion"
					cssClass="sel"
					cssStyle="width: 100px;"
					id="cmbEdicionEstado"/>
			</td>
		</tr>
		<tr><td colspan="2">&nbsp;</td></tr>
		<tr class="row_dinamico" id="row_codigo">
				<td valign="top">
					<label id="lblCodigoEti" class="etiquetagris"></label>
					<input type="checkbox" id="chkCodigoHabil" class="habil" style="display: none;"/>					
				</td>
				<td valign="top">
					<input type="text" class="ui-text" id="txtEdicionCodigo" style="width: 100px;" maxlength="20"/>
				</td>
		</tr>
		<tr class="row_dinamico" id="row_entero">
				<td valign="top">
					<label id="lblEnteroEti" class="etiquetagris"></label>
					<input type="checkbox" id="chkEnteroHabil" class="habil" style="display: none;"/>
				</td>
				<td valign="top">
					<input type="text" class="ui-text" id="txtEdicionEntero" style="width: 120px;" maxlength="10"/>
				</td>
		</tr>
		<tr class="row_dinamico" id="row_decimal">
				<td valign="top">
					<label id="lblDecimalEti" class="etiquetagris"></label>
					<input type="checkbox" id="chkDecimalHabil" class="habil" style="display: none;"/>
				</td>
				<td valign="top">
					<input type="text" class="ui-text" id="txtEdicionDecimal" style="width: 120px;" maxlength="18"/>
				</td>
		</tr>
		<tr class="row_dinamico" id="row_texto">
				<td valign="top">
					<label id="lblTextoEti" class="etiquetagris"></label>
					<input type="checkbox" id="chkTextoHabil" class="habil" style="display: none;"/>
				</td>
				<td valign="top"> 
					<textarea rows="4" cols="60" class="ui-text" id="txtEdicionTexto" maxlength="500"></textarea>										
				</td>
		</tr>
		<tr class="row_dinamico" id="row_fecha">
				<td valign="top">
					<label id="lblFechaEti" class="etiquetagris"></label>
					<input type="checkbox" id="chkFechaHabil" class="habil" style="display: none;"/>
				</td>
				<td valign="top">
					<input type="text" class="ui-text ui-text-date" id="txtEdicionFecha" maxlength="10"/>
				</td>
		</tr>
		<tr class="row_dinamico" id="row_hora">
				<td valign="top">
					<label id="lblHoraEti" class="etiquetagris"></label>
					<input type="checkbox" id="chkHoraHabil" class="habil ui-text" style="display: none;"/>
				</td>
				<td valign="top">
					<input type="text" class="ui-text ui-text-time" id="txtEdicionHora" maxlength="5"/>
				</td>
		</tr>
		<tr class="row_dinamico" id="row_boolean">
				<td valign="top">
					<label id="lblBooleanEti" class="etiquetagris"></label>
					<input type="checkbox" id="chkBooleanHabil" class="habil" style="display: none;"/>
				</td>
				<td valign="top">
					<input type="checkbox" id="chkEdicionBoolean"/>
				</td>
		</tr>
		<tr class="row_dinamico" id="row_funcion">
				<td colspan="2" align="center">
					<input type="checkbox" id="chkFuncionHabil" class="habil" style="display: none;"/>					
					<button id="btnFuncion" class="etiqueta">[Nombre Funcion]</button>
				</td>
		</tr>		
		<tr>
			<td align="right" colspan="2">
				<button id="btnGrabar">Grabar</button>
				<button id="btnCancelar">Cancelar</button>
			</td>
		</tr>	
	</table>
</div>
