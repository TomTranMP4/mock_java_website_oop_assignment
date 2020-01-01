<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="model.categoryModel" %>
    <%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add new category</title>
</head>
<body>

	<!-- VIEWING ALL CATEGORIES SECTION -->
	<h1>Categories</h1>
	<%
		ArrayList<categoryModel> categories = null;
		try{
			categories = (ArrayList<categoryModel>)request.getAttribute("categories");
			System.out.println("Getting categories successfully - add new category page");
		}catch(Exception e){
			System.out.println("Getting categories fail - add new category page");
			e.printStackTrace();
		}
		if(null != categories){
			%>
				<table>
					<tr>
						<th>Categogy ID</th>
						<th>Categogy Name</th>
						<th>Delete</th>
					</tr>
					
					<%
						for(categoryModel category : categories){
							%>
								<tr>
									<td><% out.println(category.getCategoryID()); %></td>
									<td><% out.print(category.getCategoryName()); %></td>
									<!-- Loading categoryID for deleting purpose in database -->
									<td>
										<form action="deletingCategory" method="POST">
											<input type=hidden name="intCategoryID" value="<% out.print(category.getCategoryID()); %>"> 
											<input type="submit" value="Delete" name="btnDelete">
										</form>
									</td>
								</tr>
							<%
						}
					%>
					
				</table>
				<br>
				<hr>
			<%
		}
	%>
	<!-- ENDING VIEWING ALL CATEGORIES SECTION -->
	
	
	
	
	
	<!-- ADDING NEW CATEGORY SECTION -->
	<h1>Add new category</h1>
	<hr>
	<form action="addNewCategory" method="post">
		Category Name: <input name="txtCategoryName" type="text"><br>
		<input type="hidden" value="addNewCategory" name="function">
		<input type="submit" value="Submit">
	</form>
	<!-- ENDING ADDING NEW CATEGORY SECTION -->
	
	
	
	<br>
	<hr>
	
	
	<!-- HOME -->
	<h1><a href="">Home</a></h1>
	<h1><a href="login">Admin Portal</a></h1>
	
	
</body>
</html>