@echo off

@SET ANT_HOME=D:\apache-ant-1.8.0
@SET PATH=%PATH%;%ANT_HOME%\bin
ant "-buildfile" "build.xml"
pause
