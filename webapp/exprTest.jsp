<c:escape encoder="xml"/>
<c:set var="myString" value="<h1>test</h1>"/>
expr: ${?myString}
text: ${#myString}
html: ${&myString}
html: ${myString}
<c:escape encoder="null"/>
text: ${myString}

${?}
${#}
${&}
