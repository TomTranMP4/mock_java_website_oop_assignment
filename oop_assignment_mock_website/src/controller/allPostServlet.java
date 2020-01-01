package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.postModel;
import model.databaseConnection;

public class allPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String REDIRECT_PAGE = "WEB-INF/all_blog.jsp";
       
    public allPostServlet() {
        super();
        System.out.println("AllPostServlet is fucking running, mates");
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Getting search information from all post page
		String txtSearch = null;
		Object tmp = request.getParameter("txtSearch");
		if(null != tmp) {
			try {
				txtSearch = tmp.toString();
				System.out.println("Getting search term successfully - allPostServlet");
			}catch(Exception e) {
				System.out.println("Getting search term fail - allPostServlet");
				e.printStackTrace();
			}
		}
		
		//Loading database connection
		databaseConnection dcon = new databaseConnection();
		dcon.openDatabaseConnection();
		
		//Getting all posts from database
		ArrayList<postModel> posts = null;
		posts = dcon.gettingPosts(0);
		
		//Getting searchResult from database
		ArrayList<postModel> searchResults = null;
		if(null != txtSearch)
		{
			if(0 != txtSearch.length()) {
				searchResults = dcon.searchPostByTitle(txtSearch);
			}
		}
		
		//Close database connection
		dcon.closeDatabaseConnection();
		
		//Redirect to allPost page
		request.setAttribute("posts", posts);
		request.setAttribute("searchResults", searchResults);
		request.getRequestDispatcher(REDIRECT_PAGE).forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
		
	}

}
