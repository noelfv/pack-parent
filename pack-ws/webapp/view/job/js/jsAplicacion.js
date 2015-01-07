var

fmtVerDetalle = function(cellvalue, options, rowObject) {
	return "<a title='Ver detalle' href='javascript:void(0);' onclick='verDetalle(" + cellvalue + ")'><span class='ui-icon u-icon-ver-detalle'></span></a>" +
		"<a title='Editar' href='javascript:void(0);' onclick='editarDetalle(" + cellvalue + ")'><span class='ui-icon u-icon-editar'></span></a>" +
		"<a title='Eliminar' href='javascript:void(0);' onclick='elimniarDetalle(" + cellvalue + ")'><span class='ui-icon u-icon-eliminar'></span></a>";
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
},

editarDetalle = function(rowid) {
	AjaxUtil({
		url: obtenerContexto("application/get.html"),
		data: {
			"application.id": rowid
		},
		onSuccess: function(request) {
			$("#dialogAplicacion").dialog("open");
		}
	});
},

elimniarDetalle = function(rowid) {
	AjaxUtil({
		action: 'delete',
		url: obtenerContexto("application/delete.html"),
		data: {
			"application.id": rowid
		},
		onSuccess: function(request) {
			console.log('Hola Mundo');
		}
	});
};

$(document).ready(function() {
	$("#btnNuevo").button({icons : { primary : "ui-icon-document" }}).on("click", function(){
		var ids = $("#tbl_pnlApplications").jqGrid('getGridParam','selarrrow');
		var id = $("#tbl_pnlApplications").jqGrid('getGridParam', 'selrow');
		$("#dialogAplicacion").dialog("open");
	});

	request = $.parseJSON($("#labelJSON").text());
	if(request.tipoResultado == 'EXITO') {
		configurarAplicacion(request.applications);
	} else if(request.tipoResultado == 'ERROR_SISTEMA') {
		openJqError({type: "SYS", content: request.mensaje});
	}
	
	createDialogHTML("dialogAplicacion", {
		width : 290,
		buttons: {
			  "Aceptar": function(){ closeDialog($(this).attr("id")); }
			, "Cancelar": function(){ $("#dialogAplicacion").dialog("close"); }
		}
	});
});