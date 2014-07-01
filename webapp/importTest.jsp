<%@ page contentType="text/html;charset=UTF-8"%>
<t:import name="app:bodytest" className="test.com.skin.ayada.taglib.TestBodyTag"/>
<app:bodytest>Hello World !</app:bodytest>
<c:out value="a${1}c"/>
<c:out value="a${?1}c"/>
