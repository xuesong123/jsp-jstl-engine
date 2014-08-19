<c:escape escapeXml="true"/>
<c:set var="myString" value="&lt;test&gt;test&lt;/test&gt;"/>
<c:set var="myString" value="<h1>test</h1>"/>
${myString}
<c:escape escapeXml="false"/>
${myString}
