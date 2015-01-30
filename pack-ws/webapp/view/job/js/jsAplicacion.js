var

fmtVerDetalle = function(cellvalue, options, rowObject) {
	return "<a title='Ver detalle' href='javascript:void(0);' onclick='verDetalle(" + cellvalue + ")'><span class='ui-icon u-icon-ver-detalle'></span></a>" +
		"<a title='Editar' href='javascript:void(0);' onclick='editarDetalle(" + cellvalue + ")'><span class='ui-icon u-icon-editar'></span></a>";
		// "<a title='Eliminar' href='javascript:void(0);' onclick='eliminarDetalle(" + cellvalue + ")'><span class='ui-icon u-icon-eliminar'></span></a>"
},

listar = function() {
	AjaxUtil({
		url: obtenerContexto("application/find.html"),
		data: {
			"application.name": $("#findApplicationName").val()
		},
		onSuccess: function(request) {
			configurarAplicacion(request.applications);
		}
	});
},

configurarAplicacion = function(applications) {
	configurarGrid("pnlApplications", {
	    datatype : "local",
		data : applications || [],
		height : "auto",
		width : 620,
		rowNum : 8,
		multiselect : false,
		colNames : [
			  'Aplicaci\u00F3n'
			, 'JNDI'
			// , 'Nro. Trab.'
			, ''   
		],
	   	colModel : [
	   		  {name: 'name'     , index: 'name'     , width: 250}
	   		, {name: 'jndi'     , index: 'jndi'     , width: 160}
	   		// , {name: 'sizeJobs' , index: 'sizeJobs' , width: 90}
	   		, {name: 'id'       , index: 'id'       , width: 70, align: "center", formatter: fmtVerDetalle, sortable: false}	
	   	]
	});
},

verDetalle = function(idAplicacion) {
	window.location = obtenerContexto("job/list/" + idAplicacion + ".html");
},

editarDetalle = function(rowid) {
	AjaxUtil({
		action: 'viewDetaill',
		element: 'application',
		url: obtenerContexto("application/get.html"),
		data: {
			"application.id": rowid
		},
		onSuccess: function(request) {
			$("#application\\.description").jqteVal(request.application.description);
			$("#dialogAplicacion").dialog("open");
		}
	});
},

eliminarDetalle = function(rowid) {
	AjaxUtil({
		action: 'delete',
		url: obtenerContexto("application/delete.html"),
		data: {
			"application.id": rowid
		},
		onSuccess: function(request) {
			configurarAplicacion(request.applications);
		}
	});
},

guardarDetalle = function() {
	AjaxUtil({
		action: 'save',
		container: 'dialogApplication',
		url: obtenerContexto("application/save.html"),
		data: {
			"application.id": $("#application\\.id").text(),
			"application.version": $("#application\\.version").val(),
			"application.name": $("#application\\.name").text(),
			"application.jndi": $("#application\\.jndi").val(),
			"application.description": StringUtil.escape($("#application\\.description").val()),
		},
		onSuccess: function(request) {
			configurarAplicacion(request.applications);
			$("#dialogAplicacion").dialog("close");
		},
		validation: function() {
			$.validity.start();

			$("#application\\.name").require();
			$("#application\\.jndi").require().match(function(){
			    var rgx = /^(jdbc)[\/]*/;
			    var str = $("#application\\.jndi").val();
			    
			    if(str.replace(rgx, "").length > 0) {
			    	return rgx.test(str);
			    } 
			    
			    return false;
			}, "Formato invalido. Debe iniciar con jdbc/, ejemplo: jdbc/APP");

			$("#application\\.description").assert(function(){
				var o = $("#application\\.description\\.validity"),
					l = $("#application\\.description").parent().parent().find(".jqte_editor").text().length,
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
		$("#findApplicationName").val("");
		listar();
	});
	$("#btnNuevo").button({icons : { primary : "ui-icon-document" }}).on("click", function(){
		// var ids = $("#tbl_pnlApplications").jqGrid('getGridParam','selarrrow');
		// var id = $("#tbl_pnlApplications").jqGrid('getGridParam', 'selrow');
		$("#application\\.id").html("");
		$("#application\\.version").val("");
		$("#application\\.name").val("");
		$("#application\\.jndi").val("");
		$("#application\\.description").jqteVal("");
		$("#dialogAplicacion").dialog("open");
	});
	
	$("#application\\.description\\.validity").tooltip({
		tooltipClass : "ui-state-error"
	});
	
	$("#application\\.description").jqte({
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

	createDialogHTML("dialogAplicacion", {
		title: "Registro de Aplicaci\u00F3n",
		width: 550,
		buttons: {
			  "Aceptar": function(){ guardarDetalle(); }
			, "Cancelar": function(){ $("#dialogAplicacion").dialog("close"); }
		}
	});

	listar();
});