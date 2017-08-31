<%@ page import ="java.sql.*" %> 
<%@ page import ="javax.sql.*" %>
<%@ page import="java.io.*,java.util.*" %>
<html><head><link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script><link href="./font-awesome.min.css" rel="stylesheet">



	</head><body background="test.jpg">
	<center>
    <div id="login-overlay" class="modal-dialog">
      <div class="modal-content">
          <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true"><% out.println(""); %></span><span class="sr-only"></span></button>
              <h4 class="modal-title" id="myModalLabel"></h4>
          </div>
          <div class="modal-body">
              <div class="row">
              <center>
                  <div class="col-xs-12">
                      <div class="well">
                          <form id="loginForm" method="POST" action="P3.jsp" novalidate="novalidate">
                              <div class="form-group">
                                  <label for="username" class="control-label"><br><br><% 
String usr = request.getParameter("username");
String pwd = request.getParameter("password");
String uid = "";
String name = "";
String pass = "";
Class.forName("com.mysql.jdbc.Driver");
java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost/bankdb","bankdb","bankdb");
Statement st = con.createStatement(); 
ResultSet rs = st.executeQuery("select * from users;");
String message = "";
int flag=0;
while(rs.next()) 
{
uid = rs.getString("UID");
name = rs.getString("Username");
pass = rs.getString("Password");
if(usr.equals(name)){
flag=1;
if(pwd.equals(pass)){
flag=2;
break;}
else{
flag=1;}
break;}
}
int a =(int) (Math.random()*10000);
if(flag==0)
out.println("Unidentified user!");
else if(flag==1)
out.println("Incorrect password!");
else if(flag==2){
String site = new String("http://localhost:8080/Potp.jsp");
response.setStatus(response.SC_MOVED_TEMPORARILY);
response.setHeader("Location", site);
}

session.setAttribute("n",name);
session.setAttribute("p",pass);
session.setAttribute("u",uid);

%></label>
                                  <span class="help-block"></span>
                              </div>
                              <br>
                              
                          </form>
                      </div>
                  </div>
                  </center>
              </div>
          </div>
      </div>
  </div>
</center>
</body></html>
