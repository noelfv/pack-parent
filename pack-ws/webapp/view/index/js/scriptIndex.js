function cargarGridEEFF(data){
var options = {
		data: data.estadosFinancieros,
        width: 850,
        height: 190,
        colNames: ['Meses',
            'Desde',
            'Hasta',
            'Estado Asociado',
            'Ajuste',
            'Certificación',
            'Situación',
            'Origen'
        ],
        colModel: [{name: 'meses', index:'meses', width: 50, align:'center', editable:true},
            {name: 'anioInicio', index:'anioInicio', width: 90, align:'center', editable:true},
            {name: 'anioFin', index:'anioFin', width: 90, align:'center'},
            {name: 'estudioAsociado', index:'estudioAsociado', width: 420, align:'center', editable:true},
            {name: 'ajuste', index:'ajuste', width: 70, align: 'center', editable:true},
            {name: 'certificacion', index:'certificacion', width: 95, align: 'center', editable:true},
            {name: 'situacion', index:'situacion', width: 70, align: 'center', editable:true},
            {name: 'origen', index:'origen', width: 70, align: 'center', editable:true},
        ],
        multiselect : true
        // , caption: "Estados Financieros"
    };

	return options;
};
	
$(function(){
	/*$("button").button().on("click", function(){
		console.log("Hola N");
		
	});*/
	
	$("#cboTipoDoc").css({"display":"none"});
	$("#txtBuscar").css({"display":"none"});
	$("#txtCodigoCentral").css({"display":"none"});
	$("#btnBuscar").click(function(){buscarCliente();});
	
	$("#hrNuevoEEFF").click(function(){ingresaEEFF();});
	$("#hrNuevoEEFF1").click(function(){ingresaEEFF();});
	
	$("#hrModificarEEFF").click(function(){modificarEEFF();});
	$("#hrModificarEEFF1").click(function(){modificarEEFF1();});
	
	$("#hrEliminarEEFF").click(function(){eliminarEEFF();});
	$("#hrEliminarEEFF1").click(function(){eliminarEEFF1();});
	
//	$("#hrExportaExcel").click(function(){exportaExcel();});
//	$("#hrExportaPdf").click(function(){exportaPDF();});
//	configurarGrid("pgEEFF", options);
	$("#tabs").tabs();
	

	$("#btnExportaPDF").button({icons: {primary: 'ui-icon-pdf'}}).bind('click', exportaPDF);
	$("#btnExportaExcel").button({icons: {primary: 'ui-icon-xls'}}).bind('click', exportaExcel);
	$("#btnExportaPDF1").button({icons: {primary: 'ui-icon-pdf'}}).bind('click', exportaPDF);
	$("#btnExportaExcel1").button({icons: {primary: 'ui-icon-xls'}}).bind('click', exportaExcel);
	
});

function exportaExcel(){
	document.location.href = obtenerContexto("estadofinanciero/exportar/xls.do");
};

function exportaPDF(){
	document.location.href = obtenerContexto("estadofinanciero/exportar/pdf.do");
};

function buscarCliente(){
	var codCentral = $("#txtCodigoCentral").val();
	var nroDocumento = $("#txtBuscar").val();
	
	if(codCentral=="" & nroDocumento=="" ){
		openJqError({content: "Debe ingresar un criterio de búsqueda"});
	}else{
		
		var datos = "codigoCentral="+$("#txtCodigoCentral").val() + "&tipoDocumento=" 
		+ $("#cboTipoDoc").val() + "&nroDocumento=" + $("#txtBuscar").val();

		$.ajax({
			type : "post",
			data:datos, 
			url : obtenerContexto("estadofinanciero/obtenerCliente.do"),
			success : function(data) {
				mostrarDatosCliente(data);
				buscarEstadosFinancieros(data.cliente.codigoCentral);
				$("#hrEstadoFinanciero").addClass("hide");
				$("#panelEstadoFinanciero").addClass("hide");
			}
		});
		
		//obtenerContexto("closeSessionAction.do")
		/*$.post(obtenerContexto("estadofinanciero/obtenerCliente.do"), function( data ) {
		alert("success: " + data);
		});*/
	}
};

