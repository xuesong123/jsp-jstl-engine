<c:set var="var1" value="2"/>
<c:set var="var2" value="5"/>
<s:if test="${var1 == 1}">
    <h1>var1 == 1</h1>
    <s:if test="${var2 == 1}">
        <h1>var2 == 1</h1>
    </s:if>
    <s:elseif test="${var2 == 2}">
        <h1>var2 == 2</h1>
    </s:elseif>
    <s:else>
        <h1>var2 == ?</h1>
    </s:else>
</s:if>
## 11111
<s:elseif test="${var1 == 2}">
    <h1>var1 == 2</h1>
    <s:if test="${var2 == 1}">
        <h1>var2 == 1</h1>
    </s:if>
    <s:elseif test="${var2 == 2}">
        <h1>var2 == 2</h1>
    </s:elseif>
    <s:else>
        <h1>var2 == ?</h1>
    </s:else>
</s:elseif>
## 22222
<s:else>
    <h1>var1 == ?</h1>
    <s:if test="${var2 == 1}">
        <h1>var2 == 1</h1>
    </s:if>
    <s:elseif test="${var2 == 2}">
        <h1>var2 == 2</h1>
    </s:elseif>
    <s:else>
        <h1>var2 == ?</h1>
    </s:else>
</s:else>

<t:text>
    <s:if test="${1 == 1}">
        # 1
    <s:elseif test="${1 == 1}">
        # 2
    <s:elseif test="${1 == 1}">
        # 3
    <s:else>
        # 4
    </s:if>
</t:text>


