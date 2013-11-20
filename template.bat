@ECHO OFF

@ECHO.
@ECHO Ayada-Template version 1.0.0
@ECHO (c) 2005-2008 xuesong.net
@ECHO.
@ECHO For details, See the Ayada-Template web site: https://www.github.com/xuesong123
@ECHO.

cd /d "%~dp0"

@SET APP_HOME=%~dp0
@SET APP_NAME=%~nx0

@SET LIB_HOME=.

@SET APP_JARS=%LIB_HOME%\build\classes
@SET JRE_JARS=%JAVA_HOME%\lib\dt.jar;%JAVA_HOME%\lib\tools.jar;%JAVA_HOME%\jre\lib\rt.jar

FOR /R %LIB_HOME%\lib %%n in (*.jar) do call :SET_APP_JARS %%n

GOTO :Main

:SET_APP_JARS
set APP_JARS=%APP_JARS%;%~1
GOTO :EOF

:Main

@ECHO APP_HOME: %APP_HOME%
@ECHO USER_LIB: %LIB_HOME%\lib
@ECHO JRE_JARS: %JRE_JARS%
@ECHO APP_JARS: %APP_JARS%
@ECHO.

@REM jar cvfe 1.jar demo.server.http.file.Main *
%JAVA_HOME%\bin\java.exe -cp .;%JRE_JARS%;%APP_JARS% com.skin.ayada.template.Main "%1" "UTF-8"
:end
@REM pause
