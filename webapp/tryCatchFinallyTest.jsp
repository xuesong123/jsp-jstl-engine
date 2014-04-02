<%@ page contentType="text/html; charset=UTF-8" buffer="8192" autoFlush="true"%>
<c:execute value="${pageContext.getOut().setBufferSize(4096)}"/>
<c:execute value="${pageContext.getOut().setAutoFlush(true)}"/>
<!--
功能测试
    该测试仅针对解释模式， 编译模式不会存在这个问题。
    测试项目:
        1. 当Tag的doStartTag或者doEndTag返回Tag.SKIP_PAGE时, 父Tag如果是TryCatchFinally标签, 是否能够执行父Tag的doFinally
        2. 如果父Tag的doFinally方法在执行的时候抛出了异常，那么整个执行过程是否能够正常终止。
        3. 如果父Tag的doFinally方法在执行的时候抛出了异常，显示出来的错误信息是否正确。错误信息应该定位到TryCatchFinally标签上，而不应该定位到返回Tag.SKIP_PAGE的标签上。
-->
<c:tryCatchTest exception="doFinally">
    <c:exit/>
</c:tryCatchTest>