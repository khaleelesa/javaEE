<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="windows-1255"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1 maximum-scale=1, user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Error Page</title>

</head>
<body>
	<div data-role="page" id="errorPage">
	<div data-role="header">
		<div align="center" style="font-size:15px;"><br><h1>Ops, There is a problem  :(</h1></div>
	<div data-role="content" align="center">
		<%
			out.println((String)request.getAttribute("errorMessage"));
		%>
	</div></div></div>
</body>
</html>