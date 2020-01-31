<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="windows-1255"%>
<!DOCTYPE html>
<html>

<head>
<meta name="viewport" content="width=device-width, initial-scale=1 maximum-scale=1, user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Update Item</title>
</head>

<body>
	<div data-role="page" id="updateItem">
	<div data-role="header">
		<div align="center" style="font-size:15px;"><br><h1>Update your item</h1></div>
	<div data-role="content" align="center">
		<form action="/Java_ee/controller/updateItem" method="post">
		<%
			if (request.getAttribute("message") != null)
			{
				out.println((String)request.getAttribute("message"));
			}
			String itemId = (String)request.getAttribute("itemId");
		%>
		      <br>
		      <br>
  			<textarea name="updateContent" id="info" placeholder="<%=request.getAttribute("itemContent")%>"></textarea>
  			<input type="hidden" value="<%=itemId %>" name="itemId">
  			<br>
  			<br>
  			<input type="submit" value="Done!" data-icon="check" data-iconpos="right">
  			<input type="submit" formaction="/Java_ee/controller/userPage" name="cancel" value="Back" data-icon="back" data-iconpos="right">
		</form>
	</div></div></div>
</body>
</html>