package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.databaseConnection;
import model.postModel;
import model.categoryModel;

public class updatePostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String REDIRECT_PAGE_SUCCESSFULLY = "allPost";
	private static final String REDIRECT_PAGE_ERROR = "WEB-INF/update_post_error.jsp";	
	private static final String REDIRECT_PAGE = "WEB-INF/update_post_page.jsp";

    public updatePostServlet() {
        super();
        System.out.println("updatePostServlet is running");
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = REDIRECT_PAGE_ERROR;
		
		//Getting parameter function
		String function = null;
		try {
			function = request.getParameter("function");
			System.out.println("Getting parameter 2 successfully - updatePost Servlet");
		}catch(Exception e) {
			System.out.println("Getting parameter fail - updatePost Servlet");
			e.printStackTrace();
		}
		
		//Getting parameter postID
		int postID = 0;
		try {
			postID = Integer.parseInt(request.getParameter("intPostID"));
			System.out.println("Getting parameter 1 successfully - updatePost Servlet");
		}catch(Exception e) {
			System.out.println("Getting parameter fail - updatePost Servlet");
			e.printStackTrace();
		}
		
		//Checking parameter for redirect purposes
		if(null != function) {
			if("redirect".equals(function)) {
				//Connect to database
				databaseConnection dcon = new databaseConnection();
				dcon.openDatabaseConnection();
				
				//Getting post object from database
				postModel post = null;
				if(0 != postID) {
					post = dcon.getPost(postID);
					System.out.println("Getting post successfully - updatePostServlet");
				}else {
					System.out.println("Getting post fail - updatePostServlet");
				}
				
				//Getting categories from database
				ArrayList<categoryModel> categories = dcon.gettingCategories();
				
				//Setting attribute
				request.setAttribute("post", post);
				request.setAttribute("categories", categories);
				
				//Setting path
				path = REDIRECT_PAGE;
				
				//Close database
				dcon.closeDatabaseConnection();
			}else if("updatePost".equals(function)) {
				//Getting parameter from update post page
				String postTitle = null;
				String postIntroduction = null;
				String postContent = null;
				int postCategoryID = 0;
				
				//Getting post title
				try {
					postTitle = request.getParameter("txtPostTitle");
					System.out.println("Getting post title successfully - updatePostServlet");
				}catch(Exception e) {
					System.out.println("Getting post title fail - updatePostServlet");
					e.printStackTrace();
				}
				
				//Getting post introduction
				try {
					postIntroduction = request.getParameter("txtPostIntroduction");
					System.out.println("Getting post introduction successfully - updatePostServlet");
				}catch(Exception e) {
					System.out.println("Getting post introduction fail - updatePostServlet");
					e.printStackTrace();
				}
				
				//Getting post content
				try {
					postContent = request.getParameter("txtPostContent");
					System.out.println("Getting post content successfully - updatePostServlet");
				}catch(Exception e) {
					System.out.println("Getting post content fail - updatePostServlet");
					e.printStackTrace();
				}
				
				//Getting post category ID
				try {
					postCategoryID = Integer.parseInt(request.getParameter("postCategoryID"));
					System.out.println("Getting post category ID successfully - updatePostServlet");
				}catch(Exception e) {
					System.out.println("Getting post category ID fail - updatePostServlet");
					e.printStackTrace();
				}
				
				//connect to database
				databaseConnection dcon = new databaseConnection();
				dcon.openDatabaseConnection();
				
				//update new edited post to database
				if(null != postContent && null != postIntroduction && null != postTitle && 0 != postID && 0 != postCategoryID) {
					if(0 != postContent.length() && 0 != postIntroduction.length() && 0 != postTitle.length()) {
						boolean result = dcon.updatePostFromDatabaseByPostID(postID, postTitle, postIntroduction, postContent, postCategoryID);
						if(true == result) {
							path = REDIRECT_PAGE_SUCCESSFULLY;
							System.out.println("Update post to database successfully - update post servlet");
						}
					}else{
						System.out.println("Update post to database fail - update post servlet");
					}
				}else {
					System.out.println("Update post to database fail - update post servlet");
				}
				
				//close connection to database
				dcon.closeDatabaseConnection();
			}
		}
		
		//Redirect to related pages
		request.getRequestDispatcher(path).forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
