package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import model.databaseConnection;
import model.postModel;

public class defaultServlet extends HttpServlet {
	
	private static final String DEFAULT_PATH = "WEB-INF/blog_portal.jsp";
	private static final long serialVersionUID = 1L;
	private static final int NUMBER_OF_POSTS_FOR_MAINPAGE = 3;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Testing section
		System.out.println("DefaultServlet is running");
		
		//Connecting to database
		databaseConnection dcon = new databaseConnection();
		dcon.openDatabaseConnection();
		
		//Retrieving data from database
		ArrayList<postModel> posts = dcon.gettingPosts(NUMBER_OF_POSTS_FOR_MAINPAGE);
		request.setAttribute("posts", posts);
		
		//Closing database connection
		dcon.closeDatabaseConnection();
		
		//Redirect to blog_portal page
		request.getRequestDispatcher(DEFAULT_PATH).forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
