spool 05_createTable.log

CREATE TABLE CONELE.TBL_CE_IBM_MULTITABLA (
  ID NUMBER(10,0) NOT NULL ENABLE, 
  ID_PARAMETRIA NUMBER(10,0), 
  TIPO NUMBER(10,0) NOT NULL ENABLE, 
  NOMBRE VARCHAR2(300 CHAR) NOT NULL ENABLE, 
  DESCRIPCION VARCHAR2(500 CHAR), 
  ESTADO CHAR(1 CHAR) DEFAULT 'A' NOT NULL ENABLE, 
  CODIGO VARCHAR2(50 BYTE), 
  CODIGO_ETI VARCHAR2(100 BYTE), 
  CODIGO_HABIL CHAR(1 BYTE) DEFAULT 'N' NOT NULL ENABLE, 
  ENTERO NUMBER(10,0), 
  ENTERO_ETI VARCHAR2(100 CHAR), 
  ENTERO_HABIL CHAR(1 BYTE) DEFAULT 'N' NOT NULL ENABLE, 
  DECIMALES NUMBER, 
  DECIMALES_ETI VARCHAR2(100 CHAR), 
  DECIMALES_HABIL CHAR(1 BYTE) DEFAULT 'N' NOT NULL ENABLE, 
  TEXTO VARCHAR2(500 CHAR), 
  TEXTO_ETI VARCHAR2(100 CHAR), 
  TEXTO_HABIL CHAR(1 BYTE) DEFAULT 'N' NOT NULL ENABLE, 
  FECHA DATE, 
  FECHA_ETI VARCHAR2(100 CHAR), 
  FECHA_HABIL CHAR(1 BYTE) DEFAULT 'N' NOT NULL ENABLE, 
  HORA CHAR(5 CHAR), 
  HORA_ETI VARCHAR2(100 BYTE), 
  HORA_HABIL CHAR(1 BYTE) DEFAULT 'N' NOT NULL ENABLE, 
  BOOLEANO CHAR(1 BYTE), 
  BOOLEANO_ETI VARCHAR2(100 CHAR), 
  BOOLEANO_HABIL CHAR(1 BYTE) DEFAULT 'N' NOT NULL ENABLE, 
  FUNCION VARCHAR2(100 CHAR), 
  FUNCION_ETI VARCHAR2(100 BYTE), 
  FUNCION_HABIL CHAR(1 BYTE) DEFAULT 'N' NOT NULL ENABLE, 
  FUNCION_MSG VARCHAR2(200 CHAR), 
  PERMITE_HIJO CHAR(1 BYTE), 
  FECHA_MODIFICA DATE, 
  FECHA_CREA DATE NOT NULL ENABLE, 
  USU_CREA CHAR(7 CHAR) NOT NULL ENABLE, 
  USU_MODIFICA CHAR(7 CHAR)
)TABLESPACE CONELE_DAT; 
ALTER TABLE CONELE.TBL_CE_IBM_MULTITABLA ADD CONSTRAINT PK_PARAMETRIA PRIMARY KEY (ID)  USING INDEX  TABLESPACE "CONELE_IDX";
ALTER TABLE CONELE.TBL_CE_IBM_MULTITABLA ADD CONSTRAINT FK_PARAMETRIA_PARAMETRIA_1 FOREIGN KEY (ID_PARAMETRIA) REFERENCES CONELE.TBL_CE_IBM_MULTITABLA (ID);

GRANT SELECT, INSERT, UPDATE, DELETE ON CONELE.TBL_CE_IBM_MULTITABLA TO APP_CONELE;



--=====================================================--
-- Monitor de Procesos por Lotes 
--=====================================================--
CREATE TABLE CONELE.MNTR_APLICACION (
    ID NUMERIC(10, 0) NOT NULL
  , VERSION NUMERIC(10, 0)
  , NOMBRE VARCHAR2(30)
  , JNDI VARCHAR2(30)
  , DESCRIPCION VARCHAR2(2000)
) TABLESPACE CONELE_DAT;

