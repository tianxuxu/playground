@ECHO OFF
call env.bat

set PR_INSTALL=%~dp0prunsrv.exe

REM Service log configuration
set PR_LOGPREFIX=%SERVICE_NAME%
set PR_LOGPATH=%~dp0log
set PR_STDOUTPUT=%~dp0log\service_stdout.txt
set PR_STDERROR=%~dp0log\service_stderr.txt
set PR_LOGLEVEL=Error

REM Path to java installation
set PR_JVM=%~dp0jre\bin\server\jvm.dll
set PR_CLASSPATH=%~dp0maildeleter.jar

REM Startup configuration
set PR_STARTUP=auto
set PR_STARTMODE=jvm
set PR_STARTCLASS=ch.rasc.maildeleter.Main
set PR_STARTMETHOD=main

REM Shutdown configuration
set PR_STOPMODE=jvm
set PR_STOPCLASS=ch.rasc.maildeleter.Main
set PR_STOPMETHOD=stop

prunsrv.exe //IS//%SERVICE_NAME%