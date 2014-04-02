[NODE]: <c:execute lineNumber="2" offset="0" length="2" tagClass="com.skin.ayada.jstl.core.ExecuteTag" tagFactory="com.skin.ayada.jstl.factory.ExecuteTagFactory" value="${pageContext.getOut().setBufferSize(4096)}">
[NODE]: </c:execute>
[NODE]: <c:execute lineNumber="3" offset="2" length="2" tagClass="com.skin.ayada.jstl.core.ExecuteTag" tagFactory="com.skin.ayada.jstl.factory.ExecuteTagFactory" value="${pageContext.getOut().setAutoFlush(true)}">
[NODE]: </c:execute>
[TEXT]: <!--\r\n功能测试\r\n    该测试仅针对解释模式， 编译模式不会存在这个问题。\r\n    测试项目:\r\n        1. 当Tag的doStartTag或者doEndTag返回Tag.SKIP_PAGE时, 父Tag如果是TryCatchFinally标签, 是否能够执行父Tag的doFinally\r\n        2. 如果父Tag的doFinally方法在执行的时候抛出了异常，那么整个执行过程是否能够正常终止。\r\n        3. 如果父Tag的doFinally方法在执行的时候抛出了异常，显示出来的错误信息是否正确。错误信息应该定位到TryCatchFinally标签上，而不应该定位到返回Tag.SKIP_PAGE的标签上。\r\n-->\r\n
[NODE]: <c:tryCatchTest lineNumber="12" offset="5" length="6" tagClass="com.skin.ayada.jstl.core.TryCatchTestTag" tagFactory="com.skin.ayada.jstl.factory.TryCatchTestTagFactory" exception="doFinally">
[TEXT]: \r\n    
[NODE]: <c:exit lineNumber="13" offset="7" length="2" tagClass="com.skin.ayada.jstl.core.ExitTag" tagFactory="com.skin.ayada.jstl.factory.ExitTagFactory">
[NODE]: </c:exit>
[TEXT]: \r\n
[NODE]: </c:tryCatchTest>
