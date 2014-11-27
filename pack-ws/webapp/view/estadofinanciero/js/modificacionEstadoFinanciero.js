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
	
	if (parseFloat(value) > 0) {   
		Handsontable.Dom.addClass(td, 'htNumeric'); //Seteamos un class numerico
	}

	if (!value || value === '') {
		td.style.background = '#EEE';
	} else {
		td.style.background = '';
	}
};

$(function(){
//	var spinner = $("#spinner").spinner();
//	spinner.spinner("value", 12);
	$("#tabs").tabs();
	modificarEEFF();
	$("#txtOpinionAuditor").css({"resize":"none"});
	$("#hrContinuar").click(function(){continuarRegistro();});
	$("#hrGuardarEEFF").click(function(){guardarEstadoFinanciero();});

});

function modificarEEFF(){
	$.ajax({
		type : "post",
		url : obtenerContexto("estadofinanciero/modificarEstadoFinanciero.do"),
		success : function(data) {
			if(data.tipoResultado == "ERROR_SISTEMA"){
				openJqError({content: data.mensaje});
			}else{
				$("#cmbUnidadesMedida").val(data.unidadMedida);
				$("#cmbEstructuraProductiva").val(data.estructuraProductiva);
				$("#cmbCertificacion").val(data.certificacion);
				$("#txtMesInicio").val(data.estadoFinanciero.mesInicio);
				$("#txtAnioInicio").val(data.estadoFinanciero.anioInicio);
				$("#txtMesFin").val(data.estadoFinanciero.mesFin);
				$("#txtAnioFin").val(data.estadoFinanciero.anioFin);
				$("#spinner").val(data.estadoFinanciero.meses);
				$("#txtCantidadEmpledos").val(data.estadoFinanciero.cantidadEmpleados);
				$("#txtNombreAuditor").val(data.estadoFinanciero.nombreAuditor);
				$("#txtProcedenciaAuditor").val(data.estadoFinanciero.procedencia);
				$("#txtOpinionAuditor").val(data.estadoFinanciero.opinionAuditor);
				$("#idEstadoFinanciero").val(data.estadoFinanciero.idEstadoFinanciero);
				
				if($("#cmbCertificacion").val()==57){
					mostrarAuditoria();
				}
				
				mostrarDatosEjercicio(data);
			}
		}
	});
};

function mostrarAuditoria(){
	var valor = $("#cmbCertificacion").val();
	if(valor=="57"){
		//SI ES AUDITADA
		$('#filaDatosAuditor').removeClass('hide');
		$('#filaOpinionAuditor').removeClass('hide');
	}else{
		$("#filaDatosAuditor").addClass("hide");
		$("#filaOpinionAuditor").addClass("hide");
		$("#txtNombreAuditor").val("");
		$("#txtProcedenciaAuditor").val("");
		$("#txtOpinionAuditor").val("");
	}
};

function continuarRegistro(){
	if(validaCampos()){
		if(validaLogica()){
			//MUESTRA EL TAB DEL LISTADO DE EJERCICIOS
			var datos = $("#formEEFF").serialize();
			$.ajax({
				type : "post",
				data : datos,
				url : obtenerContexto("estadofinanciero/ingresarEjercicios.do"),
				success : function(data) {
					mostrarDatosEjercicio(data);
					//SELECCIONAMOS EL TAB DE ESTADO FINANCIERO
					$("#hrEstadoFinanciero").removeClass('hide');
					$("[href='#panelEstadoFinanciero']").trigger("click");
					$("#panelEstadoFinanciero").removeClass('hide');
				}
			});
		}
	}
	
};

