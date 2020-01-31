<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="windows-1255"%>
<!DOCTYPE html>
<html>

<head>
<meta name="viewport" content="width=device-width, initial-scale=1 maximum-scale=1, user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Login_Page</title>
</head>

<body>
	<div data-role="page" id="login"style="background-color:fuchsia">
	<div data-role="header"style="background-color:aqua">
		<div align="center"style="background-color:orange"><br><h1><font size ="18"  style="font-family:fantasy"color="blue">--Welcome to the Login Page--</font></h1></div>
	
	<div data-role="content" align="center">
		<form action="/Java_ee/controller/login" method="post">
		<%
			if (request.getAttribute("message") != null)
			{
				out.println((String)request.getAttribute("message"));
			}
		%>
		<br>
  			<label for="userName" class="ui-hidden-accessible"><font size ="6"style="font-family:fantasy"color="green">User name:</font> </label>
  			<input type="text" name="userName" id="userName" placeholder="Enter user name here" data-clear-btn="true">
  			<br>
  			<br>
  			<label for="password" class="ui-hidden-accessible"><font size ="6"style="font-family:fantasy"color="green">Password:</font> </label>
  			<input type="password" name="password" id="password" placeholder="Enter password here" data-clear-btn="true" >
  			<br>
  			<br>
  			<input type="submit" value="Log in" data-icon="check" data-iconpos="right" >
  			&nbsp;
  			<input type="submit" formaction="/Java_ee/controller/index" name="cancel" value="Cancel" data-icon="delete" data-iconpos="right">
		</form>
		
	</div></div></div>
</body>
</html>
