<%@taglib prefix="s" uri="/struts-tags"%>
<style>
	.negative {
		color: #f00;
	}
</style>
<script type="text/javascript">
function firstRowRenderer(instance, td, row, col, prop, value, cellProperties) {
	Handsontable.renderers.TextRenderer.apply(this, arguments);
	td.style.fontWeight = 'bold';
	td.style.color = 'green';
	td.style.background = '#CEC';
}

function negativeValueRenderer(instance, td, row, col, prop, value, cellProperties) {
	Handsontable.renderers.TextRenderer.apply(this, arguments);
	if (parseFloat(value) < 0) {   // if row contains negative number
		td.className = 'negative'; // add class "negative"
	}

	if (!value || value === '') {
		td.style.background = '#EEE';
	} else {
		td.style.background = '';
	}
}

	var data = [
/* {id: 0, name: "ITEM", period1: "2007-12", period2: "2008-12", period3: "2009-12", period4: "2017-12"},*/
{id: 1, name: "BALANCE GENERAL", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 2, name: "ACTIVO", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 3, name: "Caja y bancos", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 4, name: "Valores Negociables", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 5, name: "Depósitos en garantía ", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 6, name: "Clientes", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 7, name: "  - Provisión cob. dudosa", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 8, name: "Cuentas por cobrar soc. relacionadas", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 9, name: "Cuentas por cobrar diversas", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 10, name: "Existencias", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 11, name: "   mercaderías/productos terminados", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 12, name: "   subproductos, desechos y desperdicios", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 13, name: "   productos en proceso", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 14, name: "   materia prima, envases y suministros", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 15, name: "   existencias por recibir", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 16, name: "   - prov. por desvalorización de existencias", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 17, name: "   otras existencias", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 18, name: "Otros activos corrientes", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 19, name: "Cargas diferidas", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 20, name: "Activo Corriente", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 21, name: "Inversiones en relacionadas", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 22, name: "Otras inversiones", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 23, name: "Cuentas por cobrar soc. relacionadas", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 24, name: "Otras cuentas por cobrar", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 25, name: "Inmueble, maquinaria y equipo (neto)", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 26, name: "     terrenos", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 27, name: "     edificios y otras construcciones", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 28, name: "     maquinaria y equipo", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 29, name: "     muebles y enseres", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 30, name: "     unidades de transporte", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 31, name: "     equipos diversos", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 32, name: "     - depreciación acumulada", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 33, name: "    trabajos en curso", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 34, name: "    otros activos fijos", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 35, name: "Intangibles", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 36, name: "Otros Activos", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 37, name: "Cargas diferidas", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 38, name: "Activo No Corriente", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 39, name: "TOTAL  ACTIVOS", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 40, name: "PASIVOS", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 41, name: "Bancos deuda comercial", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 42, name: "Bancos deuda financiera CP", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 43, name: "Otra deuda financiera CP", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 44, name: "Parte corriente de deuda LP", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 45, name: "Tributos por pagar", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 46, name: "Remuneraciones y part por pagar", period1: -1, period2: 0, period3: 0, period4: 0},
{id: 47, name: "Proveedores", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 48, name: "Cuentas por pagar soc. relacionadas", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 49, name: "Cuentas por pagar diversas", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 50, name: "Otros pasivos corrientes", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 51, name: "Provisiones diversas", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 52, name: "Ganancias diferidas", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 53, name: "Pasivo Corriente", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 54, name: "Bancos deuda financiera LP", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 55, name: "Otra deuda financiera LP", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 56, name: "Cuentas por pagar soc. relacionadas", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 57, name: "Otros pasivos No Corrientes", period1: 0, period2: -2, period3: 0, period4: 0},
{id: 58, name: "Provisiones diversas", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 59, name: "Ganancias diferidas", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 60, name: "Imp y participaciones diferidas", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 61, name: "Pasivo No Corriente", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 62, name: "Interés Minoritario", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 63, name: "TOTAL PASIVO", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 64, name: "Capital ", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 65, name: "Capital Adicional", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 66, name: "Excedente de revaluación", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 67, name: "Reservas", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 68, name: "Resultados Acumulados", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 69, name: "Resultados del ejercicio", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 70, name: "Otros", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 71, name: "Patrimonio", period1: 0, period2: 0, period3: 0, period4: 0},
{id: 72, name: "TOTAL PASIVO Y PATRIMONIO", period1: 0, period2: 0, period3: 0, period4: 0}
                     ];
	
	$(document).ready(function () {
		buscarEstadosFinancieros();
	});
	
	function buscarEstadosFinancieros(){
		$.ajax({
			type : "post",
			url : obtenerContexto("estadofinanciero/buscarEstadosFinancieros.do"),
			success : function(data) {
				mostrarDatosCliente(data);
				mostrarDatosEjercicio(data);
			}
		});
	};
	
	function mostrarDatosCliente(data){
		if(data.cliente==null){
			openJqInfo({content: "Cliente No Encontrado"});
		}else{
			$("#label").val(data.cliente.nombreCliente);
			$("#txtCliente").val(data.cliente.nombreCliente);
			$("#txtCodCentral").val(data.cliente.codigoCentral);
			$("#txtNroDocumento").val(data.cliente.nroDocumento);
			$("#txtTipoPersona").val(data.cliente.tipoPersona);
			$("#txtCodCIUU").val(data.cliente.codigoCIUU);
			$("#txtDesCIUU").val(data.cliente.descripcionCIUU);
			$("#txtGrupoEconomico").val(data.cliente.grupoEconomico);
			
			//DEUDA
			$("#txtClasificacionBBVA").val(data.cliente.deuda.clasificacionBBVA);
			$("#txtBureau").val(data.cliente.deuda.bureau);
			$("#txtClasificacionSBS").val(data.cliente.deuda.clasificacionSBS);
			$("#txtNor").val(data.cliente.deuda.nOR);
			$("#txtCpp").val(data.cliente.deuda.cPP);
			$("#txtDef").val(data.cliente.deuda.dEF);
			$("#txtDud").val(data.cliente.deuda.dUD);
			
			$("#txtPer").val(data.cliente.deuda.pER);
			$("#txtRiesgoTotalBBVA").val(data.cliente.deuda.riesgoTotalBBVA);
			$("#txtRiesgoTotalSBS").val(data.cliente.deuda.riesgoTotalSBS);
			
			$('#panelResultado').removeClass('hide');
		}
	};
	
	function mostrarDatosEjercicio(datos){
		var isChecked;
		var columnas = obtenerColumnas(datos.periodos);
		Handsontable.renderers.registerRenderer('negativeValueRenderer', negativeValueRenderer);
		container = $("#tblEEFF");
		container.handsontable({
		    data: datos.itemsEstadoFinanciero,
		    minSpareRows: 0, // Adiciona Filas
		    autoWrapCol : true, // Cambio de columna al llegar al final de la fila
		    columns: columnas,
// 		    columns: data.estadosFinancieros,
		    fixedColumnsLeft: 1, // Columnas Fijas
		    // fixedRowsTop: 1, // Columnas Fijas
		    stretchH: 'all', // Auto ajuste
		    contextMenu: false, // Menu contextual 
		    cells: function (row, col, prop) {
		        var cellProperties = {};
		        if (col < 1 || container.handsontable('getData')[row][col] === 'readOnly') { //  row === 0 ||
		          cellProperties.readOnly = true; //make cell read-only if it is first row or the text reads 'readOnly'
		        }
		        /*if (row === 0) {
		          cellProperties.renderer = firstRowRenderer; //uses function directly
		        }*/
		        else {
		          cellProperties.renderer = "negativeValueRenderer"; //uses lookup map
		        }
		        
		        //BLOQUEAMOS LAS CELDAS QUE NO PERTENECEN A NINGUN PERIODO
		        if(col>obtenerPeriodosConsultados(datos.periodos)){
		        	cellProperties.readOnly = true;
		        }
		        return cellProperties;
		      }, // Evento Celda
		  colHeaders: function (col) {
			    switch (col) {
			      /*case 0:
			        return "<b></b>";
			        */
			      case 0:
				        return "<b>Balance General</b>";
			      default:
			        /*var txt = "200" + col + " <input type='checkbox' class='checker' ";
			        txt += isChecked ? 'checked="checked"' : '';
			        txt += ">";
			        return txt;*/
			        return obtenerCabecera(col, datos.periodos);
			    }
		  } // Cabecera
	});
		
		container.on('mousedown', 'input.checker', function (event) {
			  event.stopPropagation();
			});

			container.on('mouseup', 'input.checker', function (event) {
			  isChecked = !$(this).is(':checked');
			  // $container.handsontable('render');
			});
	};
	
	function obtenerColumnas(periodos){
		var period = periodos.split(',');
		var cantidadPeriodos = period.length - 1; 
		var dato = new Array();
		dato[0] = {data: "cuentaContable.descripcionCuenta", type: 'text'};
	      
		for(var i=1; i<=cantidadPeriodos; i++){
			//dato[i] = {data: "periodo"+i, type: 'numeric'};
			dato.push({data: "periodo"+i, type: 'numeric'});
		}
		return dato;
	}
	
	function obtenerCabecera(col, periodos){
		var period = periodos.split(',');
		var cantidadPeriodos = period.length - 1; //Le quitamos 1 
		var cadenaHTML = "";
		
		if(col <= cantidadPeriodos){
			cadenaHTML = period[col-1] + " <input id='" + period[col-1] + "' type='checkbox' class='checker' >";
		}else{
			cadenaHTML = "<b></b>";
		}
		return cadenaHTML;
	};
	
	function obtenerPeriodosConsultados(periodos){
		var period = periodos.split(',');
		var cantidadPeriodos = period.length - 1; 
		return cantidadPeriodos;
	}
	
</script>

<div id="panelResultado">
	<div>
<%-- 		<span class="etiquetagris">Cliente: <label id="cliente">Julio Medina</label></span>  --%>
		<a href="#" onclick="$('#sss').toggleClass('hide');$('#sssss').toggleClass('hide');">Ver Detalle</a>
		<!--  a href="javascript: ">Ver Detalle Riesgo</a -->
	</div>
	<br>
	<div id="sss">
<!-- 		<b>Datos del Cliente</b> -->
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
		<br>
	</div>
	
	<div id="sssss">
<!-- 		<b onclick="$('#tblRiesgo').toggleClass('hide');">Datos de Riesgo</b> -->
		<table id="tblRiesgo">
			<tr class="tr_cabecera">
				<td colspan="23">
					<div class="cabecera_tabla">
						<span class="titulo_tabla"> Datos de Riesgo </span>
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
		<br>
	</div>
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
 		<div id="tblEEFF" style="width: 900px; height: 350px; overflow: auto; margin: auto;"></div>
 	</div>
</div>