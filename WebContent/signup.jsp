<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="windows-1255"%>
<!DOCTYPE html>
<html>

<head>
<meta name="viewport" content="width=device-width, initial-scale=1 maximum-scale=1, user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Sign Up Page</title>
</head>

<body>
	<div data-role="page" id="signup" style="background-color:orange" >
	<div data-role="header" style="background-color:fuchsia">
		<div align="center" style="background-color:aqua"><br><h1><font size ="18" style="font-family:fantasy" color="orange">Welcome to the sign up page</font></h1>
			<h2><font size ="15"  style="font-family:fantasy" color ="pink">Crate your account </font></h2>
		</div>
		
	<div data-role="content" align="center">
		<form action="/Java_ee/controller/signup" method="post">
		<%
			if (request.getAttribute("message") != null)
			{
				out.println((String)request.getAttribute("message"));
			}
		%>
		<br>
			
			<label for="userName" class="ui-hidden-accessible"><font size ="6"style="font-family:serif" color="white">User name:</font></label>
  			<input type="text" name="userName" id="userName" placeholder="Enter User name here" data-clear-btn="true">
  			<br>
  			
  			<label for="lastName" class="ui-hidden-accessible"><font size ="6"style="font-family:serif" color="white">Last name:</font></label>
  			<input type="text" name="lastName" id="lastName" placeholder="Enter Last name here" data-clear-btn="true">
  			<br>
  			
  			<label for="password" class="ui-hidden-accessible"><font size ="6"style="font-family:serif" color="white">Password:</font></label>
  			<input type="password" name="password" id="password" placeholder="Enter Password here" data-clear-btn="true">
  			<br>
  			
  			<input type="submit" value="Create Account" data-icon="check" data-iconpos="right">
  			&nbsp;
  			<input type="submit" formaction="/Java_ee/controller/index" name="cancel" value="Cancel" data-icon="delete" data-iconpos="right">
		</form>
	</div></div></div>
</body>
</html>
