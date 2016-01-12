@ECHO OFF
rd /s /q target
@IF exist "C:\apache-maven-2.2.1" @SET MVN_HOME=C:\apache-maven-2.2.1
@IF exist "D:\apache-maven-3.2.2" @SET MVN_HOME=D:\apache-maven-3.2.2

@SET PATH=%PATH%;%MVN_HOME%\bin
call mvn clean package -Dmaven.test.skip=true -DfailIfNoTests=false
@pause
