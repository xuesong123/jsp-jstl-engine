Ayada是一个java版的模板引擎, 支持jstl语法, 支持el表达式. 支持自定义标签

1. 基本使用
// webapp代表应用根目录
// 如果页面使用了t:include指令, 则根目录必须指定
// TemplateContext对模板进行管理, 默认情况下模板在修改5分钟之后重新编译,
// 因此TemplateContext对象在应用中应该是单粒.
TemplateContext templateContext = new TemplateContext("webapp");
Template template = templateContext.getTemplate("/user/userList.tml");
StringWriter writer = new StringWriter();
PageContext pageContext = JspFactory.getPageContext(templateContext, writer);
DefaultExecutor.execute(template, pageContext);

System.out.println(writer.toString());

2. 自定义标签
在classes目录下建taglib.tld文件, 内容如下:
## ---- compile tag ----
t:taglib       com.skin.ayada.jstl.core.LibTag

## ---- jstl.core ----
## default tag support
c:if           com.skin.ayada.jstl.core.IfTag
c:set          com.skin.ayada.jstl.core.SetTag
c:out          com.skin.ayada.jstl.core.OutTag
c:each         com.skin.ayada.jstl.core.ForEachTag
c:forEach      com.skin.ayada.jstl.core.ForEachTag
c:choose       com.skin.ayada.jstl.core.ChooseTag
c:when         com.skin.ayada.jstl.core.WhenTag
c:otherwise    com.skin.ayada.jstl.core.OtherwiseTag
c:comment      com.skin.ayada.jstl.core.CommentTag

按照该格式添加自定义标签即可.

自定义标签支持两种引入方式.
全局引入: 在taglib.tld文件中定义的都是全局标签
局部引入: 在页面中引入, 使用<t:taglib name="my:hello" className="test.tag.HelloTag"/>
          局部引入的方式要求必须全局引入t:taglib(t:taglib可以在taglib.tld文件中修改为其他名字)

建议使用全局引入的方式.

3. el表达式
    el表达式解析采用的是ognl, ognl在对不存在的对象进行解析的时候会抛异常, 为了避免在页面中有过多的null检查
    因此对这种异常进行了屏蔽

4. 模板页面示例
------------------------------------------------------------
<t:import name="my:hello1" className="test.tag.HelloTag1"/>
<t:import name="my:hello2" className="test.tag.HelloTag2"/>
<t:import name="my:hello3" className="test.tag.HelloTag3"/>
<html>
<head>
<title>test</title>
</head>
<body>
<h1>c:cout test</h1>
<c:out value="${user.userName}"></c:out>
<c:out><div>${user.userName}</div></c:out>
<c:out id="1" escapeXml="true">
    <div><c:out id="2" value="${user.userName}"></c:out></div>
</c:out>

<h1>c:if test</h1>
<c:if test="${1 == 1}">1 == 1</c:if>

<h1>c:each test</h1>

<c:each items="1,2,3" var="varValue"><p>${varValue}</p></c:each>

<c:forEach items="${userList}" var="user">
    <c:choose>
        <c:when test="${user.age == 0}">0</c:when>
        <c:when test="${user.age == 1}">1</c:when>
        <c:when test="${user.age == 2}">2</c:when>
        <c:when test="${user.age == 3}">3</c:when>
        <c:otherwise>other: ${user.age}</c:otherwise>
    </c:choose>
</c:forEach>
<h1>my:hello test</h1>
<my:hello1/>
<my:hello2/>
<my:hello3/>
</body>
</html>



http://ayada.googlecode.com/svn/trunk/