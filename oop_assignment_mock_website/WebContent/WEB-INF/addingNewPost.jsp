<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="model.categoryModel" %>
    <%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Adding new post</title>
</head>
<body>

	<%
		ArrayList<categoryModel> categories = null;
		Object tmp = request.getAttribute("categories");
		if(null != tmp){
			categories = (ArrayList<categoryModel>) tmp;
		}
	%>

	<h1>This is adding new post page</h1>
	<form action="addNewPost" method="post">
		Post title: <input type="text" name="postTitle"><br>
		Post introduction:<br>
		<textarea name="postIntroduction" rows="10" cols="30"></textarea><br>
		Post content: <br>
		<textarea name="postContent" rows="10" cols="30"></textarea><br><br>
		Post category:<br>
  		<%
  			if(null != categories){
  				for(categoryModel category : categories){
  					%><input type="radio" name=postCategoryID value="<% out.print(category.getCategoryID()); %>" checked><% out.print(category.getCategoryName()); %><br><%
  				}
  			}
  		%>
  		<input value="addNewPost" type="hidden" name="function">
  		<input value="Submit" type="submit"><br>
	</form>
	<hr>
	
	<!-- HOME -->
	<h1><a href="">Home</a></h1>
	<h1><a href="login">Admin Portal</a></h1>
</body>
</html>