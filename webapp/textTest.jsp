<h1>文本转义</h1>
<p>文本转义的方式有两种:</p>

<p>文本转义: 第一种方式 - 编译期根据escape把内容进行编码</p>
<textarea id="mytpl">
<t:text escape="xml">
    <div><c:if test="${1 == 1}">test</c:if></div>
</t:text>
</textarea>

<p>文本转义: 第二种方式 - 运行期转义</p>
<textarea id="mytpl">
    <c:out escapeXml="true">
        <t:text>
            <div><c:if test="${1 == 1}">test</c:if></div>
        </t:text>
    </c:out>
</textarea>
<p>上面的两种方式执行结果是一样的，但是第一种方式更好一些，第一种方式是在编译期进行转义，只需要转义一次。</p>
<p>第二种方式每次执行的时候都会执行一次转义。</p>
<p>两种方式都只能是静态文本</p>

t:text标签是编译指令，在编译期其中的任何内容都会被当作文本处理，包括标签和java脚本，并且内容中不允许包含&lt;/t:text&gt;
这个标签是专门为使用了js模板设计的, 例如:
<textarea id="mytpl">
<t:text escape="xml">
    <div><c:if test="${1 == 1}">test</c:if></div>
</t:text>
</textarea>

<script type="text/javascript">
function getTemplate(id){
    return document.getElementById(id).value;
}
</script>
