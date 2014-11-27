<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/view/perfiles/js/scriptPerfiles.js"></script>

<input type="hidden" id="hidIdPerfil" name="perfil.idPerfil"/>
<input type="hidden" id="hidIdPerfilAccion" name="perfil.idPerfil"/>

<table style="width:100%">
	<tr>
		<td style="width:40%" valign="top">
			<table style="width: 100%">
				<tr>
					<th style="padding-bottom: 1em" colspan="2">
						<div class="cabecera_tabla">
							<span class="titulo_tabla"> Datos del Perfil </span>
						</div>
					</th>
				</tr>
				<tr>
					<td class="etiquetagris" style="width:90px">C&oacute;digo </td>
					<td><input id="txtCodigoPerfil" name="perfil.codigoPerfil"
						type="text" style="width: 250px;" maxlength="100" class="ui-text" />
					</td>
				</tr>
				<tr><td colspan="2">&nbsp;</td></tr>
				<tr>
					<td class="etiquetagris">Nombre </td>
					<td><input id="txtNombrePerfil" name="perfil.nombrePerfil"
						type="text" style="width: 250px;" maxlength="100" class="ui-text" />
					</td>
				</tr>
				<tr><td colspan="2">&nbsp;</td></tr>
				<tr>
					<td class="etiquetagris">Estado </td>
					<td><s:select
							list="perfilModel.listaEstados" listKey="estado"
							listValue="descripcion" cssStyle="width: 100px;"
							id="cmbPerfilEstado" name="perfilModel.idEstado" cssClass="sel"/></td>
				</tr>
				<tr><td colspan="2">&nbsp;</td></tr>
				<tr>
					<td align="right" colspan="2">
						<button id="btnGrabar">Guardar</button>
						<button id="btnCancelar">Cancelar</button>
					</td>
				</tr>
			</table>
		</td>
		<td style="width:10%"></td>
		<td style="width:50%" valign="top">
			<table style="width: 100%">
				<tr>
					<th style="padding-bottom: 1em;">
						<div class="cabecera_tabla">
							<span class="titulo_tabla"> Listado de Perfiles </span>
						</div>
					</th>
				</tr>
				<tr>
					<td>
						<div id="pnlPerfiles"></div>
					</td>
				</tr>
			</table>
		</td>
	</tr>

</table>


<div id="dialogAcciones" style="display: none;">
	<table style="width: 100%;">
		
		<tr>
			<td style="width: 70px;" valign="top">Perfil : </td>
			<td style="padding-bottom: 1em"><input readonly="readonly" type="text" class="ui-text" id="txtAccionNombrePerfil" style="width: 100px;" maxlength="100"/></td>
		</tr>		
		<tr>
			<td style="padding-bottom: 1em" colspan="2">
				<div id="pnlAcciones"></div>
			</td>
		</tr>		
		<tr>
			<td align="right" colspan="2">
				<button id="btnGrabarAccion">Grabar</button>
				<button id="btnCancelarAccion">Cancelar</button>
			</td>
		</tr>	
	</table>
</div>