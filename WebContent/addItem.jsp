<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="windows-1255"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1 maximum-scale=1, user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Item_Adding_Page</title>

</head>
<body>
	<div data-role="page" id="addItem">
	<div data-role="header">
		<div align="center" style="font-size:15px;"><br><h1>What Item You Want to Add</h1></div>
	<div data-role="content" align="center">
		<form action="/Java_ee/controller/addItem" method="post">
		<%
			if (request.getAttribute("message") != null)
			{
				out.println((String)request.getAttribute("message"));
			}
		%>
            <br>
            <br>
  			<textarea name="itemContent" id="info"></textarea>
  			<br>
  			<br>
  			<input type="submit" value="Post It!" data-icon="check" data-iconpos="right">
  			
  			<input type="submit" formaction="/Java_ee/controller/userPage" name="cancel" value="Back" data-icon="back" data-iconpos="right">
		</form>
	</div></div></div>
</body>
</html>