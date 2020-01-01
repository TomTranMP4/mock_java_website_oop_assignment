<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="model.postModel" %>
    <%@ page import="java.util.ArrayList" %>
    <%@ page import="model.categoryModel" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Update post page</title>
</head>
<body>
	
	<!-- LOADING UP VARIABLES SECTION -->
	<%
		//Loading post model
		postModel post = null;
		try{
			post = (postModel)request.getAttribute("post");
			System.out.println("Getting parameter post successfully - update post page");
		}catch(Exception e){
			System.out.println("Getting parameter post fail - update post page");
			e.printStackTrace();
		}
		
		//Loading arrayList of categories
		ArrayList<categoryModel> categories = null;
		try{
			categories = (ArrayList<categoryModel>) request.getAttribute("categories");
			System.out.println("Getting parameter categories successfully - update post page");
		}catch(Exception e){
			System.out.println("Getting parameter categories fai - update post page");
			e.printStackTrace();
		}
	%>
	<!-- ENDING LOADING UP VARIABLES SECTION -->

	<h1>Update post page</h1>
	<hr>
	
	<!-- FORM SECTION FOR UPDATING POST -->
	<%
		if(null != post){
			%>
				<form action="updatePost" method="POST">
					<input type="hidden" value="updatePost" name="function">
					<input type="hidden" value="<% out.print(post.getPostID()); %>" name="intPostID">
					Post title: <input type="text" value="<% out.print(post.getPostTitle()); %>" name="txtPostTitle">
					<br>
					Post introduction:
					<br>
					<textarea name="txtPostIntroduction" rows="10" cols="30"><%out.print(post.getPostIntroduction());%></textarea>
					<br>
					Post content:
					<br> 
					<textarea name="txtPostContent" rows="10" cols="30"><%out.print(post.getPostContent());%></textarea>
					<br>
					Post current category: 
					<%
						if(null != post.getPostCategory()){
							out.print(post.getPostCategory().getCategoryName());
						}
					%>
					<br>
					Choosing new post category:
					<br>
					<%
						if(null != categories){
							for(categoryModel category : categories){
			  					%><input type="radio" name=postCategoryID value="<% out.print(category.getCategoryID()); %>" checked><% out.print(category.getCategoryName()); %><br><%
			  				}
						}
					%>
					<br>
					<input type="submit" value="Update" name="btnUpdate">
					<br>
				</form>
			<%
		}else{
			%>
				<h2>Loading post from database error!</h2>
			<%
		}
	%>
	<!-- ENDING FORM SECTION FOR UPDATING POST -->
	
	<!-- COMING HOME SECTION -->
	<h1><a href="">HOME</a></h1>
	<h1><a href="login">Admin Portal</a></h1>
	
</body>
</html>