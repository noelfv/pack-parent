@echo off
@echo off
@echo [INFO] ------------------------------------------------------------------------
@echo [INFO] Begin process compile Maven
@echo [INFO] ------------------------------------------------------------------------
call mvn -P devWAS clean install -Dmaven.test.skip=true
@echo [INFO] ------------------------------------------------------------------------
@echo [INFO] End process compile Maven
@echo [INFO] ------------------------------------------------------------------------

@echo [INFO] ------------------------------------------------------------------------
@echo [INFO] Begin process install Websphere
@echo [INFO] ------------------------------------------------------------------------
call C:\IBM\WebSphere8.5\AppServer\profiles\AppSrv02\bin\wsadmin.bat -user admin -password admin -lang jython -f wsadmin-install.py
@echo [INFO] ------------------------------------------------------------------------
@echo [INFO] End process install Websphere
@echo [INFO] ------------------------------------------------------------------------

@echo [INFO] ------------------------------------------------------------------------
@echo [INFO] Begin process valid code
@echo [INFO] ------------------------------------------------------------------------
call mvn cobertura:clean cobertura:cobertura sonar:sonar
@echo [INFO] ------------------------------------------------------------------------
@echo [INFO] End process valid code
@echo [INFO] ------------------------------------------------------------------------
