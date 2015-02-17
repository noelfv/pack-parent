@echo off
@echo [INFO] ------------------------------------------------------------------------
@echo [INFO] Begin process install Websphere
@echo [INFO] ------------------------------------------------------------------------
call C:\IBM\WebSphere8.5\AppServer\profiles\AppSrv02\bin\wsadmin.bat -user admin -password admin -lang jython -f %CD%\wsadmin-install.py
@echo [INFO] ------------------------------------------------------------------------
@echo [INFO] End process install Websphere
@echo [INFO] ------------------------------------------------------------------------