function buscarEstadosFinancieros(codigoCentral){
	var datos = "codigoCentral="+ codigoCentral;
	$.ajax({
		type : "post",
		data:datos, 
		url : obtenerContexto("estadofinanciero/listar.do"),
		success : function(data) {
			configurarGrid("pgEEFF", cargarGridEEFF(data));
		}
	});
};

function mostrarDatosCliente(data){
	if(data.cliente==null){
		openJqInfo({content: "Cliente No Encontrado"});
	}else{
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
		$('#tabs').removeClass('hide');
	}
};

function seleccionar(){
	var idsEEFFHidden = $("#idsEstadosFinancieros").val();
	var valor = "";
	var val = "";
	var indicesGrid = $("#tbl_pgEEFF").jqGrid('getGridParam','selarrrow');
	$.each(indicesGrid, function(index, value){
		val = $("#tbl_pgEEFF").jqGrid('getRowData',value);
		valor = valor + val.anioFin + ",";
	});
	$("#idsEstadosFinancieros").val(valor);
	
	/*var s;
	s = $("#tbl_pgEEFF").jqGrid('getGridParam','selarrrow'); //selarrrow
	$.each(s, function(index, value){
		alert(index + ":" + value);
		var ret = $("#tbl_pgEEFF").jqGrid('getRowData',value);
		alert("Hasta: " + ret.anioFin);
	});*/
		
};

function consultarEEFF(){
	//OBTENEMOS LOS ESTADOS FINANCIEROS SELECCIONADOS
	var idsEEFFHidden = $("#idsEstadosFinancieros").val();
	var valor = "";
	var val = "";
	var indicesGrid = $("#tbl_pgEEFF").jqGrid('getGridParam','selarrrow');
	$.each(indicesGrid, function(index, value){
		val = $("#tbl_pgEEFF").jqGrid('getRowData',value);
		valor = valor + val.anioFin + ",";
	});
	$("#idsEstadosFinancieros").val(valor);
	
	var cliente = $("#txtCodCentral").val();
	var datos = "codigoCentral="+cliente+"&periodos="+$("#idsEstadosFinancieros").val();

	$.ajax({
		type : "post",
		data:datos, 
		url : obtenerContexto("estadofinanciero/obtenerEstadosFinancieros.do"),
		success : function(data) {
			if(data.tipoResultado!=null && data.tipoResultado=="ERROR_SISTEMA"){
				openJqError({content: data.mensaje});
			}else{
//				document.location.href = obtenerContexto("estadofinanciero/irEstadosFinancieros.do");
				buscarEstadosFinancierosDetalle();
			}
		}
	});
};

function buscarEstadosFinancierosDetalle(){
	$.ajax({
		type : "post",
		url : obtenerContexto("estadofinanciero/buscarEstadosFinancieros.do"),
		success : function(data) {
			mostrarDatosEjercicio(data);
			$("#panelEstadoFinanciero").removeClass('hide');
			$("#hrEstadoFinanciero").removeClass('hide');
			
			//SELECCIONAMOS EL TAB DE ESTADO FINANCIERO
			$("[href='#panelEstadoFinanciero']").trigger("click");
		},
		error: function (xhr, ajaxOptions, thrownError) {
			 /*alert(xhr.status);
		     alert(thrownError);*/
		}
	});
};

