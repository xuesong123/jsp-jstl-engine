<%@ page contentType="text/html; charset=UTF-8"%>
<c:set var="appName" value="ayada"/>
<c:set var="version" value="1.0.0"/>
<c:set var="basedir" value="E:\WorkSpace\ayada"/>
<c:set var="webapp"  value="${basedir}\webapp"/>
<c:set var="classes" value="${basedir}\build\classes"/>

<b:copy-class src="${classes}" target="${basedir}\build\version\${Version}\ayada-${version}\WEB-INF\classes">
    com.skin.ayada.template.DefaultExecutor
    com.skin.ayada.template.Template
</b:copy-class>

<b:copy src="${webapp}" target="${basedir}\build\version\${Version}\ayada-${version}">
    /include/common.jsp
    /include/footer.jsp
    /include/header.jsp
    /include/static.jsp
</b:copy>