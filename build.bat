@ECHO OFF
@IF exist "D:\MyApp\bin\setenv.bat" call D:\MyApp\bin\setenv.bat
@IF exist "C:\apache-ant-1.8.0" @SET ANT_HOME=C:\apache-ant-1.8.0
@IF exist "D:\apache-ant-1.8.0" @SET ANT_HOME=D:\apache-ant-1.8.0

@SET PATH=%PATH%;%ANT_HOME%\bin
call ant "-buildfile" "build.xml"
pause
