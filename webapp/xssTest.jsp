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
