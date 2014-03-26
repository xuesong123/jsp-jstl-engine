[NODE]: <jsp:directive.page lineNumber="1" offset="0" length="2" contentType="text/html; charset=UTF-8">
[NODE]: </jsp:directive.page>
[TEXT]: <!-- c:command 不会向页面输出任何东西，但其中的内容仍然会被执行 -->\r\n
[NODE]: <c:command lineNumber="3" offset="3" length="23" tagClass="com.skin.ayada.jstl.core.DependentTag" tagFactory="com.skin.ayada.jstl.factory.DependentTagFactory">
[NODE]: <c:if lineNumber="4" offset="4" length="21" tagClass="com.skin.ayada.jstl.core.IfTag" tagFactory="com.skin.ayada.jstl.factory.IfTagFactory" test="${1 == 2}">
[TEXT]: \r\n        
[NODE]: <io:copy lineNumber="5" offset="6" length="2" tagClass="com.skin.ayada.jstl.io.CopyTag" tagFactory="com.skin.ayada.jstl.factory.CopyTagFactory" file="E:/WorkSpace/ayada/webapp/test1" todir="E:/WorkSpace/ayada/webapp/test2">
[NODE]: </io:copy>
[TEXT]: \r\n        
[NODE]: <io:delete lineNumber="6" offset="9" length="2" tagClass="com.skin.ayada.jstl.io.DeleteTag" tagFactory="com.skin.ayada.jstl.factory.DeleteTagFactory" file="E:/WorkSpace/ayada/webapp/test2/style.css">
[NODE]: </io:delete>
[TEXT]: \r\n        
[NODE]: <io:mkdir lineNumber="7" offset="12" length="2" tagClass="com.skin.ayada.jstl.io.MakeDirTag" tagFactory="com.skin.ayada.jstl.factory.MakeDirTagFactory" file="E:/WorkSpace/ayada/webapp/test2/empty1">
[NODE]: </io:mkdir>
[TEXT]: \r\n        
[NODE]: <io:mkdir lineNumber="8" offset="15" length="2" tagClass="com.skin.ayada.jstl.io.MakeDirTag" tagFactory="com.skin.ayada.jstl.factory.MakeDirTagFactory" file="E:/WorkSpace/ayada/webapp/test2/empty2">
[NODE]: </io:mkdir>
[TEXT]: \r\n        
[NODE]: <io:delete lineNumber="9" offset="18" length="2" tagClass="com.skin.ayada.jstl.io.DeleteTag" tagFactory="com.skin.ayada.jstl.factory.DeleteTagFactory" file="E:/WorkSpace/ayada/webapp/test2/empty2">
[NODE]: </io:delete>
[TEXT]: \r\n        
[NODE]: <c:exit lineNumber="10" offset="21" length="2" tagClass="com.skin.ayada.jstl.core.ExitTag" tagFactory="com.skin.ayada.jstl.factory.ExitTagFactory" test="${1 == 1}">
[NODE]: </c:exit>
[TEXT]: \r\n    
[NODE]: </c:if>
[NODE]: </c:command>
[TEXT]: \r\n\r\n<h1>sql:execute</h1>\r\n<h2>connection</h2>\r\n<p>1. get from attribute, example: connection=\"[expression]\"</p>\r\n<p>2. get from pageContext by name: connection</p>\r\n<p>3. get from parent tag</p>\r\n<p>4. throws exception</p>\r\n<p>template.home: 
[EXPR]: ${template.home}
[TEXT]: </p>\r\n\r\n<!-- use external connection -->\r\n
[NODE]: <sql:connect lineNumber="23" offset="29" length="2" tagClass="com.skin.ayada.jstl.sql.ConnectTag" tagFactory="com.skin.ayada.jstl.factory.ConnectTagFactory" var="connection2" connection="${myConnection}">
[NODE]: </sql:connect>
[TEXT]: \r\n
[NODE]: <sql:connect lineNumber="24" offset="32" length="7" tagClass="com.skin.ayada.jstl.sql.ConnectTag" tagFactory="com.skin.ayada.jstl.factory.ConnectTagFactory" var="connection" url="jdbc:mysql://localhost:3306?user=root&password=1234&characterEncoding=utf8" driverClass="com.mysql.jdbc.Driver">
[TEXT]: \r\n    
[NODE]: <sql:execute lineNumber="25" offset="34" length="3" tagClass="com.skin.ayada.jstl.sql.SqlTag" tagFactory="com.skin.ayada.jstl.factory.SqlTagFactory" out="${pageContext.getOut()}">
[TEXT]: \r\n        drop database if exists mytest2;\r\n        create database mytest2 character set utf8;\r\n    
[NODE]: </sql:execute>
[TEXT]: \r\n
[NODE]: </sql:connect>
[TEXT]: \r\n\r\n
[NODE]: <sql:connect lineNumber="31" offset="40" length="56" tagClass="com.skin.ayada.jstl.sql.ConnectTag" tagFactory="com.skin.ayada.jstl.factory.ConnectTagFactory" var="connection" url="jdbc:mysql://localhost:3306/mytest2?user=root&password=1234&characterEncoding=utf8" driverClass="com.mysql.jdbc.Driver">
[TEXT]: \r\n    
[NODE]: <sql:execute lineNumber="32" offset="42" length="2" tagClass="com.skin.ayada.jstl.sql.SqlTag" tagFactory="com.skin.ayada.jstl.factory.SqlTagFactory" home="${template.home}/database" file="create.sql" encoding="UTF-8" out="${pageContext.getOut()}">
[NODE]: </sql:execute>
[TEXT]: \r\n    
[NODE]: <sql:execute lineNumber="33" offset="45" length="2" tagClass="com.skin.ayada.jstl.sql.SqlTag" tagFactory="com.skin.ayada.jstl.factory.SqlTagFactory" sql="delete from my_test1;" out="${pageContext.getOut()}">
[NODE]: </sql:execute>
[TEXT]: \r\n    
[NODE]: <sql:execute lineNumber="34" offset="48" length="2" tagClass="com.skin.ayada.jstl.sql.SqlTag" tagFactory="com.skin.ayada.jstl.factory.SqlTagFactory" sql="delete from my_test2;" out="${pageContext.getOut()}">
[NODE]: </sql:execute>
[TEXT]: \r\n    
[NODE]: <sql:execute lineNumber="35" offset="51" length="2" tagClass="com.skin.ayada.jstl.sql.SqlTag" tagFactory="com.skin.ayada.jstl.factory.SqlTagFactory" sql="insert into my_test2(my_id, my_code, my_name) values (1, '1', '1');">
[NODE]: </sql:execute>
[TEXT]: \r\n\r\n    
[NODE]: <sql:execute lineNumber="37" offset="54" length="23" tagClass="com.skin.ayada.jstl.sql.SqlTag" tagFactory="com.skin.ayada.jstl.factory.SqlTagFactory" out="${pageContext.getOut()}">
[TEXT]: \r\n        
[NODE]: <c:forEach lineNumber="38" offset="56" length="9" tagClass="com.skin.ayada.jstl.core.ForEachTag" tagFactory="com.skin.ayada.jstl.factory.ForEachTagFactory" items="1, 2, 3, 4, 5" var="id">
[TEXT]: \r\n            insert into my_test1(my_id, my_code, my_name) values (
[VARI]: ${id}
[TEXT]: , \'
[VARI]: ${id}
[TEXT]: \', \'
[VARI]: ${id}
[TEXT]: \');\r\n        
[NODE]: </c:forEach>
[TEXT]: \r\n        
[NODE]: <c:forEach lineNumber="41" offset="66" length="9" tagClass="com.skin.ayada.jstl.core.ForEachTag" tagFactory="com.skin.ayada.jstl.factory.ForEachTagFactory" items="1, 2, 3, 4, 5" var="id">
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
[NODE]: <sql:execute lineNumber="46" offset="78" length="3" tagClass="com.skin.ayada.jstl.sql.SqlTag" tagFactory="com.skin.ayada.jstl.factory.SqlTagFactory" out="${pageContext.getOut()}">
[TEXT]: \r\n        delete from my_test2;\r\n        insert into my_test2(my_id, my_code, my_name) values (1001, \'1001\', \'1001\');\r\n        insert into my_test2(my_id, my_code, my_name) values (1002, \'1001\', \'1001\');\r\n        insert into my_test2(my_id, my_code, my_name) values (1003, \'1001\', \'1001\');\r\n        insert into my_test2(my_id, my_code, my_name) values (1004, \'1001\', \'1001\');\r\n        insert into my_test2(my_id, my_code, my_name) values (1005, \'1001\', \'1001\');\r\n        insert into my_test2(my_id, my_code, my_name) values (1006, \'1001\', \'1001\');\r\n        insert into my_test2(my_id, my_code, my_name) values (1007, \'1001\', \'1001\');\r\n        insert into my_test2(my_id, my_code, my_name) values (1008, \'1001\', \'1001\');\r\n    
[NODE]: </sql:execute>
[TEXT]: \r\n\r\n    
[NODE]: <sql:execute lineNumber="58" offset="82" length="2" tagClass="com.skin.ayada.jstl.sql.SqlTag" tagFactory="com.skin.ayada.jstl.factory.SqlTagFactory" connection="${connection}" sql="insert into my_test2(my_id, my_code, my_name) values (1009, '1001', '1001');">
[NODE]: </sql:execute>
[TEXT]: \r\n\r\n    <table>\r\n        <tr>\r\n            <td>id</td>\r\n            <td>userName</td>\r\n            <td>nickName</td>\r\n        </tr>\r\n        
[NODE]: <sql:query lineNumber="66" offset="85" length="9" tagClass="com.skin.ayada.jstl.sql.QueryTag" tagFactory="com.skin.ayada.jstl.factory.QueryTagFactory" var="resultSet" sql="select * from my_test2 where my_id > 1001" offset="2" length="3">
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
