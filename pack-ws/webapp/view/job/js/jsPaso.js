var

fmtVerDetalle = function(cellvalue, options, rowObject) {
	return "<a title='Ver detalle' href='javascript:void(0);' onclick='verDetalle(" + cellvalue + ")'><span class='ui-icon u-icon-ver-detalle'></span></a>" +
		"<a title='Editar' href='javascript:void(0);' onclick='editarDetalle(" + cellvalue + ")'><span class='ui-icon u-icon-editar'></span></a>" +
		"<a title='Eliminar' href='javascript:void(0);' onclick='elimniarDetalle(" + cellvalue + ")'><span class='ui-icon u-icon-eliminar'></span></a>";
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

configurarAplicacion = function(applications) {
	configurarGrid("pnlApplications", {
	    datatype : "local",
		data : applications,
		height : "auto",
		width : 620,
		rowNum : 10,
		multiselect : false,
		colNames : [
			  'Aplicaci\u00F3n'
			, 'JNDI'
			, 'Nro. Trab.'
			, ''   
		],
	   	colModel : [
	   		  {name: 'name'     , index: 'name'     , width: 250}
	   		, {name: 'jndi'     , index: 'jndi'     , width: 160}
	   		, {name: 'sizeJobs' , index: 'sizeJobs' , width: 90}
	   		, {name: 'id'       , index: 'id'       , width: 70, align: "center", formatter: fmtVerDetalle, sortable: false}	
	   	],
	   	onPaging: function(pgButton) {
	   		// Change de Page
	   		//console.log("eventLocal1");
	   	},
	   	loadComplete: function(data) {
	   		//console.log("eventLocal2");
	   	},
	   	onSelectRow: function(rowid, status, e) {
	   		//console.log("eventLocal3");
	   	}
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
},

editarDetalle = function(rowid) {
	$("#dialogAplicacion").dialog("open");
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
	$("#btnNuevo").button({icons : { primary : "ui-icon-document" }}).on("click", function(){
		var ids = $("#tbl_pnlApplications").jqGrid('getGridParam','selarrrow');
        var id = $("#tbl_pnlApplications").jqGrid('getGridParam', 'selrow');

        console.log(ids);
        console.log(id);
	});

	/* $("#btnEliminar").button({icons : { primary : "ui-icon-trash" }}); */
	createDialogHTML("dialogAplicacion", {
        width : 290
    });
	/* if(!$("#lblCargando").hasClass("hide")) {
		$("#btnEjecutar").button("disable");
		setTimeout(function(){ verificarOperacion() }, 5000);
	} */
	
	configurarAplicacion($.parseJSON($("#labelJSON").text()));
});