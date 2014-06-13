@echo off
@SET ANT_HOME=C:\apache-ant-1.8.0
@SET PATH=%PATH%;%ANT_HOME%\bin
call ant "-buildfile" "build.xml"
pause
