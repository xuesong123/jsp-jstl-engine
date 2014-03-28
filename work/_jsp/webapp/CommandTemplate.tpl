[NODE]: <jsp:directive.page lineNumber="1" offset="0" length="2" contentType="text/html; charset=UTF-8">
[NODE]: </jsp:directive.page>
[TEXT]: <h1>tag.bodyContent</h1>\r\n<p>1. jsp: any content</p>\r\n<p>2. tagdependent: tag only</p>\r\n<p>3. empty: ignore any content</p>\r\n
[NODE]: <t:import lineNumber="6" offset="3" length="2" tagClass="com.skin.ayada.jstl.core.ImportTag" tagFactory="com.skin.ayada.jstl.factory.ImportTagFactory" name="c:if" className="com.skin.ayada.jstl.core.IfTag" bodyContent="tagdependent" description="重写c:if标签, 不输出内容">
[NODE]: </t:import>
[NODE]: <c:set lineNumber="8" offset="5" length="2" tagClass="com.skin.ayada.jstl.core.SetTag" tagFactory="com.skin.ayada.jstl.factory.SetTagFactory" var="template_print" value="true">
[NODE]: </c:set>
[TEXT]: <!-- c:command 不会向页面输出任何东西，但其中的内容仍然会被执行 -->\r\n
[NODE]: <c:command lineNumber="10" offset="8" length="16" tagClass="com.skin.ayada.jstl.core.DependentTag" tagFactory="com.skin.ayada.jstl.factory.DependentTagFactory">
[NODE]: <c:if lineNumber="11" offset="9" length="14" tagClass="com.skin.ayada.jstl.core.IfTag" tagFactory="com.skin.ayada.jstl.factory.IfTagFactory" test="${1 == 2}">
[NODE]: <io:copy lineNumber="12" offset="10" length="2" tagClass="com.skin.ayada.jstl.io.CopyTag" tagFactory="com.skin.ayada.jstl.factory.CopyTagFactory" file="E:/WorkSpace/ayada/webapp/test1" todir="E:/WorkSpace/ayada/webapp/test2">
[NODE]: </io:copy>
[NODE]: <io:delete lineNumber="13" offset="12" length="2" tagClass="com.skin.ayada.jstl.io.DeleteTag" tagFactory="com.skin.ayada.jstl.factory.DeleteTagFactory" file="E:/WorkSpace/ayada/webapp/test2/style.css">
[NODE]: </io:delete>
[NODE]: <io:mkdir lineNumber="14" offset="14" length="2" tagClass="com.skin.ayada.jstl.io.MakeDirTag" tagFactory="com.skin.ayada.jstl.factory.MakeDirTagFactory" file="E:/WorkSpace/ayada/webapp/test2/empty1">
[NODE]: </io:mkdir>
[NODE]: <io:mkdir lineNumber="15" offset="16" length="2" tagClass="com.skin.ayada.jstl.io.MakeDirTag" tagFactory="com.skin.ayada.jstl.factory.MakeDirTagFactory" file="E:/WorkSpace/ayada/webapp/test2/empty2">
[NODE]: </io:mkdir>
[NODE]: <io:delete lineNumber="16" offset="18" length="2" tagClass="com.skin.ayada.jstl.io.DeleteTag" tagFactory="com.skin.ayada.jstl.factory.DeleteTagFactory" file="E:/WorkSpace/ayada/webapp/test2/empty2">
[NODE]: </io:delete>
[NODE]: <c:exit lineNumber="17" offset="20" length="2" tagClass="com.skin.ayada.jstl.core.ExitTag" tagFactory="com.skin.ayada.jstl.factory.ExitTagFactory" test="${1 == 1}">
[NODE]: </c:exit>
[NODE]: </c:if>
[NODE]: </c:command>
[TEXT]: \r\n\r\n<h1>sql:execute</h1>\r\n<h2>connection</h2>\r\n<p>1. get from attribute, example: connection=\"${expression}\"</p>\r\n<p>2. get from pageContext by name: connection</p>\r\n<p>3. get from parent tag</p>\r\n<p>4. throws exception</p>\r\n<p>template.home: 
[EXPR]: ${template.home}
[TEXT]: </p>\r\n\r\n<!-- use external connection -->\r\n
[NODE]: <sql:connect lineNumber="30" offset="27" length="2" tagClass="com.skin.ayada.jstl.sql.ConnectTag" tagFactory="com.skin.ayada.jstl.factory.ConnectTagFactory" var="connection2" connection="${myConnection}">
[NODE]: </sql:connect>
[TEXT]: \r\n
[NODE]: <sql:connect lineNumber="31" offset="30" length="7" tagClass="com.skin.ayada.jstl.sql.ConnectTag" tagFactory="com.skin.ayada.jstl.factory.ConnectTagFactory" var="connection" url="jdbc:mysql://localhost:3306?user=root&password=1234&characterEncoding=utf8" driverClass="com.mysql.jdbc.Driver">
[TEXT]: \r\n    
[NODE]: <sql:execute lineNumber="32" offset="32" length="3" tagClass="com.skin.ayada.jstl.sql.SqlTag" tagFactory="com.skin.ayada.jstl.factory.SqlTagFactory" out="${pageContext.getOut()}">
[TEXT]: \r\n        drop database if exists mytest2;\r\n        create database mytest2 character set utf8;\r\n    
[NODE]: </sql:execute>
[TEXT]: \r\n
[NODE]: </sql:connect>
[TEXT]: \r\n\r\n
[NODE]: <sql:connect lineNumber="38" offset="38" length="56" tagClass="com.skin.ayada.jstl.sql.ConnectTag" tagFactory="com.skin.ayada.jstl.factory.ConnectTagFactory" var="connection" url="jdbc:mysql://localhost:3306/mytest2?user=root&password=1234&characterEncoding=utf8" driverClass="com.mysql.jdbc.Driver">
[TEXT]: \r\n    
[NODE]: <sql:execute lineNumber="39" offset="40" length="2" tagClass="com.skin.ayada.jstl.sql.SqlTag" tagFactory="com.skin.ayada.jstl.factory.SqlTagFactory" home="${template.home}/database" file="create.sql" encoding="UTF-8" out="${pageContext.getOut()}">
[NODE]: </sql:execute>
[TEXT]: \r\n    
[NODE]: <sql:execute lineNumber="40" offset="43" length="2" tagClass="com.skin.ayada.jstl.sql.SqlTag" tagFactory="com.skin.ayada.jstl.factory.SqlTagFactory" sql="delete from my_test1;" out="${pageContext.getOut()}">
[NODE]: </sql:execute>
[TEXT]: \r\n    
[NODE]: <sql:execute lineNumber="41" offset="46" length="2" tagClass="com.skin.ayada.jstl.sql.SqlTag" tagFactory="com.skin.ayada.jstl.factory.SqlTagFactory" sql="delete from my_test2;" out="${pageContext.getOut()}">
[NODE]: </sql:execute>
[TEXT]: \r\n    
[NODE]: <sql:execute lineNumber="42" offset="49" length="2" tagClass="com.skin.ayada.jstl.sql.SqlTag" tagFactory="com.skin.ayada.jstl.factory.SqlTagFactory" sql="insert into my_test2(my_id, my_code, my_name) values (1, '1', '1');">
[NODE]: </sql:execute>
[TEXT]: \r\n\r\n    
[NODE]: <sql:execute lineNumber="44" offset="52" length="23" tagClass="com.skin.ayada.jstl.sql.SqlTag" tagFactory="com.skin.ayada.jstl.factory.SqlTagFactory" out="${pageContext.getOut()}">
[TEXT]: \r\n        
[NODE]: <c:forEach lineNumber="45" offset="54" length="9" tagClass="com.skin.ayada.jstl.core.ForEachTag" tagFactory="com.skin.ayada.jstl.factory.ForEachTagFactory" items="1, 2, 3, 4, 5" var="id">
[TEXT]: \r\n            insert into my_test1(my_id, my_code, my_name) values (
[VARI]: ${id}
[TEXT]: , \'
[VARI]: ${id}
[TEXT]: \', \'
[VARI]: ${id}
[TEXT]: \');\r\n        
[NODE]: </c:forEach>
[TEXT]: \r\n        
[NODE]: <c:forEach lineNumber="48" offset="64" length="9" tagClass="com.skin.ayada.jstl.core.ForEachTag" tagFactory="com.skin.ayada.jstl.factory.ForEachTagFactory" items="1, 2, 3, 4, 5" var="id">
[TEXT]: \r\n            insert into my_test2(my_id, my_code, my_name) values (
[VARI]: ${id}
[TEXT]: , \'
[VARI]: ${id}
[TEXT]: \', \'
[VARI]: ${id}
[TEXT]: \');\r\n        
[NODE]: </c:forEach>
[TEXT]: \r\n    
[NODE]: </sql:execute>
[TEXT]: \r\n\r\n    
[NODE]: <sql:execute lineNumber="53" offset="76" length="3" tagClass="com.skin.ayada.jstl.sql.SqlTag" tagFactory="com.skin.ayada.jstl.factory.SqlTagFactory" out="${pageContext.getOut()}">
[TEXT]: \r\n        delete from my_test2;\r\n        insert into my_test2(my_id, my_code, my_name) values (1001, \'1001\', \'1001\');\r\n        insert into my_test2(my_id, my_code, my_name) values (1002, \'1001\', \'1001\');\r\n        insert into my_test2(my_id, my_code, my_name) values (1003, \'1001\', \'1001\');\r\n        insert into my_test2(my_id, my_code, my_name) values (1004, \'1001\', \'1001\');\r\n        insert into my_test2(my_id, my_code, my_name) values (1005, \'1001\', \'1001\');\r\n        insert into my_test2(my_id, my_code, my_name) values (1006, \'1001\', \'1001\');\r\n        insert into my_test2(my_id, my_code, my_name) values (1007, \'1001\', \'1001\');\r\n        insert into my_test2(my_id, my_code, my_name) values (1008, \'1001\', \'1001\');\r\n    
[NODE]: </sql:execute>
[TEXT]: \r\n\r\n    
[NODE]: <sql:execute lineNumber="65" offset="80" length="2" tagClass="com.skin.ayada.jstl.sql.SqlTag" tagFactory="com.skin.ayada.jstl.factory.SqlTagFactory" connection="${connection}" sql="insert into my_test2(my_id, my_code, my_name) values (1009, '1001', '1001');">
[NODE]: </sql:execute>
[TEXT]: \r\n\r\n    <table>\r\n        <tr>\r\n            <td>id</td>\r\n            <td>userName</td>\r\n            <td>nickName</td>\r\n        </tr>\r\n        
[NODE]: <sql:query lineNumber="73" offset="83" length="9" tagClass="com.skin.ayada.jstl.sql.QueryTag" tagFactory="com.skin.ayada.jstl.factory.QueryTagFactory" var="resultSet" sql="select * from my_test2 where my_id > 1001" offset="2" length="3">
[TEXT]: \r\n        <tr>\r\n            <td>
[EXPR]: ${resultSet.getLong(\"my_id\")}
[TEXT]: </td>\r\n            <td>
[EXPR]: ${resultSet.getString(\"my_code\")}
[TEXT]: </td>\r\n            <td>
[EXPR]: ${resultSet.getString(\"my_name\")}
[TEXT]: </td>\r\n        </tr>\r\n        
[NODE]: </sql:query>
[TEXT]: \r\n    </table>\r\n
[NODE]: </sql:connect>
