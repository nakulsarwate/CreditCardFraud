<%@ page import ="java.sql.*" %> 
<%@ page import ="javax.sql.*" %> 
<% 
String site = new String("http://localhost:8080/Second.html");
response.setStatus(response.SC_MOVED_TEMPORARILY);
response.setHeader("Location", site);
%>
