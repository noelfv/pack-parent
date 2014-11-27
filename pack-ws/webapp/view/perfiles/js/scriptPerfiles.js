var seleccionados = '';
var PARAMETRO_OPCION = 59;
guardar = function() {
	if (validarGuardar()) {
		procesarGuardar();
	}
},

guardarAccion = function() {
	if(true){
		procesarGuardarAccion();
	}
},


cancelar = function() {
	$("#cmbPerfilEstado").val("-1");
	$("#txtCodigoPerfil").val("");
	$("#txtNombrePerfil").val("");
},

isEmpty = function(element) {
	return ($.trim(element.value).length > 0);
},

isSelect = function(element) {
	return parseInt(element.value) != -1;
},

cancelarAccion = function() {
	$("#dialogAcciones").dialog('close');
},

procesarGuardar = function() {
	var param = {
		"perfil.idPerfil" : ($("#hidIdPerfil").val() == "" ? 0 : $("#hidIdPerfil").val()),
		"perfil.codigoPerfil" : $("#txtCodigoPerfil").val(),
		"perfil.nombrePerfil" : $("#txtNombrePerfil").val(),
		"perfilModel.idEstado" : $("#cmbPerfilEstado").val()	
	};					
										
	_post = $.post(obtenerContexto("perfiles/guardar.do"), param);
	_post.success(function(data) {
		if(data.tipoResultado == "EXITO") {
			configurarGrid("pnlPerfiles", cargarGridPerfiles(data));
			$("#hidIdPerfil").val("");

			$("#cmbPerfilEstado").val("-1");
			$("#txtCodigoPerfil").val("");
			$("#txtNombrePerfil").val("");
		} else if(data.tipoResultado == "ERROR") {
			openJqError({content: data.mensaje});
		} else if(data.tipoResultado == "ERROR_SISTEMA") {
			openJqError({type: "SYS", content: data.mensaje});
		}						
	});
},

procesarGuardarAccion = function() {

	var param = {
		"perfil.idPerfil" : ($("#hidIdPerfilAccion").val() == "" ? 0 : $("#hidIdPerfilAccion").val()),
		"seleccionados" : seleccionados	
	};					
										
	_post = $.post(obtenerContexto("perfiles/guardarAccion.do"), param);
	_post.success(function(data) {
		if(data.tipoResultado == "EXITO") {
			$("#hidIdPerfilAccion").val("");
			openJqInfo({
				content: data.mensaje
			});
		} else if(data.tipoResultado == "ERROR") {
			$("#hidIdPerfilAccion").val("");
			seleccionados="";
			openJqError({content: data.mensaje});
		} else if(data.tipoResultado == "ERROR_SISTEMA") {
			openJqError({type: "SYS", content: data.mensaje});
		}						
	});
},

limpiar = function() {
	$("#hidIdPerfil").val("");

	$("#cmbPerfilEstado").val("-1");
	$("#txtCodigoPerfil").val("");
	$("#txtNombrePerfil").val("");
	
	options = $.extend(options,{data: []});
	configurarGrid('pnlParametria', options);
},

validarGuardar = function() {
	$.validity.start();
	
	$("#txtCodigoPerfil").assert(isEmpty, "Debe ingresar un valor.");
	$("#txtNombrePerfil").assert(isEmpty, "Debe ingresar un valor.");	
	$("#cmbPerfilEstado").assert(isSelect, "Debe seleccionar un valor de la lista.");
	
	var result = $.validity.end();
	return result.valid;
},

$(document).ready(function() {
	$("#btnGrabar").button({icons: {primary: 'ui-icon-disk'}}).bind('click', guardar); //ui-icon-bbva-disk  ui-icon-disk
	$("#btnCancelar").button({icons: {primary: 'ui-icon ui-icon-arrowreturn-1-w'}}).bind('click', cancelar);
	
	$("#btnGrabarAccion").button({icons: {primary: 'ui-icon-disk'}}).bind('click', guardarAccion);
	$("#btnCancelarAccion").button({icons: {primary: 'ui-icon ui-icon-arrowreturn-1-w'}}).bind('click', cancelarAccion);
	
	$.ajax({
		type : "post",
		// data : datos,
		url : obtenerContexto("perfiles/listar.do"),
		success : function(data) {
			configurarGrid("pnlPerfiles", cargarGridPerfiles(data));
		}
	});

}),

