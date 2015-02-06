spool 07_loadInitialDataBatch.log

delete from CONELE.MNTR_PARAMETRO;
delete from CONELE.MNTR_PASO;
delete from CONELE.MNTR_TRABAJO;
delete from CONELE.MNTR_APLICACION;
commit;

--=====================================================================================================================================================================--
-- MNTR_APLICACION
--=====================================================================================================================================================================--
insert into CONELE.MNTR_APLICACION (ID, VERSION, NOMBRE, JNDI, DESCRIPCION)
values (1, 0, 'packBBVA', 'jdbc/BBVA', '');
commit;

--=====================================================================================================================================================================--
-- MNTR_TRABAJO
--=====================================================================================================================================================================--
insert into CONELE.MNTR_TRABAJO (ID, VERSION, NOMBRE, DESCRIPCION, CRON, ID_APLICACION, TIPO)
values (1, 2, 'jobSimulacion', 'Genera un archivo plano ', '0 38 16 ? * * *', 1, 'FLOW');

insert into CONELE.MNTR_TRABAJO (ID, VERSION, NOMBRE, DESCRIPCION, CRON, ID_APLICACION, TIPO)
values (2, 6, 'jobTerritorioOficina', 'Realizar la actualizaci' || CHR(38) || '#243;n de los territorios y oficinas leyendo el servico de CentroWebServiceBBVA (GESCAR)', '0 59 16 ? * * *', 1, 'SIMPLE');

insert into CONELE.MNTR_TRABAJO (ID, VERSION, NOMBRE, DESCRIPCION, CRON, ID_APLICACION, TIPO)
values (3, 0, 'jobDepuracionArchivo', 'Eliminar los archivos mas antiguos', '0 0 1 ? * * *', 1, 'SIMPLE');
commit;

--=====================================================================================================================================================================--
-- MNTR_PASO
--=====================================================================================================================================================================--
insert into CONELE.MNTR_PASO (ID, VERSION, NOMBRE, ORDEN, LECTOR, ESCRITOR, ID_TRABAJO, DESCRIPCION, SIG_PASO)
values (1, 0, 'generarSolicitudCONELE', 1, 'READER_TABLE', 'WRITER_TEXT_POSITION', 1, 'A partir de los datos de la tabla V_SOLICITUD generar un archivo de las solicitudes del sistema PLD unificado', 'decisionSolicitudIICE');

insert into CONELE.MNTR_PASO (ID, VERSION, NOMBRE, ORDEN, LECTOR, ESCRITOR, ID_TRABAJO, DESCRIPCION, SIG_PASO)
values (2, 0, 'decisionSolicitudIICE', 2, '', 'WRITER_DECISOR', 1, 'Evalua si usa el esquema IICE para la generación del archivo', '[{"class":"com.bbva.batch.util.DeciderBatch","nextStep":"generarSolicitudIICE","status":"COMPLETED"},{"class":"com.bbva.batch.util.DeciderBatch","nextStep":"endFail","status":"FAILED"}]');

insert into CONELE.MNTR_PASO (ID, VERSION, NOMBRE, ORDEN, LECTOR, ESCRITOR, ID_TRABAJO, DESCRIPCION, SIG_PASO)
values (3, 0, 'generarSolicitudIICE', 3, 'READER_TABLE', 'WRITER_TEXT_POSITION', 1, 'A partir de los datos de la tabla V_SOLICITUD generar un archivo de las solicitudes del sistema PLD unificado', 'end');

insert into CONELE.MNTR_PASO (ID, VERSION, NOMBRE, ORDEN, LECTOR, ESCRITOR, ID_TRABAJO, DESCRIPCION, SIG_PASO)
values (4, 0, 'desactivarTerritorios', 1, '', 'WRITER_QUERY', 2, 'Desactiva los territorios', '');

insert into CONELE.MNTR_PASO (ID, VERSION, NOMBRE, ORDEN, LECTOR, ESCRITOR, ID_TRABAJO, DESCRIPCION, SIG_PASO)
values (5, 0, 'cargandoTerritorio', 2, 'READER_XML', 'WRITER_TABLE', 2, 'Inserta o actualiza la lista de territorios', '');