function mostrarDatosEjercicio(datos){
	var isChecked;
	var columnas = obtenerColumnas(datos.periodos);
	var ancho = obtenerAnchoColumnas(datos.periodos);
	
	Handsontable.renderers.registerRenderer('negativeValueRenderer', negativeValueRenderer);
	container = $("#tblEjercicios"); //tblEEFF
	container.handsontable({
	    data: datos.itemsEstadoFinanciero,
	    minSpareRows: 0, // Adiciona Filas
	    autoWrapCol : true, // Cambio de columna al llegar al final de la fila
	    colWidths: ancho,
	    columns: columnas,
//		    columns: data.estadosFinancieros,
//	    fixedColumnsLeft: 1, // Columnas Fijas se
	    // fixedRowsTop: 1, // Columnas Fijas
	    stretchH: 'all', // Auto ajuste
//	    contextMenu: false, // Menu contextual
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
	        
	        return cellProperties;
	    },//Obtener los valores 
	    afterChange: function (changes, source) {
	    	if (source !== 'loadData') {
//	          $("#example1console").text(JSON.stringify(changes));
//	    	  valores.push(JSON.stringify(changes));
	        }
	    },// Evento Celda
	    colHeaders: function (col) {
		    switch (col) {
		      /*case 0:
		        return "<b></b>";
		        */
		      case 0:
//			        return "<b>Balance General</b>";
		    	  return "Balance General";
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
	if(periodos!=null){
		var period = periodos.split(',');
		var cantidadPeriodos = period.length; 
		var dato = new Array();
		dato[0] = {data: "cuentaContable.descripcionCuenta", type: 'text'};
	    for(var i=1; i<=cantidadPeriodos; i++){
			//dato[i] = {data: "periodo"+i, type: 'numeric'};
			dato.push({data: "periodo"+i, type: "numeric", format: "0,0.00", language: "en"});
		}
		return dato;	
	}
};

function obtenerAnchoColumnas(periodos){
	if(periodos!=null){
		var period = periodos.split(',');
		var cantidadPeriodos = period.length - 1; 
		var dato = new Array();
		dato.push(90);
	    for(var i=1; i<=cantidadPeriodos; i++){
			//dato[i] = {data: "periodo"+i, type: 'numeric'};
			dato.push(70);
		}
		return dato;	
	}
};

function obtenerPeriodosConsultados(periodos){
	if(periodos!=null){
		var period = periodos.split(',');
		var cantidadPeriodos = period.length - 1; 
		return cantidadPeriodos;
	}
};

function obtenerCabecera(col, periodos){
	if(periodos!=null){
		var period = periodos.split(',');
		var cantidadPeriodos = period.length; //Le quitamos 1 
		var cadenaHTML = "";
		
		if(col <= cantidadPeriodos){
			cadenaHTML = period[col-1];
		}else{
			cadenaHTML = "<b></b>";
		}
		return cadenaHTML;	
	}
};

function validaLogica(){
	var mensaje = '';
	var mesInicio = $("#txtMesInicio").val();
	var mesFin = $("#txtMesFin").val();
	var anioInicio = $("#txtAnioInicio").val();
	var anioFin = $("#txtAnioFin").val();
	
	//VALIDA QUE PERIODO DE INICIO SEA ENERO
	if(mesInicio!="01"){
		mensaje = mensaje + "Periodo Inicio debe ser Enero" + "<br>";	
	}
	
	//VALIDA QUE AÑO INICIO Y FIN SEAN EL MISMO
	if(anioInicio!=anioFin){
		mensaje = mensaje + "Periodo inicial y final deben ser del mismo año" + "<br>";
	}
	
	//VALIDA QUE PERIODO FIN NO SEA MAYOR A FECHA ACTUAL
	var fechaActual = new Date();
	var mesActual = fechaActual.getMonth();
	var anioActual = fechaActual.getFullYear();
	
	if(anioFin == anioActual && (mesFin-1)>mesActual){
		mensaje = mensaje + "Periodo Fin no debe ser mayor a la fecha actual" + "<br>";
	}else if(anioFin > anioActual){
		mensaje = mensaje + "Periodo Fin no debe ser mayor a la fecha actual" + "<br>";	
	}
	
	if(mensaje!=''){
		openJqError({content: mensaje});
		return false;
	}
	return true;
}

function validaCampos(){
	var mensaje = '';
	
	var mesInicio = $("#txtMesInicio").val();
	var anioInicio = $("#txtAnioInicio").val();
	var mesFin = $("#txtMesFin").val();
	var anioFin = $("#txtAnioFin").val();
	var cantidadEmpleados = $("#txtCantidadEmpledos").val();
	var unidadMedida = $("#cmbUnidadesMedida").val();
	var estructuraProductiva = $("#cmbEstructuraProductiva").val();
	var certificacion = $("#cmbCertificacion").val();
	
	mensaje = validaCampoTexto(mesInicio, mensaje, "Mes Inicio");
	mensaje = validaCampoTexto(anioInicio, mensaje, "Año Inicio");
	mensaje = validaCampoTexto(mesFin, mensaje, "Mes Fin");
	mensaje = validaCampoTexto(anioFin, mensaje, "Año Fin");
	mensaje = validaCampoTexto(cantidadEmpleados, mensaje, "Cantidad de Empleados");
	
	mensaje = validaCampoCombo(unidadMedida, mensaje, "Unidad Medida");
	mensaje = validaCampoCombo(estructuraProductiva, mensaje, "Estructura Productiva");
	mensaje = validaCampoCombo(certificacion, mensaje, "Certificación");
	
	if(certificacion=="57"){
		var nombreAuditor = $("#txtNombreAuditor").val();
		var procedenciaAuditor = $("#txtProcedenciaAuditor").val();
		var opinionAuditor = $("#txtOpinionAuditor").val();
		
		mensaje = validaCampoTexto(nombreAuditor, mensaje, "Nombre del Auditor");
		mensaje = validaCampoTexto(procedenciaAuditor, mensaje, "Procedencia del Auditor");
		mensaje = validaCampoTexto(opinionAuditor, mensaje, "Opinión del Auditor");
	}
	
	if(mensaje!=''){
		openJqError({content: mensaje});
		return false;
	}
	return true;
};

function validaCampoTexto(valor, mensaje, campo){
	if(valor==''){
		mensaje = mensaje + campo + " es obligatorio" + "<br>";
	}
	return mensaje;
};

function validaCampoCombo(valor, mensaje, campo){
	if(valor=='-1'){
		mensaje = mensaje + campo + " es obligatorio" + "<br>";
	}
	return mensaje
};

function calcularMeses(){
	var mesInicio = $("#txtMesInicio").val();
	var anioInicio = $("#txtAnioInicio").val();
	var mesFin = $("#txtMesFin").val();
	var anioFin = $("#txtAnioFin").val();
	var mensaje;
	var cantidadMeses;
	
	if(anioInicio==anioFin) {
		cantidadMeses = (mesFin - mesInicio)+1;
		$("#spinner").val(cantidadMeses);
//		spinner.spinner("value", cantidadMeses);
	}else{
		mensaje = "Periodo inicial y final deben ser del mismo año" + "<br>";
		openJqError({content: mensaje});
	}
};

function guardarEstadoFinanciero(){
	var $container = $("#tblEjercicios");
	var handsontable = $container.data('handsontable');
	var data1 = JSON.stringify(handsontable.getData());
	$("#hdListaEjercicios").val(data1);
	$("#hdCodigoCentral").val($("#txtCodCentral").val());
	var datos = $("#formEEFF").serialize();
	
	/*$.ajax({
		url : "guardarEEFF.do?data=" + data1,
//		data : datos,
		dataType : 'json',
		contentType : 'application/json',
		type : 'POST',
		async : true,
		success : function(res) {
			console.log(res.data.length);
			for(var i=0; i<res.data.length;i++){
//				console.log(" "+res.data[i].name+"-"+res.data[i].id+"-"+res.data[i].active+"-"+res.data[i].date);
			}
		},
		error: function (xhr, ajaxOptions, thrownError) {
			 alert(xhr.status);
		     alert(thrownError);
		}
	});*/
	
	$.ajax({
		type : "post",
		data:datos, 
		url : obtenerContexto("estadofinanciero/guardar.do"),
		success : function(data) {
			if(data.tipoResultado == "ERROR_SISTEMA"){
				openJqError({content: data.mensaje});
			}else{
				openJqInfo({content: "Operación satisfactoria al guardar Estado Financiero"});
			}
		}
	});
};



