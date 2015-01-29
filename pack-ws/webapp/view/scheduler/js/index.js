var

formatterJobInstance = function(cellvalue, options, rowObject){
    return rowObject.jobInstance.jobName;
},

formatterExitStatus = function(cellvalue, options, rowObject){
    return rowObject.exitStatus.exitCode;
},

configurarJobExecutions = function(runningJobInstances) {
    configurarGrid("pnlJobExecutions", {
        datatype : "local",
        data : runningJobInstances,
        height : "auto",
        colNames:[
              'Nombre'
            , 'Inicio'
            , 'Termino'
            , 'Ultima Ejecuci\u00F3n'
            , 'Estado'
        ],
        colModel:[
              {name: 'jobInstance' , index: 'jobInstance' , width: 250, formatter: formatterJobInstance}
            , {name: 'startTime'   , index: 'startTime'   , width: 140, formatter: formatterDate, align: "center"}
            , {name: 'endTime'     , index: 'endTime'     , width: 140, formatter: formatterDate, align: "center"}
            , {name: 'lastUpdated' , index: 'lastUpdated' , width: 140, formatter: formatterDate, align: "center"}      
            , {name: 'exitStatus'  , index: 'exitStatus'  , width: 110, formatter: formatterExitStatus, align: "center"}
        ]
    });

    if(runningJobInstances.length > 0) {
        configurarJobParameters(runningJobInstances[0].jobParameters.parameters);
        configurarStepExecutions(runningJobInstances[0].stepExecutions);        
    }
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
        width : 900,
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
              {name: 'stepName'     , index: 'stepName'     , width: 300},
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

verDetalle = function(jobName) {
    AjaxUtil({
        url: obtenerContexto("scheduler/detail/" + jobName + ".html"),
        onSuccess: function(request) {
            if(request.runningJobInstances.length > 0) {
                if(!$("#pnlDesconocido").hasClass("hide")) {
                    $("#pnlDesconocido").addClass("hide");
                }
                $("#pnlDetalleEjecucion").removeClass("hide");
                configurarJobExecutions(request.runningJobInstances);
            } else {
                $("#pnlDesconocido").removeClass("hide");
                if(!$("#pnlDetalleEjecucion").hasClass("hide")) {
                    $("#pnlDetalleEjecucion").addClass("hide");
                }
            }

            $("#pnlDetalleTarea").removeClass("hide");
            $("#pnlTareas").addClass("hide");
        }
    });
},

fmtSemaforo = function(cellvalue, options, rowObject) {
    src = cellvalue == 'COMPLETED' ? obtenerContexto('public/img/green-circle-icone-4156-16.png') :
          cellvalue == 'FAILED'    ? obtenerContexto('public/img/red-circle-icone-5751-16.png')   :
                                     obtenerContexto('public/img/yellow-circle-icone-7011-16.png');
    
    return "<a href='javascript:void(0);' onclick=\"verDetalle('" + rowObject.jobName + "')\"><img src='" + src + "'></a>";
},

configurarTrigger = function(request) {
    configurarGrid("pnlTrigger", {
        datatype : "local",
        data : request.triggerInstances,
        height : "auto",
        colNames : [ 'Nombre', 'Grupo', 'Nombre', 'Grupo', 'Estado', 'Tipo', 'Proxima', 'Estado' ],
        colModel:[
              {name: 'triggerName' , index: 'triggerName' , width: 180}
            , {name: 'triggerGroup', index: 'triggerGroup', width: 90}
            , {name: 'jobName'     , index: 'jobName'     , width: 180}
            , {name: 'jobGroup'    , index: 'jobGroup'    , width: 90}
            , {name: 'triggerState', index: 'triggerState', width: 60}
            , {name: 'triggerType' , index: 'triggerType' , width: 40}
            , {name: 'nextFireTime', index: 'nextFireTime', width: 140, formatter: formatterDate, align: "center"}
            , {name: 'exitCode' , index: 'exitCode' , width: 60, formatter: fmtSemaforo, align: "center"}
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
        }, {
            startColumnName : 'nextFireTime',
            numberOfColumns : 2,
            titleText : 'Ejecuci\u00F3n'
        } ]
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
    listar();
});