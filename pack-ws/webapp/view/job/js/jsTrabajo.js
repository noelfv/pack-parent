var

fmtVerDetalle = function(cellvalue, options, rowObject) {
	/** return "<a title='Ver detalle' href='javascript:void(0);' onclick='verDetalle(" + cellvalue + ")'><span class='ui-icon u-icon-ver-detalle'></span></a>" +
		"<a title='Editar' href='javascript:void(0);' onclick='editarDetalle(" + cellvalue + ")'><span class='ui-icon u-icon-editar'></span></a>" +
		"<a title='Eliminar' href='javascript:void(0);' onclick='eliminarDetalle(" + cellvalue + ")'><span class='ui-icon u-icon-eliminar'></span></a>";
	**/
	return "<a title='Editar' href='javascript:void(0);' onclick='editarDetalle(" + cellvalue + ")'><span class='ui-icon u-icon-editar'></span></a>" +
		"<a title='Eliminar' href='javascript:void(0);' onclick='eliminarDetalle(" + cellvalue + ")'><span class='ui-icon u-icon-eliminar'></span></a>";
},

listar = function() {
	AjaxUtil({
		url: obtenerContexto("job/find.html"),
		data: {
			"job.application.id": $("#application\\.id").val(),
			"job.name": $("#findJobName").val()
		},
		onSuccess: function(request) {
			configurarTrabajo(request.jobs);
		}
	});
},

configurarTrabajo = function(jobs) {
	configurarGrid("pnlJobs", {
	    datatype : "local",
		data : jobs || [],
		height : "auto",
		width : 630,
		rowNum : 5,
		colNames : [
			  'Trabajo'
			, 'Cron'
			, 'Tipo'
			, ''   
		],
	   	colModel : [
	   		  {name: 'name'          , index: 'name'          , width: 300}
	   		, {name: 'cronExpression', index: 'cronExpression', width: 120}
	   		, {name: 'type'          , index: 'type      '    , width: 90, align: "center"}
	   		, {name: 'id'            , index: 'id'            , width: 70, align: "center", formatter: fmtVerDetalle, sortable: false}
	   	]
	});
},

verDetalle = function(idJob) {
	window.location = obtenerContexto("step/list/" + idJob + ".html");
},

editarDetalle = function(rowid) {
	AjaxUtil({
		action: 'viewDetaill',
		element: 'job',
		url: obtenerContexto("job/get.html"),
		data: {
			"job.application.id": $("#application\\.id").val(),
			"job.id": rowid
		},
		onSuccess: function(request) {
		    $("#job\\.nameOld").val($("#job\\.name").val());
			$("#job\\.description").jqteVal(request.job.description);
			$("#dialogTrabajo").dialog("open");
		}
	});
},

eliminarDetalle = function(rowid) {
	AjaxUtil({
		action: 'delete',
		url: obtenerContexto("job/delete.html"),
		data: {
			"job.id": rowid
		},
		onSuccess: function(request) {
			configurarTrabajo(request.jobs);
		}
	});
},

guardarDetalle = function() {
	AjaxUtil({
		action: 'save',
		container: 'dialogTrabajo',
		url: obtenerContexto("job/save.html"),
		data: {
			"job.application.id": $("#application\\.id").val(),
			"job.id": $("#job\\.id").text(),
			"job.version": $("#job\\.version").val(),
			"job.name": $("#job\\.name").val(),
			"job.type": $("#job\\.type").val(),
			"job.cronExpression": $("#job\\.cronExpression").val(),
			"job.description": StringUtil.escape($("#job\\.description").val()),
			"nameOld" : $("#job\\.nameOld").val()
		},
		onSuccess: function(request) {
			configurarTrabajo(request.jobs);
			$("#dialogTrabajo").dialog("close");
		},
		validation: function() {
			$.validity.start();

			$("#job\\.name").require();
			$("#job\\.cronExpression").require();

			$("#job\\.description").assert(function(){
				var o = $("#job\\.description\\.validity"),
					l = $("#job\\.description").parent().parent().find(".jqte_editor").text().length,
					v = (l < 1201)

				o.attr("title", "La longitud de la descripci\u00F3n no debe de exceder de 1200 caracteres. Se ha ingresado " + l + " caracteres.");

				if(!v) {
					if(o.hasClass('hide')) {
						o.removeClass('hide'); 	
					}
				} else {
					if(!o.hasClass('hide')) {
						o.addClass('hide'); 	
					}
				}

				return v;
			}, "La longitud de la descripci\u00F3n no debe de exceder de 1200 caracteres");

			var result = $.validity.end();
			return result.valid;
		}
	});
};

$(document).ready(function() {
	$("#btnBuscar").button({icons : { primary : "ui-icon-search" }}).on("click", listar);
	$("#btnLimpiar").button({icons : { primary : "ui-icon-refresh" }}).on("click", function(){
		$("#findJobName").val("");
		listar();
	});
	$("#btnNuevo").button({icons : { primary : "ui-icon-document" }}).on("click", function(){
		$("#job\\.id").html("");
		$("#job\\.version").val("");
		$("#job\\.name").val("");
		$("#job\\.cronExpression").val("");
		$("#job\\.description").jqteVal("");
		$("#dialogTrabajo").dialog("open");
	});
	
	$("#job\\.description\\.validity").tooltip({
		tooltipClass : "ui-state-error"
	});

	$("#job\\.description").jqte({
		left: false,
		center: false,
		right: false,
		sub: false,
		sup: false,
		outdent: false,
		indent: false,
		strike: false,
		link: false,
		unlink: false,
		remove: false,
		rule: false,
		source: false,
		ol: false,
		ul: false
	});

	createDialogHTML("dialogTrabajo", {
		title: "Registro de Trabajo",
		width: 550,
		buttons: {
			  "Aceptar": function(){ guardarDetalle(); }
			, "Cancelar": function(){ $("#dialogTrabajo").dialog("close"); }
		}
	});

	listar();
});