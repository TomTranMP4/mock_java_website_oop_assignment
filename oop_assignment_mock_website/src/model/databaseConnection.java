package model;

import java.util.ArrayList;

import java.sql.*;
import java.time.LocalDateTime;


import model.postModel;
import model.commentModel;
import model.categoryModel;

public class databaseConnection {
	
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DATABASE_LINK = "jdbc:mysql://localhost:3306/oop_assignment1";
	private static final String DATABASE_USERNAME = "root";
	private static final String DATABASE_PASSWORD = "root";
	
	private Connection databaseConnection = null;
	
	/*
	 * This function is responsible for loading JDBC Driver and return database connection
	 * Input: no input
	 * Output: return data type Connection (of a specific database)
	 * */
	public void openDatabaseConnection()
	{
		
		//Loading Driver
		try {
			Class.forName(JDBC_DRIVER);
			System.out.println("Loading JDBC Driver successfully -- class databaseConnection -- method openDatabaseConnection");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Loading JDBC Driver fail -- class databaseConnection -- method openDatabaseConnection");
		}
		
		//Creating database connection
		try {
			databaseConnection = DriverManager.getConnection(DATABASE_LINK, DATABASE_USERNAME, DATABASE_PASSWORD);
			System.out.println("Creat database connection successfully -- class databaseConnection -- method openDatabaseConnection");
		} catch (SQLException e) {
			System.out.println("Creat database connection fail -- class databaseConnection -- method openDatabaseConnection");
			e.printStackTrace();
		}
		
	}
	//Ending of openDatabaseConnection

	/*
	 * This function is responsible for closing database connection
	 * Users need to use this function after each time retrieving data from database
	 * Input: no input
	 * Output: no output
	 * */
	public void closeDatabaseConnection()
	{
		try {
			databaseConnection.close();
			System.out.println("DatabaseConnection closed successfully");
		} catch (SQLException e) {
			System.out.println("DatabaseConnection closed fail");
			e.printStackTrace();
			
		}
	}
	//Ending closeDatabaseConnection
	
