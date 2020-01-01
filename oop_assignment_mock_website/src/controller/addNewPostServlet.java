package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.databaseConnection;
import model.categoryModel;

public class addNewPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String ADDING_NEW_POST_PAGE = "WEB-INF/addingNewPost.jsp";
	private static final String ADMIN_PAGE = "allPost";
	private static final String ADD_NEW_POST_ERROR = "WEB-INF/addingNewPostError.jsp";

    public addNewPostServlet() {
        super();
        System.out.println("addNewPostServlet is running");
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String function = null;
		try {
			function = request.getParameter("function");
			System.out.println("Getting parameter function successfully - addnewpostServlet");
		}catch(Exception e) {
			System.out.println("Getting parameter function fail - addnewpostServlet");
			e.printStackTrace();
		}
		if(null != function) {
			if(function.equals("redirect")) {
				//Opendatabase connection
				databaseConnection dcon = new databaseConnection();
				dcon.openDatabaseConnection();
				//Getting categoryModel
				ArrayList<categoryModel> categories = dcon.gettingCategories();
				if(null != categories) {
					request.setAttribute("categories", categories);
				}
				//Close database connection
				dcon.closeDatabaseConnection();
				//Redirect to adding new post page
				request.getRequestDispatcher(ADDING_NEW_POST_PAGE).forward(request, response);
			}else if(function.equals("addNewPost")) {
				int redirect = 0; // 0 - error 1 - admin portal
				String postTitle = null;
				String postIntroduction = null;
				String postContent = null;
				int postCategoryID = 0;
				//Getting parameters
				try {
					postTitle = request.getParameter("postTitle");
					postIntroduction = request.getParameter("postIntroduction");
					postContent = request.getParameter("postContent");
					postCategoryID = Integer.parseInt(request.getParameter("postCategoryID"));
					System.out.println("Getting parameter for addingNewPost function successfully - addnewpostServlet");
				}catch(Exception e) {
					System.out.println("Getting parameter for addingNewPost function fail - addnewpostServlet");
					e.printStackTrace();
				}
				//open database connection
				databaseConnection dcon = new databaseConnection();
				dcon.openDatabaseConnection();
				//Updating database
				if(null != postTitle && null != postIntroduction && null != postContent && 0 != postCategoryID) {
					boolean updateResult = dcon.addingNewPost(postTitle, postIntroduction, postContent, postCategoryID);
					if(updateResult) {
						System.out.println("Adding new post to database successfully");
						redirect = 1;
					}
				}
				//close database connection
				dcon.closeDatabaseConnection();
				//Returning to admin portal
				if(0 != redirect) {
					request.getRequestDispatcher(ADMIN_PAGE).forward(request, response);
				}else {
					request.getRequestDispatcher(ADD_NEW_POST_ERROR).forward(request, response);
				}
			}
		}else {
			request.getRequestDispatcher(ADMIN_PAGE).forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
