<c:map name="user">
    <c:entry name="input">";
alert(1);
// </c:entry>
</c:map>
<script type="text/javascript">
// xss
var a = "${user.input}";
</script>

<script type="text/javascript">
// xss filter
var a = "${StringUtil.escape(user.input)}";
</script>

<x:escape encoder="html">
<p>${user.input}</p>
</x:escape>

<x:escape encoder="code">
<script type="text/javascript">
var a = "${user.input}";
</script>
</x:escape>

<c:setEncoder encoder="html">
<p>${user.input}</p>
</c:setEncoder>

<p>定义两个测试数据，这两个数据可能来源于用户输入。</p>
<c:set var="myHtmlContent"><h1>Hello !</h1></c:set>
<c:set var="myTextContent">
";
// 下面是注入的代码
// hello
// 发送cookie到指定邮箱
alert(document.cookie);
var a = "test hello
</c:set>

<p>对该标签内的所有el表达式使用html编码：</p>
<c:setEncoder encoder="html">
<p>${myHtmlContent}</p>
</c:setEncoder>

<p>以下的el表达式使用html编码：</p>
<script type="text/javascript">
var text = "${myTextContent}";
alert(text);
</script>

<p>以下的el表达式不做任何编码：</p>
<c:setEncoder encoder="null">
<script type="text/javascript">
var text = "${myTextContent}";
alert(text);
</script>
</c:setEncoder>

<p>对以下的el表达式转义：</p>
<c:setEncoder encoder="code">
<script type="text/javascript">
var text = "${myTextContent}";
alert(text);
</script>
</c:setEncoder>

