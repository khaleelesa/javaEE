<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page language="java" contentType="text/html; charset=utf-8" 
    pageEncoding="windows-1255" import="java.util.List,il.ac.hit.todolist.Model.ItemInfo"%> 
<!DOCTYPE html>
<html>

<head>
<meta name="viewport" content="width=device-width, initial-scale=1 maximum-scale=1, user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>UserPage</title>

<style>
ul {
  list-style-type:non;
  margin-left: 15em;
  margin-right: 15em;
  padding: 0;
  overflow: hidden;
  background-color: #333333;
}

ul li::before {
  content: "\25CE";
  color: white;
  font-weight: bold;
  display: inline-block; 
  width: 1em;
  margin-left: -10em;
}
li {
  float: center;
    display: block;
    color: white;
  text-decoration: none;
   }
</style>
</head>

<body>
	<div data-role="page" id="userPage"style="background-color:orange">
	<div data-role="header"style="background-color:aqua">
		<div align="center" style="font-size:15px">
		<br>
		<br>
		<%
			String userName = (String)request.getAttribute("userName");
			out.println("<h1>Welcome " + userName + "</h1>");
		%>
		<br>
		<br>
		</div>
	</div>
	
	<div data-role="content" style="font-size:20px;" align="center">
	<h1><font size ="18"  style="font-family:fantasy"color="blue">-- Your ToDoList --</font></h1>
		<ul data-role="listview"">
			<%
				List<ItemInfo> userItems = (List<ItemInfo>)request.getAttribute("userItems");
						for (ItemInfo item : userItems)
						{
			%>
				<li>						
					<table>
						<tr>
							<td style="white-space: normal;">
							<%
								out.println(item.getinfo());
								out.println("</br>");
							%>
							</td>
						</tr>
						<tr data-role="controlgroup">
							<td>
								<form action="/Java_ee/controller/deleteItem" method="post">
								<fieldset data-role="controlgroup" data-type="horizontal" data-mini="true">
									<input type="hidden" value="<%=item.getitemId() %>" name="itemId">
									<input type="submit" value="Delete" data-icon="delete" data-iconpos="notext">
								</fieldset>
								</form>
							</td>
							<td>
							
								<form action="/Java_ee/controller/updateItem" method="post">
								<fieldset data-role="controlgroup" data-type="horizontal" data-mini="true">
									<input type="hidden" value="<%=item.getitemId() %>" name="itemId">
									<input type="submit" value="Edit" data-icon="edit" data-iconpos="notext">
								</fieldset>
								</form>
							</td>	
															
						</tr>					
					</table>
				</li>
				<%}
			%>
		</ul>
		
		   <form action="/Java_ee/controller/addItem" method="post">
  			<input type="submit" value="Add Item" data-icon="check" data-iconpos="right">
  			<br>
  			<input type="submit" formaction="/Java_ee/controller/index" name="signout" value="Sign out" data-icon="power" data-iconpos="right">
 
  		</form>		
	</div>
	</div>
</body>
</html>