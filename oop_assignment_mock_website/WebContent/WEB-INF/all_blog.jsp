<%@page import="model.categoryModel"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ page import="model.postModel" %>
    <%@ page import="java.util.ArrayList" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>All Posts Page</title>
<style>
	table {
	  font-family: arial, sans-serif;
	  border-collapse: collapse;
	  width: 100%;
	}
	
	td, th {
	  border: 1px solid #dddddd;
	  text-align: left;
	  padding: 8px;
	}
	
	tr:nth-child(even) {
	  background-color: #dddddd;
	}
</style>



</head>
<body>
	<h1>This is all posts page</h1><hr>
	
	<!-- LOADING VARIABLES -->
	<%
		ArrayList<postModel> posts = null;
		Object tmp = request.getAttribute("posts");
		
		//Getting posts from servlet
		if(null != tmp){
			try{
				posts = (ArrayList<postModel>) tmp;
				System.out.println("Retrieveing posts successfully - all posts page");
			}catch(Exception e){
				System.out.println("Retrieveing posts fail - all posts page");
				e.printStackTrace();
			}
		}
		
		
		
	%>
	<!-- Ending loading variables -->
	
	
	
	<!-- SEARCH SECTION -->
	
	<h1>Search by Title Section</h1>
	
	<%
		HttpSession userSession = request.getSession(true);
		ArrayList<postModel> searchResults = null;
		tmp = request.getAttribute("searchResults");
		//Retrieving searchResults
		if(null != tmp){
			try{
				searchResults = (ArrayList<postModel>) tmp;
				System.out.println("Retrieveing searchResults successfully - all posts page");
			}catch(Exception e){
				System.out.println("Retrieveing searchResults fail - all posts page");
				e.printStackTrace();
			}
		}
		//Retrieving userSession
		Object temp = userSession.getAttribute("userAuthentication");
		int userAuthentication = 0;
		if(null != temp){
			try{
				userAuthentication = Integer.parseInt(temp.toString());
				System.out.println("Retrieveing userAuthentication successfully - all posts page");
			}catch(Exception e){
				System.out.println("Retrieveing userAuthentication fail - all posts page");
				e.printStackTrace();
			}
		}
	%>
	
	<form method="post" action="allPost">
		Search term <input type="text" name="txtSearch" placeholder="Search term">
		<input type="submit" name="btnSearch" value="Search">
	</form>
	<br>
	<br>
	
	<%
		if(null != searchResults){
			%>
					<table>
					 <tr>
					    <th>Title</th>
					    <th>Introduction</th>
					    <th>Date</th>
					    <th>Reading Count</th>
					    <th>Category</th>
					    <%
					    	if(0 != userAuthentication){
					    		%>
					    			 <th>Edit</th>
					   				 <th>Delete</th>
					    		<%
					    	}
					    %>
					  </tr>
			<%
			
			for(postModel post : searchResults){
				%>
				
				
					
					<tr>
					    <td><% out.println(post.getPostTitle()); %></td>
					    <td><% out.println(post.getPostIntroduction()); %></td>
					    <td><% out.println(post.getPostDate()); %></td>
					    <td><% out.println(post.getPostReadingCount()); %></td>
					    <td><%
					    	categoryModel category = post.getPostCategory();
					    	if(null != category){
					    		out.println(category.getCategoryName());
					    	}
					    %></td>
					    <%
					    if(0 != userAuthentication){
				    		%>
				    			<td>
				    				<form action="updatePost" method="POST"> 
				    					<input type="hidden" name="function" value="redirect">
				    					<input type="hidden" name="intPostID" value="<% out.print(post.getPostID()); %>">
				    					<input value="Edit" name="btnEdit" type="submit">
				    				</form>
				    			</td>
					    		<td>
					    			<form action="deletingPost" method="POST">
				    					<input type="hidden" name="intPostID" value="<% out.print(post.getPostID()); %>">
				    					<input value="Delete" name="btnDelete" type="submit">
				    				</form>
					    		</td>
				    		<%
				    	}
					    %>
					</tr>
				
	  				
  				<%
			}
			
			%>
				</table>
			<%
			
		}else{
			%>
				<h5>There is no search result</h5>
			<%
		}
	%>
	
	<hr>
	<br>

	<!-- Ending search section -->
	
	<!-- ADD NEW POST SECTION -->
	
	<%
		if(0 != userAuthentication){
			%>
					<h1><a href="addNewPost?function=redirect">Adding new post</a></h1>
	
					<hr><br>
			<%
		}
	%>

	
	<!-- ENDING ADD NEW POST SECTION -->
	
	
	
	<!-- TABLE FOR ALL POST RESULT -->
	
	<h1>All posts</h1>
	<table>
	  <tr>
	    <th>Title</th>
	    <th>Introduction</th>
	    <th>Date</th>
	    <th>Reading Count</th>
	    <th>Category</th>
	     <%
					    	if(0 != userAuthentication){
					    		%>
					    			 <th>Edit</th>
					   				 <th>Delete</th>
					    		<%
					    	}
					    %>
	  </tr>
	  
	  <!-- LOADING RESULTS INTO TABLE -->
	  <%
	  	if(null != posts){
	  		for(postModel post : posts){
	  			%>
	  				<tr>
					    <td><% out.println(post.getPostTitle()); %></td>
					    <td><% out.println(post.getPostIntroduction()); %></td>
					    <td><% out.println(post.getPostDate()); %></td>
					    <td><% out.println(post.getPostReadingCount()); %></td>
					    
					    <td><%
					    	categoryModel category = post.getPostCategory();
					    	if(null != category){
					    		out.println(category.getCategoryName());
					    	}
					    %></td>
					    <%
					    if(0 != userAuthentication){
				    		%>
				    			<td>
				    				<form action="updatePost" method="POST"> 
				    					<input type="hidden" name="function" value="redirect">
				    					<input type="hidden" name="intPostID" value="<% out.print(post.getPostID()); %>">
				    					<input value="Edit" name="btnEdit" type="submit">
				    				</form>
				    			</td>
					    		<td>
					    			<form action="deletingPost" method="POST">
				    					<input type="hidden" name="intPostID" value="<%= post.getPostID() %>">
				    					<input value="Delete" name="btnDelete" type="submit">
				    				</form>
					    		</td>
				    		<%
				    	}
					    %>
					</tr>
	  			<%
	  		}
	  	}
	  %>
	  
	</table>
	<hr>
	<br>
	
	<!-- COMING HOME SECTION -->
	<h1><a href="">HOME</a></h1>
	<h1><a href="login">Admin Portal</a></h1>


</body>
</html>