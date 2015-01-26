var

configurarTrigger = function(request) {
    configurarGrid("pnlTrigger", {
        datatype : "local",
        data : request.triggerInstances,
        height : "auto",
        colNames : [ 'Nombre', 'Grupo', 'Nombre', 'Grupo', 'Estado', 'Tipo', 'Proxima Ejecuci\u00F3n' ],
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
        useColSpanStyle : true,
        groupHeaders : [ {
            startColumnName : 'triggerName',
            numberOfColumns : 2,
            titleText : 'Disparador'
        }, {
            startColumnName : 'jobName',
            numberOfColumns : 2,
            titleText : 'Trabajo'
        } ]
    });
},

reprogramar = function() {
    var _time = $("#txtTimer").val(), _cron = "0 " + _time.substring(3, 5) + " " + _time.substring(0, 2) + " ? * * *",
        _ajax = $.ajax({
            url : obtenerContexto("scheduler/reschedule.html"),
            data : {
                "time" : _cron,
                "cronTrigger" : $("#txtTimer").attr("data-triggerName")
            }
        });

    _ajax.success(function(request) {
        if (request.tipoResultado == 'EXITO') {
            configurarTrigger(request);
        } else if (request.tipoResultado == 'ERROR_SISTEMA') {
            openJqError({
                type : "SYS",
                content : request.mensaje
            });
        }

        $("#dialogReprogramar").dialog("close");
    });
},

listar = function() {
    var _ajax = $.ajax({
        url : obtenerContexto("scheduler/listar.html")
    });

    _ajax.success(function(request) {
        if (request.tipoResultado == 'EXITO') {
            configurarTrigger(request);
        } else if (request.tipoResultado == 'ERROR_SISTEMA') {
            openJqError({
                type : "SYS",
                content : request.mensaje
            });
        }
    });
};

$(document).ready(function() {
    /***
    $("#btnReprogramar").button({
        icons : {
            primary : "ui-icon-clock"
        }
    }).on("click", function() {
        var id = $("#tbl_pnlTrigger").jqGrid('getGridParam', 'selrow')
        if (id != null) {
            var row = $("#tbl_pnlTrigger").jqGrid('getRowData', id);
            $("#txtTimer").val(row.nextFireTime.substring(11, 16));
            $("#txtTimer").attr("data-triggerName", row.triggerName);
            $("#dialogReprogramar").dialog("open");
        } else {
            openJqWarn({ content : "Debe seleccionar al un disparador" });
        }
    });

    createDialogHTML("dialogReprogramar", {
        width : 290
    });

    $("#btnAceptar").button().on("click", reprogramar);

    $("#btnCancelar").button().on("click", function() {
        $("#dialogReprogramar").dialog("close");
    });

    $('.ui-text-time').timepicker({
        timeFormat : 'HH:mm'
    });
    ***/

    listar();
});