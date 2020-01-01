package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.databaseConnection;

public class deletingPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String DIRECT_PAGE = "allPost";
	private static final String DIRECT_PAGE_ERROR = "WEB-INF/delete_post_error.jsp";

    public deletingPostServlet() {
        super();
        System.out.println("deletingPostServlet is running");
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = DIRECT_PAGE_ERROR;
		
		//Getting parameter
		int postID = 0;
		try {
			postID = Integer.parseInt(request.getParameter("intPostID"));
			System.out.println("Getting parameter successfully - deletingPost Servlet");
		}catch(Exception e) {
			System.out.println("Getting parameter fail - deletingPost Servlet");
			e.printStackTrace();
		}
		
		//Connect to database
		databaseConnection dcon = new databaseConnection();
		dcon.openDatabaseConnection();
		
		//Delete post in database
		boolean result = dcon.deletingPostByPostID(postID);
		if(true == result) {
			System.out.println("Deleting post successfully - deletingPost Servlet");
			path = DIRECT_PAGE;
		}else {
			System.out.println("Deleting post fail - deletingPost Servlet");
		}
		
		//Close database
		dcon.closeDatabaseConnection();
		
		//Redirect page
		request.getRequestDispatcher(path).forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}

}
