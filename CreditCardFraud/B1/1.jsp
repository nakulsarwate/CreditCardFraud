
<%@ page import ="java.sql.*"%>
<%@ page import ="javax.sql.*"%>
<html>
<head>
</head>
<body>
<%
Class.forName("com.mysql.jdbc.Driver");
java.sql.Connection con=DriverManager.getConnection("jdbc:mysql://localhost/bankdb","bankdb","bankdb");
ResultSet rs=con.createStatement().executeQuery("Select * from transactions;"); 
%>

<table border="1">
<tr>
<th> TID</th>
<th>UID</th>
<th>IP</th>
<th>Amount</th>
<th>Type</th>
<%
while(rs.next())
{
%>
<tr>
<td><%=rs.getString("TID")%></td>
<td><%=rs.getString("UID");%></td>
<td><%=rs.getString("IP")%></td>
<td><%=rs.getString("Amount")%></td>
<td><%=rs.getString("type")%></td>
</tr>
<%
}



%>
</table>
</body>
</html>