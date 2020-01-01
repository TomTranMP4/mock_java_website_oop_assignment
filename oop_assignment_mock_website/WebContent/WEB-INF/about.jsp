<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="model.userModel" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>About Page</title>
</head>
<body>
	<%
		userModel user = null;
		try{
			user = (userModel) request.getAttribute("user");
			System.out.println("Getting object user from aboutServlet successfully - about jsp page");
		}catch (Exception e) {
			System.out.println("Getting object user from aboutServlet fail - about jsp page");
			e.printStackTrace();
		}
	%>

	<h1>Ngoc Nhuan HUYNH Blog</h1>
	<h1>s1458633</h1>
	<hr>
	
	<!-- USER INFORMATION BLOCK -->
	<%
		if (null != user)
		{
			%>
				<h3>First name: <% out.println(user.getFirstName()); %></h3>
				<h3>Last name: <% out.println(user.getLastName()); %></h3>
				<h3>Contact number: <% out.println(user.getContactNumber()); %></h3>
				<h3>Email: <% out.println(user.getEmail()); %></h3>
				<h3>About: <% out.println(user.getAbout()); %></h3>	
				<hr>
			<%
		}
		else
		{
			%>
				<h3>There is not user information!</h3>	
			<%	
		}
	%>
	
	<!-- HOME LINK -->
	<h1><a href="">Home</a></h1>
	
</body>
</html>