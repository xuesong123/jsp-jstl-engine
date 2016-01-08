<%@ page import="java.util.Map"%>
<%@ page import="java.util.List"%>
<html>
<head>
<title>test</title>
<meta http-equiv="Content-Type" content="text/html;"/>
<style type="text/css">
body{font-size: 10pt; color: #333333;}
thead{ font-weight: bold; background-color: #c8fbaf;}
td{font-size: 10pt; text-align: center;}
.odd{background-color: #f3defb;}
.even{background-color: #effff8;}
</style>
</head>
<body>
    <h1>test</h1>
    <table>
        <thead>
            <tr>
                <th>test1</th>
                <th>test2</th>
                <th>test3</th>
                <th>test4</th>
                <th>test5</th>
            </tr>
        </thead>
        <tbody>
            <%
                int i = 0;
                List<Map<String, Object>> userList = (List<Map<String, Object>>)(pageContext.getAttribute("userList"));

                for(Map<String, Object> user : userList) {
            %>
            <tr <%=((i & 1) == 1 ? "old" : "even")%>>
                <td><%=i + 1%></td>
                <td><%=user.get("code")%></td>
                <td><%=user.get("name")%></td>
                <td><%=user.get("date")%></td>
                <td><%=user.get("value")%></td>
                <td style="color: red"><%=((Float)(user.get("value")) > 105.5 ? "a" : "b")%></td>
                <td style="color: red"><%=((Float)(user.get("value")) > 105.5 ? "a" : "b")%></td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>
</body>
</html>