	/*
	 * This function takes connection database from defaultServlet and retrieving the last three posts from database
	 * Input: limitation of results or number 0 if want everything in database
	 * Output: three postModel Objects in an arraylist
	 * or Null in case of there is no result or no database connection
	 * */
	public ArrayList<postModel> gettingPosts(int resultLimit)
	{
		//Variables
		ArrayList<postModel> posts = new ArrayList<postModel>();
		String txtQuery = null;
		
		System.out.println("Result Limit = " + resultLimit); //Testing
		
		//Preparing SQL query
		if (null != databaseConnection)
		{
			if (0 == resultLimit){
				txtQuery = "SELECT * FROM post ORDER BY postDate DESC;";
			}else {
				txtQuery = "SELECT * FROM post ORDER BY postDate DESC LIMIT ?;";
			}
			
			System.out.println("Query = " + txtQuery); //Testing
			
			//Executing SQL Query
			java.sql.PreparedStatement stm = null;
			ResultSet rs = null;
			
			
			try {
				stm = databaseConnection.prepareStatement(txtQuery);
				if (0 != resultLimit)
				{
					stm.setInt(1, resultLimit);
				}
				System.out.println("Preparing statement sucessfully - databaseConnection class - retrievingMainPagePost");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Preparing statement fail - databaseConnection class - retrievingMainPagePost");
				e.printStackTrace();
			}
			try {
				rs = stm.executeQuery();
				System.out.println("Getting resultset sucessfully - databaseConnection class - retrievingMainPagePost");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Getting resultset fail - databaseConnection class - retrievingMainPagePost");
				e.printStackTrace();
			}
			try {
				if (false == rs.wasNull())
				{
					while(rs.next())
					{
						postModel post = new postModel();
						post.setPostID(rs.getInt("postID"));
						post.setPostTitle(rs.getString("postTitle"));
						post.setPostIntroduction(rs.getString("postIntroduction"));
						post.setPostContent(rs.getString("postContent"));
						
						int categoryID = rs.getInt("postCategoryID");
						post.setPostCategoryID(categoryID);
						
						categoryModel category = gettingCategoryModelByID(categoryID);
						if(null != category) {
							post.setPostCategory(category);
						}
						
						
						
						post.setPostDate(rs.getString("postDate"));
						post.setPostReadingCount(rs.getInt("postReadingCount"));
						posts.add(post);
					}
					System.out.println("Retrieving data from database successfully - databaseConnection class - retrievingMainPagePost");
					return posts;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Retrieving data from database fail - databaseConnection class - retrievingMainPagePost");
				e.printStackTrace();
			} finally {
				try {
					rs.close();
					stm.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println("Returning null of retrievingMainPagePost - databaseConnection class - retrievingMainPagePost");
		return null;
	}
	//Ending of retrievingMainPagePost
	
	/*
	 * This function will return all information of a specific post from database based on postID
	 * Input: int postID
	 * Output: object postModel or null in case of non-result or error from database
	 * */
	public postModel getPost(int postID)
	{
		if(null != databaseConnection)
		{
			String txtQuery = "select * from post where postID = ? limit 1";
			java.sql.PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = databaseConnection.prepareStatement(txtQuery);
				ps.setInt(1, postID);
				System.out.println("PreparedStatement successfully - databaseConnection class - getPost function");
			} catch (SQLException e) {
				System.out.println("PreparedStatement fail - databaseConnection class - getPost function");
				e.printStackTrace();
			}
			try {
				rs = ps.executeQuery();
				System.out.println("Execution of query successfully - databaseConnection class - getPost function");
			} catch (SQLException e) {
				System.out.println("Execution of query fail - databaseConnection class - getPost function");
				e.printStackTrace();
			}
			if (null != rs)
			{
				try {
					if (false == rs.wasNull())
					{
						postModel post = null;
						while (rs.next())
						{
							post = new postModel();
							post.setPostID(rs.getInt("postID"));
							post.setPostTitle(rs.getString("postTitle"));
							post.setPostIntroduction(rs.getString("postIntroduction"));
							post.setPostContent(rs.getString("postContent"));
							
							
							
							int categoryID = rs.getInt("postCategoryID");
							post.setPostCategoryID(categoryID);
							
							categoryModel category = gettingCategoryModelByID(categoryID);
							if(null != category) {
								post.setPostCategory(category);
							}
							
							
							
							
							
							post.setPostDate(rs.getString("postDate"));
							post.setPostReadingCount(rs.getInt("postReadingCount"));
						}
						System.out.println("Getting post by postID successfully - databaseConnection class - getPost function");
						return post;
					}
				} catch (SQLException e) {
					System.out.println("Getting post by postID fail - databaseConnection class - getPost function");
					e.printStackTrace();
				} finally {
					try {
						ps.close();
						rs.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		System.out.println("Returning null value - databaseConnection class - getPost function");
		return null;
	}
	//Ending getPost function
	
	/*
	 * This function is responsible for getting object comment from database based on postID
	 * Input: postID
	 * Output: array list of objects commentModel or null if error or nothing
	 * */
	public ArrayList<commentModel> getComment(int postID)
	{
		
		if (null == databaseConnection)
		{
			System.out.println("databaseConnection is null");
			return null;
		}
		
		ArrayList<commentModel> comments = new ArrayList<commentModel>();
		java.sql.PreparedStatement ps = null;
		ResultSet rs = null;
		String txtQuery = "select * from comment where postID = ?";
		
		try {
			ps = databaseConnection.prepareStatement(txtQuery);
			ps.setInt(1, postID);
			System.out.println("PreparedStatement successfully - databaseConnection class - getComment function");
		} catch (SQLException e) {
			System.out.println("PreparedStatement fail - databaseConnection class - getComment function");
			e.printStackTrace();
		}
		
		try {
			rs = ps.executeQuery();
			System.out.println("ResultSet successfully - databaseConnection class - getComment function");
		} catch (SQLException e) {
			System.out.println("ResultSet fail - databaseConnection class - getComment function");
			e.printStackTrace();
		}
		
		try {
			if (null != rs && false == rs.wasNull())
			{
				while (rs.next())
				{
					commentModel comment = new commentModel();
					comment.setCommentID(rs.getInt("commentID"));
					comment.setPostID(postID);
					comment.setCommentContent(rs.getString("commentContent"));
					comment.setCommentUsername(rs.getString("commentUserName"));
					comment.setCommentDate(rs.getString("commentDate"));
					comments.add(comment);
				}
				System.out.println("Getting comments from database successfully - databaseConnection class - getComment function");
				return comments;
			}
		} catch (SQLException e) {
			System.out.println("Getting comments from database fail - databaseConnection class - getComment function");
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("Returning null object - databaseConnection class - getComment function");
		return null;
	}
	//Ending get comments
	
	/*
	 * This function is responsible for adding reading post count to database
	 * Input: postID
	 * Output: updating post reading count to database
	 * */
	public void addingPostReadingCount (int postID)
	{
		if (null != databaseConnection)
		{
			java.sql.PreparedStatement ps = null;
			String txtQuery = "update post set postReadingCount = postReadingCount + 1 where postID = ?";
			
			try {
				ps = databaseConnection.prepareStatement(txtQuery);
				ps.setInt(1, postID);
				ps.execute();
				System.out.println("PreparedStatement successfully - databaseConnection class - addingPostReadingCount function");
			} catch (SQLException e) {
				System.out.println("PreparedStatement fail - databaseConnection class - addingPostReadingCount function");
				e.printStackTrace();
			} finally {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else
		{
			System.out.println("Database Connection is closed");
		}
	}
	//Ending addingPostReadingCount
	
	/*
	 * This function will add a comment to database based on postID
	 * Input: postID, commentContent, commentUserName
	 * Output: adding comment to table comment of database
	 * */
	public void addingPostComment(int postID, String commentUsername, String commentContent)
	{
		if (null != databaseConnection)
		{
			//Variables
			java.sql.PreparedStatement ps = null;
			String toDay = LocalDateTime.now().toString();
			String txtQuery = "insert into comment (postID, commentContent, commentUserName, commentDate) values (?, ?, ?, ?)";
			
			//PreparedStatement
			try {
				ps = databaseConnection.prepareStatement(txtQuery);
				ps.setInt(1, postID);
				ps.setString(2, commentContent);
				ps.setString(3, commentUsername);
				ps.setString(4, toDay);
				ps.execute();
				System.out.println("PreparedStatement successfully - databaseConnection class - addingPostComment function");
			} catch (SQLException e) {
				System.out.println("PreparedStatement fail - databaseConnection class - addingPostComment function");
				e.printStackTrace();
			} finally {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else
		{
			System.out.println("Database connection is closed");
		}
	}
	//Ending addingPostComment
	
	/*
	 * The function is responsible for authentication username and password
	 * Input: username and password as String
	 * Output: boolean result
	 * */
	public boolean userAuthentication(String txtusername, String txtpassword)
	{
		if (null != databaseConnection)
		{
			java.sql.PreparedStatement ps = null;
			ResultSet rs = null;
			String txtQuery = "SELECT COUNT(*) as rowCount FROM user WHERE username = ? and password = ?";
			
			try {
				ps = databaseConnection.prepareStatement(txtQuery);
				ps.setString(1, txtusername);
				ps.setString(2, txtpassword);
				System.out.println("PreparedStatement successfully - databaseConnection class - userAuthentication function");
			} catch (SQLException e) {
				System.out.println("PreparedStatement fail - databaseConnection class - userAuthentication function");
				e.printStackTrace();
			}
			
			try {
				rs = ps.executeQuery();
				System.out.println("ResultSet successfully - databaseConnection class - userAuthentication function");
			} catch (SQLException e) {
				System.out.println("ResultSet fail - databaseConnection class - userAuthentication function");
				e.printStackTrace();
			}
			
			try {
				if (null != rs && false == rs.wasNull())
				{
					while (rs.next())
					{
						int rowCount = rs.getInt("rowCount");
						if (1 == rowCount)
						{
							System.out.println("User authentication is running ok - databaseConnection class - userAuthentication function");
							return true;
						}
					}
				}
			} catch (SQLException e) {
				System.out.println("User authentication is running fail - databaseConnection class - userAuthentication function");
				e.printStackTrace();
			} finally {
				try {
					ps.close();
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else
		{
			System.out.println("Database Connection fail - databaseConnection class - userAuthentication function");
		}
		System.out.println("User authentication return false - databaseConnection class - userAuthentication function");
		return false;
	}
	//Ending userAuthentication
	
	/*
	 * This function is responsible for getting user's information for aboutPage
	 * Input: no input
	 * Output: object userModel
	 * */
	public userModel gettingUserInformation()
	{
		if (null != databaseConnection)
		{
			java.sql.PreparedStatement ps = null;
			ResultSet rs = null;
			String txtQuery = "SELECT * FROM user";
			
			try {
				ps = databaseConnection.prepareStatement(txtQuery);
				System.out.println("PreparedStatement successfully - databaseConnection - gettingUserInformation");
			} catch (SQLException e) {
				System.out.println("PreparedStatement fail - databaseConnection - gettingUserInformation");
				e.printStackTrace();
			}
			
			try {
				rs = ps.executeQuery();
				System.out.println("ResultSet successfully - databaseConnection - gettingUserInformation");
			} catch (SQLException e) {
				System.out.println("ResultSet fail - databaseConnection - gettingUserInformation");
				e.printStackTrace();
			}
			
			try {
				if (null != rs && false == rs.wasNull())
				{
					userModel user = new userModel();
					while(rs.next())
					{
						user.setUserID(rs.getInt("userID"));
						user.setFirstName(rs.getString("firstName"));
						user.setLastName(rs.getString("lastName"));
						user.setContactNumber(rs.getString("contactNumber"));
						user.setEmail(rs.getString("email"));
						user.setAbout(rs.getString("about"));
						user.setUserEditFlag(rs.getBoolean("userEditFlag"));
					}
					System.out.println("gettingUserInformation returns object - databaseConnection - gettingUserInformation");
					return user;
				}
			} catch (SQLException e) {
				System.out.println("SQL Execution fail - databaseConnection - gettingUserInformation");
				e.printStackTrace();
			} finally {
				try {
					rs.close();
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println("gettingUserInformation returns null - databaseConnection - gettingUserInformation");
		return null;
	}
	//Ending gettingUserInformation
	
	/*
	 * This function responsible for search by title
	 * Input: Search Title of post by String
	 * Output: ArrayList<postModel> as a search result
	 * */
	public ArrayList<postModel> searchPostByTitle(String txtSearch){
		
		ArrayList<postModel> searchResults = null;
		java.sql.PreparedStatement ps = null;
		ResultSet rs = null;
		String txtQuery = "SELECT * FROM post WHERE postTitle LIKE ?";
		
		if(null != databaseConnection)
		{
			//PreparedStatement
			try {
				ps = databaseConnection.prepareStatement(txtQuery);
				String temp = "%" + txtSearch + "%";
				ps.setString(1, temp);
				System.out.println(temp);
				System.out.println("PreparedStatement successfully - databaseConnection - searchPostByTitle");
			} catch (SQLException e) {
				System.out.println("PreparedStatement fail - databaseConnection - searchPostByTitle");
				e.printStackTrace();
			}
			
			
			
			//ResultSet
			try {
				rs = ps.executeQuery();
				System.out.println("SQL Execution successfully - databaseConnection - searchPostByTitle");
			} catch (SQLException e) {
				System.out.println("SQL Execution fail - databaseConnection - searchPostByTitle");
				e.printStackTrace();
			}
			//Getting results
			if(null != rs) {
				try {
					if(false == rs.wasNull()) {
						searchResults = new ArrayList<postModel>();
						while(rs.next()) {
							postModel post = new postModel();
							post.setPostID(rs.getInt("postID"));
							post.setPostTitle(rs.getString("postTitle"));
							post.setPostIntroduction(rs.getString("postIntroduction"));
							post.setPostContent(rs.getString("postContent"));
							
							
							
							int categoryID = rs.getInt("postCategoryID");
							post.setPostCategoryID(categoryID);
							
							categoryModel category = gettingCategoryModelByID(categoryID);
							if(null != category) {
								post.setPostCategory(category);
							}
							
							
							
							
							post.setPostDate(rs.getString("postDate"));
							post.setPostReadingCount(rs.getInt("postReadingCount"));
							searchResults.add(post);
						}
						if(0 != searchResults.size())
						{
							System.out.println("Retrieving search result successfully - databaseConnection - searchPostByTitle");
							return searchResults;
						}
					}
				} catch (SQLException e) {
					System.out.println("Retrieving search result fail - databaseConnection - searchPostByTitle");
					e.printStackTrace();
				}
			}
		}
		
		System.out.println("searchPostByTitle returns NULL");
		return null;
		
	}
	//Ending searchPostByTitle
	
	/*
	 * This function responsible for getting cateroryTitle from categoryID from database
	 * Input: categoryID as Integer
	 * Output: categoryModel
	 * */
	public categoryModel gettingCategoryModelByID(int categoryID) {
		
		if(null != databaseConnection) {
			
			java.sql.PreparedStatement ps = null;
			ResultSet rs = null;
			String txtQuery = "SELECT category.*, COUNT(*) as rowCount FROM category WHERE categoryID = ?";
			categoryModel category = null;
			
			try {
				ps = databaseConnection.prepareStatement(txtQuery);
				ps.setInt(1, categoryID);
				System.out.println("PreparedStatement successfully - databaseConnection - gettingCategoryModelByID");
			} catch (SQLException e) {
				System.out.println("PreparedStatement fail - databaseConnection - gettingCategoryModelByID");
				e.printStackTrace();
			}
			
			try {
				rs = ps.executeQuery();
				System.out.println("ResultSet successfully - databaseConnection - gettingCategoryModelByID");
			} catch (SQLException e) {
				System.out.println("ResultSet fail - databaseConnection - gettingCategoryModelByID");
				e.printStackTrace();
			}
			
			if(null != rs){
				try {
					if(false == rs.wasNull()) {
						int rowCount = 0;
						category = new categoryModel();
						while(rs.next()) {
							category.setCategoryID(categoryID);
							category.setCategoryName(rs.getString("categoryName"));
							rowCount = rs.getInt("rowCount");
						}
						if(0 != rowCount) {
							System.out.println("Retrieving category successfully - databaseConnection - gettingCategoryModelByID");
							return category;
						}
					}
				} catch (SQLException e) {
					System.out.println("Retrieving category fail - databaseConnection - gettingCategoryModelByID");
					e.printStackTrace();
				}
			}
			
		}
		System.out.println("gettingCategoryModelByID return NULL - databaseConnection - gettingCategoryModelByID");
		return null;
		
	}
	//Ending gettingCategoryModelByID
	
	/*
	 * This function is responsible for adding new post
	 * Input: postTitle, postIntroduction, postContent, postCategoryID, postDate
	 * Output: adding these information to database
	 * */
	public boolean addingNewPost(String postTitle, String postIntroduction, String postContent, int postCategoryID) {
		
		if(null!=databaseConnection) {
			java.sql.PreparedStatement ps = null;
			String txtQuery = "INSERT INTO post (postTitle, postIntroduction, postContent, postCategoryID, postDate) VALUES (?, ?, ?, ?, ?);";
			try {
				ps = databaseConnection.prepareStatement(txtQuery);
				ps.setString(1, postTitle);
				ps.setString(2, postIntroduction);
				ps.setString(3, postContent);
				ps.setInt(4, postCategoryID);
				ps.setString(5, LocalDateTime.now().toString());
				int updateResult = ps.executeUpdate();
				if(0!=updateResult) {
					System.out.println("PreparedStatement successfully - databaseConnection - addNewPost");
					return true;
				}
			} catch (SQLException e) {
				System.out.println("PreparedStatement fail - databaseConnection - addNewPost");
				e.printStackTrace();
			}
		}
		System.out.println("Adding new post into database false - databaseConnection - addNewPost");
		return false;
	}
	//Ending addingNewPost
	
	/*
	 * This function is responsible for adding new category
	 * Input: categoryName
	 * Output: Adding new category to database
	 * */
	public boolean addingNewCategory(String categoryName) {
		if(null != databaseConnection) {
			if(null != categoryName) {
				if(0 != categoryName.length()) {
					java.sql.PreparedStatement ps = null;
					String txtQuery = "INSERT INTO category (categoryName) VALUES (?);";
					try {
						ps = databaseConnection.prepareStatement(txtQuery);
						ps.setString(1, categoryName);
						int returnResult = ps.executeUpdate();
						if(0 != returnResult) {
							System.out.println("addingNewCategory return TRUE - databaseConnection - addingNewCategory");
							return true;
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		System.out.println("addingNewCategory return FALSE - databaseConnection - addingNewCategory");
		return false;
	}
	//Ending addingNewCategory
	
	
	/*
	 * This function is responsible for update user's contact information
	 * Input: int userID, String firstName, String lastName, String contactNumber, String email, String about
	 * Output: update other information to database
	 * */
	public boolean updateUserContact(int userID, String firstName, String lastName, String contactNumber, String email, String about) {
		if(null != databaseConnection && 0 != userID && null != firstName && null != lastName && null != contactNumber && null != email && null != about) {
			if(0 != firstName.length() && 0 != lastName.length() && 0 != contactNumber.length() && 0 != email.length() && 0 != about.length()) {
				java.sql.PreparedStatement ps = null;
				String txtQuery = "UPDATE user "
						+ "SET firstName = ?, lastName = ?, contactNumber = ?, email = ?, about = ? "
						+ "WHERE userID = ?";
				try {
					ps = databaseConnection.prepareStatement(txtQuery);
					ps.setString(1, firstName);
					ps.setString(2, lastName);
					ps.setString(3, contactNumber);
					ps.setString(4, email);
					ps.setString(5, about);
					ps.setInt(6, userID);
					int returnResult = ps.executeUpdate();
					if (0 != returnResult) {
						System.out.println("updateUserContact return TRUE - databaseConnection - gettingCategoryModelByID");
						return true;
					}
				} catch (SQLException e) {
					System.out.println("updateUserContact return FALSE - databaseConnection - gettingCategoryModelByID");
					e.printStackTrace();
				}
			}
		}
		System.out.println("updateUserContact return FALSE - databaseConnection - gettingCategoryModelByID");
		return false;
	}
	//Ending updateUserContact
	
	/*
	 * This function is responsible for update username and password
	 * Input: int userID, String username, String password
	 * Output: true or false based on the result of database
	 * */
	public boolean updateLogInInformation(int userID, String username, String password) {
		if(null != databaseConnection && null!= username && null != password && 0 != userID) {
			if(0 != username.length() && 0 != password.length()) {
				java.sql.PreparedStatement ps = null;
				String txtQuery = "UPDATE user "
						+ "SET username = ?, password = ? "
						+ "WHERE userID = ?";
				try {
					ps = databaseConnection.prepareStatement(txtQuery);
					ps.setString(1, username);
					ps.setString(2, password);
					ps.setInt(3, userID);
					int returnResult = ps.executeUpdate();
					if(0 != returnResult) {
						System.out.println("updateLogInInformation returns TRUE - databaseConnection - updateLogInInformation");
						return true;
					}
				} catch (SQLException e) {
					System.out.println("updateLogInInformation returns FALSE - databaseConnection - updateLogInInformation");
					e.printStackTrace();
				}
			}
		}
		System.out.println("updateLogInInformation returns FALSE - databaseConnection - updateLogInInformation");
		return false;
	}
	//Ending updateLogInInformation
	
	/*
	 * This function is responsible for getting all categoryModel
	 * Input: no input
	 * Output: ArrayList<categoryModel> list of all category in database or NULL
	 * */
	public ArrayList<categoryModel> gettingCategories(){
		//Checking input and variables
		if(null != databaseConnection) {
			java.sql.PreparedStatement ps = null;
			ResultSet rs = null;
			String txtQuery = "SELECT * FROM category";
			//PreparedStatement
			try {
				ps = databaseConnection.prepareStatement(txtQuery);
				System.out.println("PreparedStatement successfully - databaseConnection - gettingCategories");
			} catch (SQLException e) {
				System.out.println("PreparedStatement fail - databaseConnection - gettingCategories");
				e.printStackTrace();
			}
			//ResultSet
			try {
				rs = ps.executeQuery();
				System.out.println("ResultSet successfully - databaseConnection - gettingCategories");
			} catch (SQLException e) {
				System.out.println("ResultSet fail - databaseConnection - gettingCategories");
				e.printStackTrace();
			}
			//Checking results
			try {
				if(null != rs && false == rs.wasNull()) {
					ArrayList<categoryModel> categories = new ArrayList<categoryModel>();
					while(rs.next()) {
						int categoryID = 0;
						String categoryName = null;
						try {
							categoryID = rs.getInt("categoryID");
							categoryName = rs.getString("categoryName");
							System.out.println("Casting category from database successfully - databaseConnection - gettingCategories");
						}catch(Exception e) {
							System.out.println("Casting category from database fail - databaseConnection - gettingCategories");
							e.printStackTrace();
						}
						if(0 != categoryID && null != categoryName) {
							categoryModel category = new categoryModel(categoryID, categoryName);
							categories.add(category);
						}
					}
					if(0 != categories.size()) {
						System.out.println("Getting category from database successfully - databaseConnection - gettingCategories");
						return categories;
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("gettingCategories returns NULL - databaseConnection - gettingCategories");
		return null;
	}
	//Ending gettingCategories
	
	/*
	 * This function is responsible for deleting category from database
	 * Input: categoryID
	 * Output: TRUE - delete category successfully or FALSE - fail to delete category
	 * */
	public boolean deletingCategory(int categoryID)
	{
		
		//Checking database connection
		if(null != databaseConnection)
		{
			//Checking categoryID
			if(0 != categoryID)
			{
				java.sql.PreparedStatement ps = null;
				String txtQuery = "DELETE FROM category WHERE categoryID = ?";
				//PreparedStatement section
				try {
					ps = databaseConnection.prepareStatement(txtQuery);
					ps.setInt(1, categoryID);
					System.out.println("PreparedStatement successfully - databaseConnection - deletingCategory");
				} catch (SQLException e) {
					System.out.println("PreparedStatement fail - databaseConnection - deletingCategory");
					e.printStackTrace();
				}
				//Execution from database
				try {
					int returnResult = ps.executeUpdate();
					if(0 != returnResult)
					{
						System.out.println("deletingCategory returns true");
						return true;
					}
				} catch (SQLException e) {
					System.out.println("Execution fail - databaseConnection - deletingCategory");
					e.printStackTrace();
				}
			}
		}
		
		System.out.println("deletingCategory returns false");
		return false;
	}
	//Ending deletingCategory
	
	/*
	 * This function is responsible for deleting a post from database
	 * Input: postID
	 * Output: TRUE - post was deleted from database, FALSE - fail to delete a post from database
	 * */
	public boolean deletingPostByPostID(int postID) {
		
		//Checking database connection and input
		if(null != databaseConnection && 0 != postID) {
			//PreparedStatement
			java.sql.PreparedStatement ps = null;
			String sql = "DELETE FROM post WHERE postID = ?";
			try {
				ps = databaseConnection.prepareStatement(sql);
				ps.setInt(1, postID);
				int returnResult = ps.executeUpdate();
				if(0 != returnResult) {
					System.out.println("deletingPostByPostID returns TRUE - databaseConnection");
					return true;
				}
			} catch (SQLException e) {
				System.out.println("Execution to delete post from database fail - deletingPostByPostID - databaseConnection");
				e.printStackTrace();
			}
		}
		
		System.out.println("deletingPostByPostID returns FALSE - databaseConnection");
		return false;
	}
	//Ending deletingPostByPostID
	
	/*
	 * This function is responsible for altering post
	 * Input: object post
	 * Output: TRUE - update new post to previous post from database, FALSE - fail to update
	 * */
	public boolean updatePostFromDatabaseByPostID(int postID, String postTitle, String postIntroduction, String postContent, int postCategoryID) {
		
		//Checking database connection
		if(null != databaseConnection && 0 != postID && null != postTitle && null != postIntroduction && null != postContent && 0 != postCategoryID) {
			if(0 != postTitle.length() && 0 != postIntroduction.length() && 0 != postContent.length()) {
				//PreparedStatement
				java.sql.PreparedStatement ps = null;
				String sql = "UPDATE post SET postTitle = ?, postIntroduction = ?, postContent = ?, postCategoryID = ?, postDate = ? WHERE postID = ?";
				try {
					ps = databaseConnection.prepareStatement(sql);
					ps.setString(1, postTitle);
					ps.setString(2, postIntroduction);
					ps.setString(3, postContent);
					ps.setInt(4, postCategoryID);
					ps.setString(5, LocalDateTime.now().toString());
					ps.setInt(6, postID);
					int result = ps.executeUpdate();
					if(0 != result) {
						System.out.println("updatePostFromDatabaseByPostID returns TRUE - databaseConnection");
						return true;
					}
				} catch (SQLException e) {
					System.out.println("updatePostFromDatabaseByPostID execution fail - databaseConnection");
					e.printStackTrace();
				}
			}
		}
		
		System.out.println("updatePostFromDatabaseByPostID returns FALSE - databaseConnection");
		return false;
	}
	//Ending updatePostFromDatabaseByPostID
}
