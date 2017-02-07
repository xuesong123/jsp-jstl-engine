<%@ page contentType="text/html; charset=UTF-8" buffer="8192" autoFlush="true"%>
<!--
ayada允许以静默模式执行el表达式
当el表达式执行失败时, 通常情况下是因为引用的对象出现了空值, 例如下面的示例:
ignoreElException变量用来通知el引擎当发生这种情况时是否抛出异常
true: 不抛异常
false: 抛出异常

该变量可以在任何时候任何位置设置, 即时生效.
所以它可以配置在全局配置文件中, 也可以在servlet中设置到request中, 也可以在页面上临时设置
-->
<c:set var="ignoreElException" value="true"/>
${aaa.bbb.ccc}

<c:set var="ignoreElException" value="true"/>
${aaa.bbb.ccc}
