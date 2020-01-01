<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ page import="model.postModel" %>
    <%@ page import="model.commentModel" %>
    <%@ page import="java.util.ArrayList" %>
    <%@ page import="model.categoryModel" %>
    
<!DOCTYPE html>
<html>

<!-- LOADING CODE BLOCK -->
<%
	//Variables
	postModel post = null;
	ArrayList<commentModel> comments = null;
	
	//Getting post
	try{
		post = (postModel) request.getAttribute("post");
		System.out.println("Getting post successfully - post content jsp page");
	}catch(Exception e){
		System.out.println("Getting post fail - post content jsp page");
		e.printStackTrace();
	}
	
	//Getting comments
	try{
		comments = (ArrayList<commentModel>) request.getAttribute("comments");
		System.out.println("Getting comments successfully - post content jsp page");
	}catch(Exception e){
		System.out.println("Getting comments fail - post content jsp page");
		e.printStackTrace();
	}
%>
<!-- Ending loading code block -->

<head>
	<meta charset="ISO-8859-1">
	
	<!-- POST TITLE -->
	<% 
		if (null != post)
		{
			%>
				<title>postTitle</title>
			<%
		}
	%>
	<!-- Post title ending -->
		
</head>

<body>
	<!-- INTRODUCTION -->
	<h1>Ngoc Nhuan HUYNH Blog</h1>
	<hr>
	<!-- Introduction ending -->
	
	<!-- BLOG CONTENT -->
	<%
		//Post content
		if (null != post)
		{
			%>
				<h2><% out.println(post.getPostTitle()); %></h2>
				<h3><% out.println(post.getPostDate()); %></h3>
				<h3><% out.println("Reading count: " + post.getPostReadingCount()); %></h3>
				<h3><% out.println(post.getPostIntroduction()); %></h3>
				<h3><%
					categoryModel category = post.getPostCategory();
					if(null!=category){
						out.println(category.getCategoryName());
					}
				%></h3>
				<p><% out.println(post.getPostContent()); %></p>
				<hr>
			<%
		}
	
		//Comment content
		%>
			<h2>Comments</h2>
		<%
		if (null != comments)
		{
			for (commentModel comment : comments)
			{
				%>
					<h3><% out.println(comment.getCommentUsername() + " says: "); %></h3>
					<h5><% out.println(comment.getCommentDate()); %></h5>
					<p><% out.println(comment.getCommentContent()); %></p>
				<%
			}
		}
		else
		{
			%>
				<h3>There is no comments for this post</h3>
			<%
		}
		%>
			<hr>
		<%
	%>
	<!-- Blog content ending -->
	
	<!-- ADDING COMMENT SECTION -->
	<h2>Adding comment section</h2>
	
	<form action="addingComment" method="POST">
		Username: <input type="text" name="txtUsername"><br>
  		Comment: <textarea name="txtComment" rows="10" cols="30"></textarea><br>
  		<input type="hidden" name="intPostID" value="<%= post.getPostID() %>">
  		<input type="submit" value="Submit">
	</form>
	<!-- Ending adding comment section -->
	
	<!-- LINK TO HOME PAGE -->
	<h1><a href="">Home</a></h1>
	<!--  -->
</body>
</html>