CREATE TABLE CONELE.MNTR_TRABAJO (
    ID NUMERIC(10, 0) NOT NULL
  , VERSION NUMERIC(10, 0)
  , NOMBRE VARCHAR2(30)
  , DESCRIPCION VARCHAR2(2000)
  , CRON VARCHAR2(30)
  , TIPO VARCHAR2(30)
  , ID_APLICACION NUMERIC(10, 0) NOT NULL
) TABLESPACE CONELE_DAT;

CREATE TABLE CONELE.MNTR_PASO (
    ID NUMERIC(10, 0) NOT NULL
  , VERSION NUMERIC(10, 0)
  , NOMBRE VARCHAR2(30)
  , DESCRIPCION VARCHAR2(2000)
  , ORDEN NUMERIC(10, 0)
  , LECTOR VARCHAR2(30)
  , ESCRITOR VARCHAR2(30)
  , SIG_PASO VARCHAR2(2000)
  , ID_TRABAJO NUMERIC(10, 0) NOT NULL
  ) TABLESPACE CONELE_DAT;

CREATE TABLE CONELE.MNTR_PARAMETRO (
    ID NUMERIC(10, 0) NOT NULL
  , VERSION NUMERIC(10, 0)
  , NOMBRE VARCHAR2(30)
  , ORDEN NUMERIC(10, 0)
  , TIPO VARCHAR2(30)
  , TIPO_ATRIBUTO VARCHAR2(30)
  , CADENA VARCHAR2(2000)
  , ENTERO NUMERIC(10, 0)
  , FLOTANTE NUMERIC(25, 8)
  , BINARIO BLOB
  , FECHA TIMESTAMP
  , ID_PASO NUMERIC(10, 0) NOT NULL
) TABLESPACE CONELE_DAT;

ALTER TABLE CONELE.MNTR_APLICACION ADD CONSTRAINT PK_APLICACION PRIMARY KEY (ID) USING INDEX TABLESPACE CONELE_IDX;
ALTER TABLE CONELE.MNTR_TRABAJO ADD CONSTRAINT PK_TRABAJO PRIMARY KEY (ID) USING INDEX TABLESPACE CONELE_IDX;
ALTER TABLE CONELE.MNTR_PASO ADD CONSTRAINT PK_PASO PRIMARY KEY (ID) USING INDEX TABLESPACE CONELE_IDX;
ALTER TABLE CONELE.MNTR_PARAMETRO ADD CONSTRAINT PK_PARAMETRO PRIMARY KEY (ID) USING INDEX TABLESPACE CONELE_IDX;

ALTER TABLE CONELE.MNTR_TRABAJO ADD CONSTRAINT FK_TRABAJO_APLICACION FOREIGN KEY (ID_APLICACION) REFERENCES CONELE.MNTR_APLICACION(ID);
ALTER TABLE CONELE.MNTR_PASO ADD CONSTRAINT FK_PASO_TRABAJO FOREIGN KEY (ID_TRABAJO) REFERENCES CONELE.MNTR_TRABAJO(ID);
ALTER TABLE CONELE.MNTR_PARAMETRO ADD CONSTRAINT FK_PARAMETRO_PASO FOREIGN KEY (ID_PASO) REFERENCES CONELE.MNTR_PASO(ID);

GRANT SELECT, INSERT, UPDATE, DELETE ON CONELE.MNTR_APLICACION TO APP_CONELE;
GRANT SELECT, INSERT, UPDATE, DELETE ON CONELE.MNTR_TRABAJO TO APP_CONELE;
GRANT SELECT, INSERT, UPDATE, DELETE ON CONELE.MNTR_PASO TO APP_CONELE;
GRANT SELECT, INSERT, UPDATE, DELETE ON CONELE.MNTR_PARAMETRO TO APP_CONELE;
spool off