cargarGridPerfiles = function(data){
	grid = $("#pnlPerfiles");
	var options = {
	        data: data.listaPerfiles,
	        width: 400,
	        height: 190,
	        colNames: ['Codigo',
	            'Descripccion',
	            'Estado',
	            'Acciones'],
	        colModel: [{name: 'codigoPerfil', index:'codigoPerfil', width: 70, align:'center', editable:false},
	            {name: 'nombrePerfil', index:'nombrePerfil', width: 110, align:'center', editable:false},
	            {name: 'estado', index:'estado', width: 90, align:'center'},
	            {name: 'act', index:'act', width: 110, align: 'center', sortable: false, formatter: function(cellvalue, options, rowObject) {
	                return construirBotones("'"+rowObject.codigoPerfil+"'"); }}
	        ],
	        multiselect : false

	        // , caption: "Estados Financieros"
	    };

	       return options;
	},
	
	construirBotones = function(id){
	var id = id;
	return '<table style="width:120px"><tr><td><a class="ui-icon ui-icon-pencil" title="Modificar Perfil" onclick="modificarPerfil('+id+');"></a></td>' +
    '<td><a class="ui-icon ui-icon-trash" title="Eliminar Perfil" onclick="openJqConfirm(opcionesDialog('+id+'));"></a></td>' +
    '<td><a class="ui-icon ui-icon-calculator" title="Asignacion de Acciones" onclick="accionesPerfil('+id+');"></a></td></tr></table>';
},

modificarPerfil = function(id){
	var param = {
			"perfilModel.item.codigoPerfil" : id};					
											
		_post = $.post(obtenerContexto("perfiles/obtener.do"), param);
		_post.success(function(data) {
			if(data.tipoResultado == "EXITO") {
				$("#hidIdPerfil").val(data.item.idPerfil);
				$("#txtCodigoPerfil").val(data.item.codigoPerfil);
				$("#txtNombrePerfil").val(data.item.nombrePerfil);
				$("#cmbPerfilEstado").val(data.item.estado);
			} else if(data.tipoResultado == "ERROR") {
				openJqError({content: data.mensaje});
			} else if(data.tipoResultado == "ERROR_SISTEMA") {
				openJqError({type: "SYS", content: data.mensaje});
			}						
		});
},

borrarPerfil = function(id){
//	jConfirm('¿Seguro de Eliminar Perfil: '+ id +'?', 'Eliminar', function(r) {
//	    if (r){
	    	var param = {"perfilModel.item.codigoPerfil" : id};					
			_post = $.post(obtenerContexto("perfiles/borrar.do"), param);
			_post.success(function(data) {
				if(data.tipoResultado == "EXITO") {
					configurarGrid("pnlPerfiles", cargarGridPerfiles(data));
				} else if(data.tipoResultado == "ERROR") {
					openJqError({content: data.mensaje});
				} else if(data.tipoResultado == "ERROR_SISTEMA") {
					openJqError({type: "SYS", content: data.mensaje});
				}						
			});
// }
//	  });

},

accionesPerfil = function(id){
	seleccionados="";
	var param = {
			"perfilModel.item.codigoPerfil" : id};					
											
		_post = $.post(obtenerContexto("perfiles/asignarAcciones.do"), param);
		_post.success(function(data) {
			if(data.tipoResultado == "EXITO") {
				configurarGrid("pnlAcciones", cargarGridAcciones(data));
				$("#hidIdPerfilAccion").val(data.item.idPerfil);
				$("#txtAccionNombrePerfil").val(data.item.codigoPerfil + " - " + data.item.nombrePerfil);
				abrir("Asignacion de Acciones");
			} else if(data.tipoResultado == "ERROR") {
				openJqError({content: data.mensaje});
			} else if(data.tipoResultado == "ERROR_SISTEMA") {
				openJqError({type: "SYS", content: data.mensaje});
			}						
		});
},

