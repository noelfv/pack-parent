prompt PL/SQL Developer import file
prompt Created on miércoles, 03 de diciembre de 2014 by jquedena
set feedback off
set define off
prompt Disabling triggers for conele.TBL_CE_IBM_MULTITABLA...
alter table conele.TBL_CE_IBM_MULTITABLA disable all triggers;
prompt Disabling foreign key constraints for conele.TBL_CE_IBM_MULTITABLA...
alter table conele.TBL_CE_IBM_MULTITABLA disable constraint FK_PARAMETRIA_PARAMETRIA_1;
prompt Deleting conele.TBL_CE_IBM_MULTITABLA...
delete from conele.TBL_CE_IBM_MULTITABLA;
commit;
prompt Loading conele.TBL_CE_IBM_MULTITABLA...
prompt Table is empty
prompt Enabling foreign key constraints for conele.TBL_CE_IBM_MULTITABLA...
alter table conele.TBL_CE_IBM_MULTITABLA enable constraint FK_PARAMETRIA_PARAMETRIA_1;
prompt Enabling triggers for conele.TBL_CE_IBM_MULTITABLA...
alter table conele.TBL_CE_IBM_MULTITABLA enable all triggers;
set feedback on
set define on
prompt Done.