insert into CONELE.MNTR_PASO (ID, VERSION, NOMBRE, ORDEN, LECTOR, ESCRITOR, ID_TRABAJO, DESCRIPCION, SIG_PASO)
values (6, 0, 'cargandoOficina', 3, 'READER_XML', 'WRITER_TABLE', 2, 'Inserta o actualiza la lista de oficina', '');

insert into CONELE.MNTR_PASO (ID, VERSION, NOMBRE, ORDEN, LECTOR, ESCRITOR, ID_TRABAJO, DESCRIPCION, SIG_PASO)
values (7, 0, 'depurarArchivo', 1, '', 'WRITER_DROOLS', 3, 'Elimina los archivos mas antiguos', '');
commit;

--=====================================================================================================================================================================--
-- MNTR_PARAMETRO
--=====================================================================================================================================================================--
insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (20, 0, 'READER_TABLE', 2, 'SELECT', 'String', 'select lpad(trim(SOLICITUD), 10, ''0'') ,PRODUCTO_PACK ,SUBPRODUCTO_PACK ,ESTADO_PACK ,TO_CHAR(FECHA_ALTA, ''YYYY-MM-DD'') FECHA_ALTA ,lpad(trim(TRUNC(ROUND(IMPORTE, 2) * 100, 0)), 15, ''0'') IMPORTE ,lpad(trim(DIVISA), 3, ''0'') ,TIPODOCUMENTO_PACK ,NUM_DOI ,CODIGO_CLIENTE ,CONTRATO ,lpad(trim(PLAZO), 3, ''0'') ,OFICINA ,EJECUTIVO ,lpad(trim(TRUNC(ROUND(TASA, 2) * 100, 0)), 5, ''0'') TASA', null, null, '', 1, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (16, 0, 'READER_TABLE', 1, 'RULE_PARAM', 'Byte', '', null, null, '', 1, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (17, 0, 'READER_TABLE', 1, 'RULE_JNDI', 'String', 'jdbc/BBVA', null, null, '', 1, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (21, 0, 'READER_TABLE', 3, 'FROM', 'String', 'from CONELE.V_SOLICITUD', null, null, '', 1, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (23, 0, 'READER_TABLE', 5, 'SORT', 'String', 'fecha_alta', null, null, '', 1, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (24, 0, 'READER_TABLE', 6, 'PAGE_SIZE', 'Long', '', 100, null, '', 1, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (22, 0, 'READER_TABLE', 4, 'WHERE', 'String', 'where CONTRATO is not null and trunc(fecha_alta) >= trunc(add_months(sysdate, ${mesesAnteriores}))', null, null, '', 1, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (19, 0, 'READER_TABLE', 1, 'JNDI', 'String', 'jdbc/BBVA', null, null, '', 1, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (18, 0, 'READER_TABLE', 1, 'RULE_DECISOR_PARAM', 'Byte', '', null, null, '', 1, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (38, 0, 'READER_TABLE', 4, 'WHERE', 'String', 'where CONTRATO is not null and trunc(fecha_alta) >= trunc(add_months(sysdate, ${mesesAnteriores}))', null, null, '', 3, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (37, 0, 'READER_TABLE', 3, 'FROM', 'String', 'from CONELE.V_SOLICITUD_IICE', null, null, '', 3, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (40, 0, 'READER_TABLE', 6, 'PAGE_SIZE', 'Long', '', 100, null, '', 3, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (39, 0, 'READER_TABLE', 5, 'SORT', 'String', 'fecha_alta', null, null, '', 3, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (32, 0, 'READER_TABLE', 1, 'RULE_PARAM', 'Byte', '', null, null, '', 3, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (34, 0, 'READER_TABLE', 1, 'RULE_DECISOR_PARAM', 'Byte', '', null, null, '', 3, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (33, 0, 'READER_TABLE', 1, 'RULE_JNDI', 'String', 'jdbc/BBVA', null, null, '', 3, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (35, 0, 'READER_TABLE', 1, 'JNDI', 'String', 'jdbc/BBVA', null, null, '', 3, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (36, 0, 'READER_TABLE', 2, 'SELECT', 'String', 'select lpad(trim(SOLICITUD), 10, ''0'') ,PRODUCTO_PACK ,SUBPRODUCTO_PACK ,ESTADO_PACK ,TO_CHAR(FECHA_ALTA, ''YYYY-MM-DD'') FECHA_ALTA ,lpad(trim(TRUNC(ROUND(IMPORTE, 2) * 100, 0)), 15, ''0'') IMPORTE ,lpad(trim(DIVISA), 3, ''0'') ,TIPODOCUMENTO_PACK ,NUM_DOI ,CODIGO_CLIENTE ,CONTRATO ,lpad(trim(PLAZO), 3, ''0'') ,OFICINA ,EJECUTIVO ,lpad(trim(TRUNC(ROUND(TASA, 2) * 100, 0)), 5, ''0'') TASA', null, null, '', 3, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (5, 0, 'READER_XML', 3, 'RULE', 'Byte', '', null, null, '', 5, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (3, 0, 'READER_XML', 1, 'WSDL', 'Byte', '', null, null, '', 5, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (4, 0, 'READER_XML', 2, 'WSDL_OPERATION', 'String', 'listarOficinaTerritorioSuprarea', null, null, '', 5, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (8, 0, 'READER_XML', 1, 'WSDL', 'Byte', '', null, null, '', 6, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (9, 0, 'READER_XML', 2, 'WSDL_OPERATION', 'String', 'listarOficinaTerritorioSuprarea', null, null, '', 6, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (10, 0, 'READER_XML', 3, 'RULE', 'Byte', '', null, null, '', 6, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (14, 1, 'WRITER_DECISOR', 2, 'DECISOR_PARAM', 'Byte', '', null, null, '', 2, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (13, 1, 'WRITER_DECISOR', 1, 'JNDI', 'String', 'jdbc/BBVA', null, null, '', 2, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (15, 1, 'WRITER_DECISOR', 3, 'RULE', 'Byte', '', null, null, '', 2, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (2, 0, 'WRITER_QUERY', 2, 'JNDI', 'String', 'jdbc/BBVA', null, null, '', 4, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (1, 0, 'WRITER_QUERY', 1, 'QUERY', 'String', 'UPDATE CONELE.TBL_CE_IBM_TERRITORIO SET FLAG_ACTIVO=0', null, null, '', 4, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (6, 0, 'WRITER_TABLE', 1, 'QUERY', 'String', 'MERGE INTO CONELE.TBL_CE_IBM_TERRITORIO A USING (   SELECT :listaOficina.territorio.id ID, :listaOficina.territorio.nombreTerritorio NOMBRE   FROM DUAL) B ON (A.CODIGO = B.ID) WHEN MATCHED THEN   UPDATE SET A.DESCRIPCION = B.NOMBRE,          A.UBICACION = B.NOMBRE,          A.FLAG_ACTIVO = 1 WHEN NOT MATCHED THEN   INSERT (ID, CODIGO, DESCRIPCION, UBICACION, FLAG_PROV, FLAG_ACTIVO)   VALUES (CONELE.SEQ_CE_IBM_TERRITORIO.NEXTVAL, B.ID, B.NOMBRE, B.NOMBRE, 0, 1) ', null, null, '', 5, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (7, 0, 'WRITER_TABLE', 2, 'JNDI', 'String', 'jdbc/BBVA', null, null, '', 5, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (11, 0, 'WRITER_TABLE', 1, 'QUERY', 'String', 'MERGE INTO CONELE.TBL_CE_IBM_OFICINA A USING (   SELECT (SELECT ID FROM CONELE.TBL_CE_IBM_TERRITORIO WHERE CODIGO = :listaOficina.territorio.id) ID_TERRITORIO    , :listaOficina.id ID   , :listaOficina.nombreOficina NOMBRE_OFICINA   FROM DUAL) B ON (A.CODIGO = B.ID) WHEN MATCHED THEN   UPDATE SET A.DESCRIPCION = B.NOMBRE_OFICINA,          A.ID_TERRITORIO_FK = B.ID_TERRITORIO,          A.FLAG_ACTIVO = 1 WHEN NOT MATCHED THEN   INSERT (ID, CODIGO, DESCRIPCION, ID_TERRITORIO_FK, ID_OFICINA_PRINCIPAL_FK, FLAG_ACTIVO)   VALUES (CONELE.SEQ_CE_IBM_OFICINA.NEXTVAL, B.ID, B.NOMBRE_OFICINA, B.ID_TERRITORIO, NULL, 1)', null, null, '', 6, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (12, 0, 'WRITER_TABLE', 2, 'JNDI', 'String', 'jdbc/BBVA', null, null, '', 6, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (30, 0, 'WRITER_TEXT_POSITION', 3, 'FORMAT', 'String', '%-10s%-2s%-4s%-1s%-10s%-15s%-3s%-1s%-11s%-8s%-23s%-3s%-4s%-8s%-5s', null, null, '', 1, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (31, 0, 'WRITER_TEXT_POSITION', 4, 'APPEND', 'String', 'False', null, null, '', 1, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (25, 0, 'WRITER_TEXT_POSITION', 1, 'RULE_PARAM', 'Byte', '', null, null, '', 1, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (29, 0, 'WRITER_TEXT_POSITION', 2, 'FIELDS', 'String', 'SOLICITUD,PRODUCTO_PACK,SUBPRODUCTO_PACK,ESTADO_PACK,FECHA_ALTA,IMPORTE,DIVISA,TIPODOCUMENTO_PACK,NUM_DOI,CODIGO_CLIENTE,CONTRATO,PLAZO,OFICINA,EJECUTIVO,TASA', null, null, '', 1, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (26, 0, 'WRITER_TEXT_POSITION', 1, 'RULE_JNDI', 'String', 'jdbc/BBVA', null, null, '', 1, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (28, 0, 'WRITER_TEXT_POSITION', 1, 'RESOURCE', 'String', '/mnt/compartido/conele/out/packDEMO.txt', null, null, '', 1, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (27, 0, 'WRITER_TEXT_POSITION', 1, 'RULE_DECISOR_PARAM', 'Byte', '', null, null, '', 1, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (42, 0, 'WRITER_TEXT_POSITION', 1, 'RULE_JNDI', 'String', 'jdbc/BBVA', null, null, '', 3, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (43, 0, 'WRITER_TEXT_POSITION', 1, 'RULE_DECISOR_PARAM', 'Byte', '', null, null, '', 3, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (44, 0, 'WRITER_TEXT_POSITION', 1, 'RESOURCE', 'String', '/mnt/compartido/conele/out/packDEMO.txt', null, null, '', 3, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (41, 0, 'WRITER_TEXT_POSITION', 1, 'RULE_PARAM', 'Byte', '', null, null, '', 3, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (47, 0, 'WRITER_TEXT_POSITION', 4, 'APPEND', 'String', 'True', null, null, '', 3, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (46, 0, 'WRITER_TEXT_POSITION', 3, 'FORMAT', 'String', '%-10s%-2s%-4s%-1s%-10s%-15s%-3s%-1s%-11s%-8s%-23s%-3s%-4s%-8s%-5s', null, null, '', 3, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (45, 0, 'WRITER_TEXT_POSITION', 2, 'FIELDS', 'String', 'SOLICITUD,PRODUCTO_PACK,SUBPRODUCTO_PACK,ESTADO_PACK,FECHA_ALTA,IMPORTE,DIVISA,TIPODOCUMENTO_PACK,NUM_DOI,CODIGO_CLIENTE,CONTRATO,PLAZO,OFICINA,EJECUTIVO,TASA', null, null, '', 3, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (48, 0, 'WRITER_DROOLS', 1, 'JNDI', 'String', 'jdbc/BBVA', null, null, '', 7, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (49, 0, 'WRITER_DROOLS', 2, 'DECISOR_PARAM', 'Byte', '', null, null, '', 7, NULL);

insert into CONELE.MNTR_PARAMETRO (ID, VERSION, NOMBRE, ORDEN, TIPO, TIPO_ATRIBUTO, CADENA, ENTERO, FLOTANTE, FECHA, ID_PASO, BINARIO)
values (50, 0, 'WRITER_DROOLS', 3, 'RULE', 'Byte', '', null, null, '', 7, NULL);
commit;

spool off
