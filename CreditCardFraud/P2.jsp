<%@ page import ="java.sql.*" %> 
<%@ page import ="javax.sql.*" %> 
<%@ page import ="java.sql.*" %> 
<%@ page import ="javax.sql.*" %> 
﻿<html><head><link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

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

String otp2 = (String)session.getAttribute("o");
String user = (String)session.getAttribute("n");
String userid = (String)session.getAttribute("u");
String pass = (String)session.getAttribute("p");
String creditno = request.getParameter("credit");
String cvv1 = request.getParameter("cvv");
String otp1 = request.getParameter("otp");
String ip1 = request.getParameter("ip");
int amount = Integer.parseInt(request.getParameter("amt"));
String message2 = "";
String amtype = "";
if(amount<25000)
amtype = "low";
else if((25000<=amount)&&(amount<75000))
amtype = "medium";
else
amtype = "high";
Class.forName("com.mysql.jdbc.Driver");
java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost/bankdb","bankdb","bankdb");

Statement stch = con.createStatement(); 
ResultSet rsch = stch.executeQuery("select * from Users where Username =\""+user+"\";");
rsch.next();
String uidch = rsch.getString("UID");




Statement stu = con.createStatement(); 
String sql3="insert into Current values(\"T\",\""+uidch+"\",\""+ip1+"\",\""+amtype+"\",\"Ty\");";
int rsu1=con.createStatement().executeUpdate(sql3);

Statement st = con.createStatement(); 
ResultSet rs = st.executeQuery("select * from Users where Username ='"+user+"';");
int flag=0;
while(rs.next()){
String credit1 = rs.getString("creditno");
String cvv2 = rs.getString("cvv");
if((creditno.equals(credit1))&&(cvv1.equals(cvv2))){
flag=1;
}
}
if(flag==1){
if(otp1.equals(otp2)){
flag=2;
}
else
flag=3;
}
if(flag==0)
message2 = "Incorrect Card details!";

else if(flag==3)
message2 = "Incorrect OTP!";
else if(flag==2)
{message2 = "Proceed to Pay "+amtype;
String site = new String("http://localhost:8080/hello");
response.setStatus(response.SC_MOVED_TEMPORARILY);
response.setHeader("Location", site);

}
out.println(""+message2);

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

