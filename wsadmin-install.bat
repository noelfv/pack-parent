@echo off
rem cd C:\jquedena\Proyectos\workspace-scorating-old\scorating-d99f0d348df92eda80555e6f3d2beb039543b304
rem echo "Compilando...."
rem mvn clean install -Dmaven.test.skip=true
echo "Instalando...."
C:\IBM\WebSphere8.5\AppServer\profiles\AppSrv02\bin\wsadmin.bat -user admin -password admin -lang jython -f wsadmin-install.py
