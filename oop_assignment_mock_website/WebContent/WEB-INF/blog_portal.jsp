<!DOCTYPE html>
<%@page import="javax.servlet.jsp.tagext.TryCatchFinally"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="model.postModel" %>
<%@ page import="model.categoryModel" %>
<html>
<head>

	<title>Blog Portal</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="css/blog_portal_style.css">
	
	<style type="text/css">
		ul {
			float: left;
			padding-left: 0px;
			margin-left: 0px;
		}
		
		li {
  			float: left;
  			padding: 2px;
  			display: block;
		}
	</style>
	
</head>

<body>

	<!-- HEADER CONTENT -->
	<div class="header">
  		<h2>This the blog of Ngoc Nhuan HUYNH</h2>
  		<h2>Student ID: s1458633</h2>
	</div>

<div class="row">
  <div class="leftcolumn">
  
  	<!-- POST CONTENT SECTION -->
  	<!--
    <div class="card">
      <h2>TITLE HEADING</h2>
      <h5>Title description, Dec 7, 2017</h5>
      <p>Some text..</p>
      <p>Sunt in culpa qui officia deserunt mollit anim id est laborum consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco.</p>
    </div>
    <div class="card">
      <h2>TITLE HEADING</h2>
      <h5>Title description, Sep 2, 2017</h5>
      <p>Some text..</p>
      <p>Sunt in culpa qui officia deserunt mollit anim id est laborum consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco.</p>
    </div>
    <div class="card">
      <h2>TITLE HEADING</h2>
      <h5>Title description, Dec 7, 2017</h5>
      <p>Some text..</p>
      <p>Sunt in culpa qui officia deserunt mollit anim id est laborum consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco.</p>
    </div>
     -->
     <!-- JSP Code for listing posts -->
    <%
    	ArrayList<postModel> posts = null;
    	try{
    		posts = (ArrayList<postModel>) request.getAttribute("posts");
    		System.out.println("Getting arraylist<postModel> successfully - blog_portal page");
    	}catch(Exception e) {
    		System.out.println("Getting arraylist<postModel> fail - blog_portal page");
    		e.printStackTrace();
    	}
    	if (null != posts && false == posts.isEmpty())
    	{
    		for(postModel post : posts)
    		{
    			%>
    			<div class="card">
      				<h2><a href="post?postID=<% out.print(post.getPostID()); %>"><% out.println(post.getPostTitle()); %></a></h2>
      				<h5><% out.println(post.getPostDate());%></h5>
      				<h5><% out.println("Post reading count: " + post.getPostReadingCount()); %></h5>
      				<h5><%
      					categoryModel category = post.getPostCategory();
      					if(null != category){
      						out.println(category.getCategoryName());
      					}
      				%></h5>
      				<p><%out.println(post.getPostIntroduction()); %></p>
    			</div>
    			<%
    		}
    	}else{
    		
    	}
    %>
    
    
    
  </div>
  <!-- ENDING POST CONTENT SECTION -->
  
  <!-- EXTERNAL MENU OPTIONS -->
  <div class="rightcolumn">
    <div class="card">
      <h2><a href="about">About me</a></h2>
      <div class="fakeimg" style="height:100px;">Image</div>
    </div>
    <div class="card">
      <h3><a href="allPost">All post</a></h3>
    </div>
    <div class="card">
      <h3><a href="login">Admin Portal</a></h3>
    </div>
  </div>
  <!-- ENDING EXTERNAL MENU OPTIONS -->
  
</div>

<!-- DIVIDING PAGE SECTION 
<div class="leftcolumn">
  <ul>
  	<%/*
  		int i;
  		for(i = 0; i < 1000; i++){
  			out.println("<li><a href='test'>" + i + "</a></li>");
  		}
  	*/%>
  </ul>
</div>
-->

</body>
</html>
