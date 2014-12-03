spool 4_createView.log

CREATE OR REPLACE VIEW CONELE.V_SOLICITUD AS
SELECT EXP.ID AS SOLICITUD
  , EXP.ID_PRODUCTO_FK AS CODIGO_PRODUCTO
  , ETC.ID_SUBPRODUCTO_FK AS CODIGO_SUBPRODUCTO
  , EXP.ID_ESTADO_FK AS ESTADO
  , EXP.FECHA_ENVIO AS FECHA_ALTA
  , ETC.LINEA_CRED_SOL AS IMPORTE
  , ETC.ID_TIPMONSOL_FK AS DIVISA
  , CLI.ID_TIPO_DOI_FK AS TIPO_DOI
  , CLI.NUM_DOI AS NUM_DOI
  , CLI.COD_CLIENTE AS CODIGO_CLIENTE
  , ETC.NRO_CONTRATO AS CONTRATO
  , ETC.PLAZO_SOL AS PLAZO
  , ETC.ID_OFICINA_FK AS OFICINA
  , OFI.CODIGO AS OFICINA_CODIGO
  ,EXP.ID_EMPLEADO_FK AS EJECUTIVO
  , EMP.CODIGO AS EJECUTIVO_CODIGO
  , ETC.TASA_ESP AS TASA
FROM CONELE.TBL_CE_IBM_EXPEDIENTE EXP
  , CONELE.TBL_CE_IBM_EXPEDIENTE_TC ETC
  , CONELE.TBL_CE_IBM_CLIENTE_NATURAL CLI
  , CONELE.TBL_CE_IBM_OFICINA OFI
  , CONELE.TBL_CE_IBM_EMPLEADO EMP
WHERE EXP.ID = ETC.ID_EXP_FK 
  AND CLI.ID = EXP.ID_CLIENTE_NATURAL_FK
  AND ETC.ID_OFICINA_FK = OFI.ID(+) 
  AND EXP.ID_EMPLEADO_FK = EMP.ID(+);


CREATE OR REPLACE VIEW CONELE.V_SOLICITUD_IICE AS
SELECT EXP.CORRELATIVO_EXPEDIENTE AS SOLICITUD
  , EXP.CORRELATIVO_PRODUCTO AS CODIGO_PRODUCTO
  , EXP.CORRELATIVO_SUB_PRODUCTO AS CODIGO_SUBPRODUCTO
  , EXP.CORRELATIVO_ESTADO AS ESTADO
  , EXP.FECHA_CREACION AS FECHA_ALTA
  , EXP.IMPORTE_SOLICITADO_PRESTAMO AS IMPORTE
  , EXP.CORRELATIVO_MONEDA AS DIVISA
  , CLI.CORRELATIVO_TIPO_DOC_IDENTIDAD AS TIPO_DOI
  , CLI.DOI AS NUM_DOI
  , EXP.CORRELATIVO_CLIENTE AS CODIGO_CLIENTE
  , EXP.CODIGO_CONTRATO AS CONTRATO
  , EXP.PLAZO_SOLICITADO AS PLAZO
  , EXP.CORRELATIVO_OFICINA AS OFICINA
  , OFI.CODIGO AS OFICINA_CODIGO
  , EXP.CODIGO_USUARIO_CREACION AS EJECUTIVO
  , EMP.CODIGO AS EJECUTIVO_CODIGO
  , EXP.TASA_ESPECIAL AS TASA
FROM IICE.TBL_EXPEDIENTE EXP, IICE.TBL_CLIENTE CLI, IICE.TBL_OFICINA OFI, IICE.TBL_EMPLEADO EMP
WHERE CLI.CORRELATIVO_CLIENTE = EXP.CORRELATIVO_CLIENTE
  AND EXP.CORRELATIVO_OFICINA = OFI.CORRELATIVO_OFICINA(+)
  AND EXP.CODIGO_USUARIO_CREACION = EMP.CODIGO(+);
  
spool off
