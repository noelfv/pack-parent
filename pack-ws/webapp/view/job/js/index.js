var

fmtVerDetalle = function(cellvalue, options, rowObject) {
	return "<a href='javascript:void(0);' onclick='verDetalle(\"" + cellvalue + "\")'><span class='ui-icon u-icon-ver-detalle'></span></a>";
},

formatterExitStatus = function(cellvalue, options, rowObject) {
	return cellvalue.exitCode;
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
		height : 300,
		width : 580,
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
	$("#lblAplicacion").html("request");
	$("#layoutDetalleAplicacion").removeClass("hide");
	$("#layoutAplicacion").addClass("hide");
}

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