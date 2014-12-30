var

fmtVerDetalle = function(cellvalue, options, rowObject) {
	return "<a href='javascript:void(0);' onclick='verDetalle(" + cellvalue + ")'><span class='ui-icon u-icon-ver-detalle'></span></a>";
},

fmtVerDetalleTrabajo = function(cellvalue, options, rowObject) {
	return "<a href='javascript:void(0);' onclick='verDetalleTrabajo(" + cellvalue + ")'><span class='ui-icon u-icon-ver-detalle'></span></a>";
},

fmtVerDetallePaso = function(cellvalue, options, rowObject) {
	return "<a href='javascript:void(0);' onclick='verDetallePaso(" + cellvalue + ")'><span class='ui-icon u-icon-ver-detalle'></span></a>";
},

formatterExitStatus = function(cellvalue, options, rowObject) {
	return cellvalue.exitCode;
},


listar = function() {
	var _ajax = $.ajax({
		url:  obtenerContexto("job/listar.html")
	});
	
	_ajax.success(function(request) {
		if(request.tipoResultado == 'EXITO') {
			configurarAplicacion(request);
		} else if(request.tipoResultado == 'ERROR_SISTEMA') {
			openJqError({type: "SYS", content: request.mensaje});
		}
	});
},

configurarAplicacion = function(request) {
	configurarGrid("pnlApplications", {
	    datatype : "local",
		data : [
			{}, {}, {}, {}, {},
			{}, {}, {}, {}, {},
			{}, {}, {}, {}, {},
			{}, {}, {}, {}, {},
			{}, {}, {}, {}, {},
			{}, {}, {}, {}, {}
		],
		height : "auto",
		width : 580,
		rowNum : 12,
		colNames : [
			  'Aplicaci\u00F3n'
			, 'JNDI'
			, 'Nro. Trabajos'
			, ''   
		],
	   	colModel : [
	   		  {name: 'name'     , index: 'name'     , width: 300}
	   		, {name: 'jndi'     , index: 'jndi'     , width: 100}
	   		, {name: 'sizeJobs' , index: 'sizeJobs' , width: 100}
	   		, {name: 'id'       , index: 'id'       , width: 40, formatter: fmtVerDetalle}	
	   	]
	});
},

verDetalle = function(idAplicacion) {
	var _ajax = $.ajax({
		url:  obtenerContexto("job/listar.html") /*Change */
	});
	
	_ajax.success(function(request) {
		if(request.tipoResultado == 'EXITO') {
			/* Change */
			$("#lblAplicacion").html("{request.aplicacion}");
			$("#layoutDetalleAplicacion").removeClass("hide");
			$("#layoutAplicacion").addClass("hide");
			configurarTrabajo(request);
		} else if(request.tipoResultado == 'ERROR_SISTEMA') {
			openJqError({type: "SYS", content: request.mensaje});
		}
	});
}

configurarTrabajo = function(request) {
	configurarGrid("pnlJobs", {
	    datatype : "local",
		data : [
			{}, {}, {}, {}, {},
			{}, {}, {}, {}, {},
			{}, {}, {}, {}, {},
			{}, {}, {}, {}, {},
			{}, {}, {}, {}, {},
			{}, {}, {}, {}, {}
		],
		height : "auto",
		width : 580,
		rowNum : 6,
		colNames : [
			  'Trabajo'
			, 'Cron'
			, 'Nro. Pasos'
			, ''   
		],
	   	colModel : [
	   		  {name: 'name'          , index: 'name'          , width: 300}
	   		, {name: 'cronExpression', index: 'cronExpression', width: 100}
	   		, {name: 'sizeSteps'     , index: 'sizeSteps'     , width: 100}
	   		, {name: 'id'            , index: 'id'            , width: 40, formatter: fmtVerDetalleTrabajo}	
	   	]
	});
},

verDetalleTrabajo = function() {
	var _ajax = $.ajax({
		url:  obtenerContexto("job/listar.html") /*Change */
	});
	
	_ajax.success(function(request) {
		if(request.tipoResultado == 'EXITO') {
			/* Change */
			$("#lblTrabajo").html("{request.aplicacion}:{request.trabajo}");
			$("#layoutDetalleTrabajo").removeClass("hide");
			$("#layoutDetalleAplicacion").addClass("hide");
			configurarPaso(request);
		} else if(request.tipoResultado == 'ERROR_SISTEMA') {
			openJqError({type: "SYS", content: request.mensaje});
		}
	});
},

configurarPaso = function(request) {
	configurarGrid("pnlSteps", {
	    datatype : "local",
		data : [
			{}, {}, {}, {}, {},
			{}, {}, {}, {}, {},
			{}, {}, {}, {}, {},
			{}, {}, {}, {}, {},
			{}, {}, {}, {}, {},
			{}, {}, {}, {}, {}
		],
		height : "auto",
		width : 690,
		rowNum : 6,
		colNames : [
			  'Pos.'
			, 'Paso'
			, 'Tipo'
			, 'JNDI'
			, ''
		],
	   	colModel : [
	   		  {name: 'name'          , index: 'name'          , width: 40}
	   		, {name: 'cronExpression', index: 'cronExpression', width: 300}
	   		, {name: 'sizeSteps'     , index: 'sizeSteps'     , width: 100}
	   		, {name: 'sizeSteps'     , index: 'sizeSteps'     , width: 100}
	   		, {name: 'id'            , index: 'id'            , width: 40, formatter: fmtVerDetallePaso}	
	   	]
	});
},

verDetallePaso = function() {

},

verificarOperacion = function() {
	useFunctionLoading = function(method) {
		if(method == "start") {
			
		} else if(method == "stop") {
			
		}
	}
	
	var _ajax = $.ajax({
		url:  obtenerContexto("scheduler/obtenerJob/listarJob.html")
	});
	
	_ajax.success(function(request){
		if(request.tipoResultado == 'EXITO') {
			if(request.handler.estado == 'INACTIVO') {
				$("#lblCargando").addClass("hide");
				$("#btnEjecutar").button("enable");
				configurarJobExecutions(request);
			} else {
				setTimeout(function(){ verificarOperacion() }, 5000);
			}
		} else if(request.tipoResultado == 'ERROR_SISTEMA') {
			openJqError({type: "SYS", content: request.mensaje});
		}
	});
},

iniciarOperacion = function() {
	var _ajax = $.ajax({
			url:  obtenerContexto("job/obtenerJob/iniciarJob.html")
		});
		
	_ajax.success(function(request){
		if(request.tipoResultado == 'EXITO') {
			if(request.handler.estado == 'ACTIVO') {
				$("#lblCargando").removeClass("hide");
				$("#btnEjecutar").button("disable");
				setTimeout(function(){ verificarOperacion() }, 5000);
			}
			configurarJobExecutions(request);
		} else if(request.tipoResultado == 'ERROR_SISTEMA') {
			openJqError({type: "SYS", content: request.mensaje});
		}
	});
};

$(document).ready(function() {
	$("#btnEjecutar")
		.button({icons: {primary: "ui-icon-play"}})
		.on("click", iniciarOperacion);
	
	/* if(!$("#lblCargando").hasClass("hide")) {
		$("#btnEjecutar").button("disable");
		setTimeout(function(){ verificarOperacion() }, 5000);
	} */
	
	listar();
});