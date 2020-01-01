package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.postModel;
import model.databaseConnection;
import model.commentModel;

public class postServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final String DIRECT_PAGE = "WEB-INF/post_content.jsp";
       
    public postServlet() {
        super();
        System.out.println("postServlet is running");
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Open database connection
		databaseConnection dcon = new databaseConnection();
		dcon.openDatabaseConnection();
		
		//Getting postID from previous jsp page
		postModel post = null;
		String temp = request.getParameter("postID");
		int postID = 0;
		ArrayList<commentModel> comments = null;
		
		try {
			postID = Integer.parseInt(temp);
			System.out.println("Getting postID = " + postID + " successfully - postServlet");
		}catch(Exception e) {
			System.out.println("Getting postID fail - postServlet");
			e.printStackTrace();
		}
		
		//Adding postReadingCount
		try {
			Object tmp = request.getAttribute("javax.servlet.forward.request_uri");
			String previous_URI = null;
			
			//Getting previous URI
			if(null != tmp) {
				try {
					previous_URI = tmp.toString();
					System.out.println("Getting previous URI successfullly");
				}catch(Exception e) {
					System.out.println("Getting previous URI fail");
					e.printStackTrace();
				}
			}
			
			System.out.println(previous_URI); //Testing purposes
			
			//Adding comment except from addingCommentServlet
			if(null == previous_URI) {
				dcon.addingPostReadingCount(postID);
				System.out.println("Adding postReadingCount to database successfully - postServlet");
			}
			
		}catch(Exception e) {
			System.out.println("Adding postReadingCount to database fail - postServlet");
			e.printStackTrace();
		}
		
		//Using postID for retrieving other information from database
		if (0 != postID)
		{
			//Retrieving posts
			try {
				post = dcon.getPost(postID);
				System.out.println("Getting post from postID successfully - postServlet");
			}catch(Exception e) {
				System.out.println("Getting post from postID fail - postServlet");
				e.printStackTrace();
			}
			
			//Retrieving comments
			try {
				comments = dcon.getComment(postID);
				System.out.println("Getting comments from postID successfully - postServlet");
			}catch(Exception e) {
				System.out.println("Getting comments from postID fail - postServlet");
				e.printStackTrace();
			}
		}
		
		//Attaching postModel and comments to request
		request.setAttribute("post", post);
		request.setAttribute("comments", comments);
		
		//Closing database connection
		dcon.closeDatabaseConnection();
		
		//Redirect to other post page
		request.getRequestDispatcher(DIRECT_PAGE).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