function mostrarDatosEjercicio(datos){
	var isChecked;
	var columnas = obtenerColumnas(datos.periodos);
	var ancho = obtenerAnchoColumnas(datos.periodos);
	
	Handsontable.renderers.registerRenderer('negativeValueRenderer', negativeValueRenderer);
	container = $("#tblEEFF");
	container.handsontable({
	    data: datos.itemsEstadoFinanciero,
	    minSpareRows: 0, // Adiciona Filas
	    autoWrapCol : true, // Cambio de columna al llegar al final de la fila
	    colWidths: ancho,
	    columns: columnas,
//		    columns: data.estadosFinancieros,
//	    fixedColumnsLeft: 1, // Columnas Fijas
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
	        if(col<=obtenerPeriodosConsultados(datos.periodos)){
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

function obtenerAnchoColumnas(periodos){
	if(periodos!=null){
		var period = periodos.split(',');
		var cantidadPeriodos = period.length - 1; 
		var dato = new Array();
		dato.push(90);
	    for(var i=1; i<=cantidadPeriodos; i++){
			//dato[i] = {data: "periodo"+i, type: 'numeric'};
			dato.push(60);
		}
		return dato;	
	}
};

function obtenerColumnas(periodos){
	if(periodos!=null){
		var period = periodos.split(',');
		var cantidadPeriodos = period.length - 1; 
		var dato = new Array();
		dato[0] = {data: "cuentaContable.descripcionCuenta", type: 'text'};
	      
		for(var i=1; i<=cantidadPeriodos; i++){
			//dato[i] = {data: "periodo"+i, type: 'numeric'};
			dato.push({data: "periodo"+i, type: "numeric", format: "000,000.00"});
		}
		return dato;	
	}
};

function obtenerCabecera(col, periodos){
	if(periodos!=null){
		var period = periodos.split(',');
		var cantidadPeriodos = period.length - 1; //Le quitamos 1 
		var cadenaHTML = "";
		
		if(col <= cantidadPeriodos){
//			cadenaHTML = period[col-1] + " <input id='" + period[col-1] + "' type='checkbox' class='checker' >";
			cadenaHTML = period[col-1] + " <input id='" + period[col-1] + "' value='" + period[col-1] + "' type='checkbox' class='checker' >";
		}else{
			cadenaHTML = "<b></b>";
		}
		return cadenaHTML;	
	}
};

function obtenerPeriodosConsultados(periodos){
	if(periodos!=null){
		var period = periodos.split(',');
		var cantidadPeriodos = period.length - 1; 
		return cantidadPeriodos;
	}
};

function firstRowRenderer(instance, td, row, col, prop, value, cellProperties) {
	Handsontable.renderers.TextRenderer.apply(this, arguments);
	td.style.fontWeight = 'bold';
	td.style.color = 'green';
	td.style.background = '#CEC';
};

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
};

function ingresaEEFF(){
	document.location.href = obtenerContexto("estadofinanciero/ingresar.do");
};

function modificarEEFF(){
	//OBTENEMOS LOS ESTADOS FINANCIEROS SELECCIONADOS
	var idsEEFFHidden = $("#idsEstadosFinancieros").val();
	var valor = "";
	var val = "";
	var indicesGrid = $("#tbl_pgEEFF").jqGrid('getGridParam','selarrrow');
	var contador = 0;
	$.each(indicesGrid, function(index, value){
		val = $("#tbl_pgEEFF").jqGrid('getRowData',value);
		valor = valor + val.anioFin + ",";
		contador = contador + 1;
	});
	
	if(contador==1){
		$("#idsEstadosFinancieros").val(valor);
		var cliente = $("#txtCodCentral").val();
		var datos = "codigoCentral="+cliente+"&periodos="+$("#idsEstadosFinancieros").val();
		
		$.ajax({
			type : "post",
			data:datos, 
			url : obtenerContexto("estadofinanciero/validarModificacion.do"),
			success : function(data) {
				if(data.tipoResultado == "ERROR_SISTEMA"){
					openJqError({content: data.mensaje});
				}else{
					document.location.href = obtenerContexto("estadofinanciero/modificar.do");
				}
			}
		});
	}else if(contador==0){
		openJqError({content: "Por favor seleccione un registro para su modificación"});
	}else{
		openJqError({content: "Solo se puede modificar un estado financiero"});
	}	
};

function modificarEEFF1(){
	var contador = 0;
	var periodos = "";
	$('input[class=checker]').each(function () {
	       var sThisVal = (this.checked ? $(this).val() : "");
		   if(this.checked){
			   contador = contador + 1;
			   periodos = periodos + $(this).val() + ",";
		   }
	});
	
	if(contador==0){
		openJqError({content: "Por favor seleccione un registro para su modificación"});
	}else if(contador>1){
		openJqError({content: "Solo se puede modificar un estado financiero"});
	}else{
		$("#idsEstadosFinancieros").val(periodos);
		var cliente = $("#txtCodCentral").val();
		var datos = "codigoCentral="+cliente+"&periodos="+$("#idsEstadosFinancieros").val();
		$.ajax({
			type : "post",
			data:datos, 
			url : obtenerContexto("estadofinanciero/validarModificacion.do"),
			success : function(data) {
				if(data.tipoResultado == "ERROR_SISTEMA"){
					openJqError({content: data.mensaje});
				}else{
					document.location.href = obtenerContexto("estadofinanciero/modificar.do");
				}
			}
		});
	}
};

function eliminarEEFF(){
	//OBTENEMOS LOS ESTADOS FINANCIEROS SELECCIONADOS
	var idsEEFFHidden = $("#idsEstadosFinancieros").val();
	var valor = "";
	var val = "";
	var indicesGrid = $("#tbl_pgEEFF").jqGrid('getGridParam','selarrrow');
	var contador = 0;
	$.each(indicesGrid, function(index, value){
		val = $("#tbl_pgEEFF").jqGrid('getRowData',value);
		valor = valor + val.anioFin + ",";
		contador = contador + 1;
	});
	
	if(contador==0){
		openJqError({content: "Por favor seleccione un registro para eliminar"});
	}else if(contador>1){
		openJqError({content: "Solo se puede eliminar un estado financiero"});
	}else{
		valor = valor.replace(",", "");
		openJqConfirm(opcionesDialog(valor));
	}
};

function eliminarEEFF1(){
	//OBTENEMOS LOS ESTADOS FINANCIEROS SELECCIONADOS
	var contador = 0;
	var periodos = "";
	
	$('input[class=checker]').each(function () {
	       var sThisVal = (this.checked ? $(this).val() : "");
		   if(this.checked){
			   contador = contador + 1;
			   periodos = periodos + $(this).val() + ",";
		   }
	});
	
	if(contador==0){
		openJqError({content: "Por favor seleccione un registro para eliminar"});
	}else if(contador>1){
		openJqError({content: "Solo se puede eliminar un estado financiero"});
	}else{
		periodos = periodos.replace(",", "");
		openJqConfirm(opcionesDialog1(periodos));
		
		
	}
};

opcionesDialog = function(valor){
	var options = {
	        content:'¿Seguro de Eliminar Estado Financiero con periodo '+ valor + '?',
	        buttons: {
				  "Sí": function(){eliminaEEFF(valor);closeDialog($(this).attr("id"));}
				, "No": function(){closeDialog($(this).attr("id"));}
			}
	    };
	return options;
};

function eliminaEEFF(valor){
	var cliente = $("#txtCodCentral").val();
	var datos = "codigoCentral="+cliente+"&periodo="+valor;
	$.ajax({
		type : "post",
		data:datos, 
		url : obtenerContexto("estadofinanciero/eliminar.do"),
		success : function(data) {
			if(data.tipoResultado == "ERROR_SISTEMA"){
				openJqError({content: data.mensaje});
			}else{
				configurarGrid("pgEEFF", cargarGridEEFF(data));
			}
		}
	});
};

opcionesDialog1 = function(valor){
	var options = {
	        content:'¿Seguro de Eliminar Estado Financiero con periodo '+ valor + '?',
	        buttons: {
				  "Sí": function(){eliminaEEFF1(valor);closeDialog($(this).attr("id"));}
				, "No": function(){closeDialog($(this).attr("id"));}
			}
	    };
	return options;
};

function eliminaEEFF1(valor){
	var cliente = $("#txtCodCentral").val();
	var datos = "codigoCentral="+cliente+"&periodo="+valor;
	$.ajax({
		type : "post",
		data:datos, 
		url : obtenerContexto("estadofinanciero/eliminar.do"),
		success : function(data) {
			if(data.tipoResultado == "ERROR_SISTEMA"){
				openJqError({content: data.mensaje});
			}else{
				$("#panelEstadoFinanciero").removeClass('hide').addClass('hide');
				$("#hrEstadoFinanciero").removeClass('hide').addClass('hide');
				
				//SELECCIONAMOS EL TAB DE ESTADO FINANCIERO
				$("[href='#panelResultado']").trigger("click");
				configurarGrid("pgEEFF", cargarGridEEFF(data));
			}
		}
	});
};