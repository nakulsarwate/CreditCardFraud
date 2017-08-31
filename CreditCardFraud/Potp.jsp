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
                                  <label for="username" class="control-label"><% int a = (int)(Math.random()*10000); out.println("Your OTP is: "+a); session.setAttribute("o",String.valueOf(a)); %></label>
                                  <span class="help-block"></span>
                              </div>
                              <br>
                              <button type="submit" class="btn btn-success btn-block"><% out.println("Proceed to pay!"); %></button>
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