cargarGridAcciones = function(data){
	grid = $("#pnlAcciones");
	var options = {
	        data: data.listaParametros,
	        width: 670,
	        height: 220,
	        colNames: ['Codigo',
	            'Nombre',
	            'Tipo',
	            'Asignacion'],
	        colModel: [{name: 'id', index:'id', width: 100, align:'right', editable:false},
	            {name: 'nombre', index:'nombre', width: 350, align:'left', editable:false, formatter:negrita},
// {name: 'parametro.nombre', index:'parametro.nombre', width: 100,
// align:'center', formatter:tipoTexto}, //tipo parametro.nombre
	            {name: 'parametro.nombre', index:'parametro.nombre', width: 100, align:'center', formatter:tipoTexto},
	            {name: 'booleano', index:'booleano', width: 100, align: 'center', editable: true,
	            	edittype: 'checkbox', editoptions: { value: "S:N", defaultValue: "N" }, 
	            	formatter: "checkbox", formatoptions: { disabled: false}}
	        ],
	        beforeSelectRow: function (rowid, e) {
	            var $self = $(this),
	                iCol = $.jgrid.getCellIndex($(e.target).closest("td")[0]),
	                cm = $self.jqGrid("getGridParam", "colModel"),
	                localData = $self.jqGrid("getLocalRow", rowid);
	            if (cm[iCol].name === "booleano") {
	            	var valor = 'N';
	                if($(e.target).is(":checked")){
	                	seleccionados= seleccionados+rowid+',';
	                	valor='S';
	                }else{
	                	seleccionados = seleccionados.replace(rowid+",","");
	                	valor = 'N';
	                }
	                
	                if(localData.parametro.nombre === "Opcion"){
	                	var esAccion = true;
	                	rowid++;
	                	while(esAccion){
	                		nexRowData = $self.jqGrid("getLocalRow", rowid);
	                		 if(nexRowData !=null && nexRowData.parametro.nombre === "Opcion"){
	                			 esAccion = false;
	                		 }else{
	                			 nexRowData.booleano = valor;
	                			 $('#' + rowid + ' input:checkbox').attr("checked", (valor=='S')?true:false);
	                			 if(valor=='S'){
	                				 seleccionados= seleccionados+rowid+',';
	                			 }else{
	                				 seleccionados = seleccionados.replace(rowid+",",""); 
	                			 }
	                		 }
	                		 rowid++;
	                	}
		            	
		            }
	            }
	            
	            return true; // allow selection
	        },
	        multiselect : false

	        // , caption: "Estados Financieros"
	    };

	       return options;
	},

negrita = function(cellValue, options, rowObject){
		
		if(rowObject.booleano == 'S'){
         	seleccionados= seleccionados+rowObject.id+',';
        }
		var peso = (parseInt(rowObject.parametro.id) == PARAMETRO_OPCION) ? "Bold" : "Normal";
        var cellHtml = "<span style='font-weight:" + peso + "' originalValue='" + cellValue + "'>" + cellValue + "</span>";
        return cellHtml;		
},
tipoTexto = function(cellValue, options, rowObject){
	var peso = (parseInt(rowObject.parametro.id) == PARAMETRO_OPCION) ? "Bold" : "Normal";
	var tipo = (parseInt(rowObject.parametro.id) == PARAMETRO_OPCION) ? "Opcion" : "Accion";
    var cellHtml = "<span style='font-weight:" + peso + "' originalValue='" + cellValue + "'>" + tipo + "</span>";
    return cellHtml;		
},

abrir = function(title) {
	$("#dialogAcciones").dialog({
		title : title,
		autoOpen : true,
		resizable : false,
		closeOnEscape : false,
		modal : true,
		minHeight: 'auto',
		width : 700,
		position: ['center','center']
	});
},

opcionesDialog = function(id){
	var options = {
	        content:'¿Seguro de Eliminar perfil '+ id + '?',
	        buttons: {
				  "Sí": function(){borrarPerfil(id);closeDialog($(this).attr("id"));}
				, "No": function(){closeDialog($(this).attr("id"));}
			}
	    };

	       return options;
}