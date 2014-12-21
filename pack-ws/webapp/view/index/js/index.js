var

formatterJobInstance = function(cellvalue, options, rowObject) {
	return "jobSolicitud";
},

formatterExitStatus = function(cellvalue, options, rowObject) {
	return cellvalue.exitCode;
},

configurarTrigger = function(request) {
	configurarGrid("pnlTrigger", {
	    datatype : "local",
		data : request.triggerInstances,
		height : "auto",
		colNames:[
			  'Nombre'
			, 'Grupo'
			, 'Nombre'
			, 'Grupo'
			, 'Estado'
			, 'Tipo'
			, 'Proxima Ejecuci\u00F3n'   
		],
	   	colModel:[
	   		  {name: 'triggerName' , index: 'triggerName' , width: 180}
	   		, {name: 'triggerGroup', index: 'triggerGroup', width: 90}
	   		, {name: 'jobName'     , index: 'jobName'     , width: 180}
	   		, {name: 'jobGroup'    , index: 'jobGroup'    , width: 90}
	   		, {name: 'triggerState', index: 'triggerState', width: 60}		
	   		, {name: 'triggerType' , index: 'triggerType' , width: 60}		
	   		, {name: 'nextFireTime', index: 'nextFireTime', width: 140, formatter: formatterDate, align: "center"}		
	   	]
	}, null, {
		useColSpanStyle: true,
		groupHeaders:[
		      {startColumnName: 'triggerName', numberOfColumns: 2, titleText: 'Disparador'}
		    , {startColumnName: 'jobName', numberOfColumns: 2, titleText: 'Trabajo'}
		]
	});
},

configurarJobExecutions = function(request) {
	configurarGrid("pnlJobExecutions", {
	    datatype : "local",
		data : request.runningJobInstances,
		height : "auto",
		colNames:[
			  'Nombre'
			, 'Inicio'
			, 'Termino'
			, 'Ultima Ejecuci\u00F3n'
			, 'Estado'
		],
	   	colModel:[
	   		  {name: 'jobInstance' , index: 'jobInstance' , width: 200, formatter: formatterJobInstance}
	   		, {name: 'startTime'   , index: 'startTime'   , width: 140, formatter: formatterDate, align: "center"}
	   		, {name: 'endTime'     , index: 'endTime'     , width: 140, formatter: formatterDate, align: "center"}
	   		, {name: 'lastUpdated' , index: 'lastUpdated' , width: 140, formatter: formatterDate, align: "center"}		
	   		, {name: 'exitStatus'  , index: 'exitStatus'  , width: 110, formatter: formatterExitStatus, align: "center"}
	   	]
	});

	configurarJobParameters(request.runningJobInstances[0].jobParameters.parameters);
	configurarStepExecutions(request.runningJobInstances[0].stepExecutions);
},

configurarJobParameters = function(jobExecution) {
	var jobParameters = [];
	for (var p in jobExecution) {
        if(jobExecution.hasOwnProperty(p)) {
        	jobParameters.push({"key": p, "value": jobExecution[p].value});
        }
    }
	configurarGrid("pnlParametros", {
	    datatype : "local",
		data : jobParameters,
		height : "auto",
		colNames:[
			  'Nombre'
			, 'Valor'  
		],
	   	colModel:[
	   		  {name: 'key'   , index: 'key'   , width: 140}
	   		, {name: 'value' , index: 'value' , width: 500}
	   	]
	});
},

configurarStepExecutions = function(stepExecutions) {
	configurarGrid("pnlPasos", {
	    datatype : "local",
		data : stepExecutions,
		height : "auto",
		colNames:[
			  'Paso'
			, 'Estado'  
			, 'Inicio'
			, 'Termino'
			, 'Commit'
			, 'Rollback'
			, 'Ley\u00F3'
			, 'Escribi\u00F3'
		],
	   	colModel:[
	   		  {name: 'stepName'     , index: 'stepName'     , width: 60},
	   		  {name: 'status'       , index: 'status'       , width: 90},
	   		  {name: 'startTime'    , index: 'startTime'    , width: 140, formatter: formatterDate, align: "center"},
	   		  {name: 'endTime'      , index: 'endTime'      , width: 140, formatter: formatterDate, align: "center"},
	   		  {name: 'commitCount'  , index: 'commitCount'  , width: 100, align: "right"},
	   		  {name: 'rollbackCount', index: 'rollbackCount', width: 100, align: "right"},
	   		  {name: 'readCount'    , index: 'readCount'    , width: 100, align: "right"},
	   		  {name: 'writeCount'   , index: 'writeCount'   , width: 100, align: "right"}
	   	]
	});
},

reprogramar = function() {
	var _time = $("#txtTimer").val(),
		_cron = "0 " + _time.substring(3, 5) + " " + _time.substring(0, 2) + " ? * * *",
		_ajax = $.ajax({
			url:  obtenerContexto("scheduler/reschedule.html"),
			data: {
				"time": _cron,
				"cronTrigger": $("#txtTimer").attr("data-triggerName")
			}
		});
	
	_ajax.success(function(request) {
		if(request.tipoResultado == 'EXITO') {
			configurarTrigger(request);
		} else if(request.tipoResultado == 'ERROR_SISTEMA') {
			openJqError({type: "SYS", content: request.mensaje});
		}
		
		$("#dialogReprogramar").dialog("close");
	});
},

listar = function() {
	var _ajax = $.ajax({
		url:  obtenerContexto("scheduler/listar.html")
	});
	
	_ajax.success(function(request) {
		if(request.tipoResultado == 'EXITO') {
			configurarTrigger(request);
			configurarJobExecutions(request);
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
			url:  obtenerContexto("scheduler/obtenerJob/iniciarJob.html")
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
	
	if(!$("#lblCargando").hasClass("hide")) {
		$("#btnEjecutar").button("disable");
		setTimeout(function(){ verificarOperacion() }, 5000);
	}
	
	$("#btnReprogramar")
		.button({icons: {primary: "ui-icon-clock"}})
		.on("click", function() {
			// var ids = $("#tbl_pnlTrigger").jqGrid('getGridParam','selarrrow');
			var id = $("#tbl_pnlTrigger").jqGrid('getGridParam','selrow')
			if(id != null) {
				var row = $("#tbl_pnlTrigger").jqGrid('getRowData', id);
				$("#txtTimer").val(row.nextFireTime.substring(11, 16));
				$("#txtTimer").attr("data-triggerName", row.triggerName);
				$("#dialogReprogramar").dialog("open");
			} else {
				openJqWarn({content: "Debe seleccionar al un disparador"});
			}
		});

	createDialogHTML("dialogReprogramar", {width: 290});
	
	$("#btnAceptar")
		.button()
		.on("click", reprogramar);
	
	$("#btnCancelar")
		.button()
		.on("click", function(){
			$("#dialogReprogramar").dialog("close");
		});
	
	$('.ui-text-time').timepicker({timeFormat: 'HH:mm'});
	
	listar();
});