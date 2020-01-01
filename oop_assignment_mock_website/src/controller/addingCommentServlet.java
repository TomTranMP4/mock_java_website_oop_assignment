package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.databaseConnection;

public class addingCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public addingCommentServlet() {
        super();
        System.out.println("addingCommentServlet is running");
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Variables
		String txtComment = null;
		String txtUsername = null;
		int postID = 0;
		
		//Retrieving variables from post_content jsp page
		try 
		{
			txtComment = request.getParameter("txtComment");
			txtUsername = request.getParameter("txtUsername");
			postID = Integer.parseInt(request.getParameter("intPostID"));
			System.out.println("Retrieve variables from post_content_page successfully");
		}
		catch(Exception e)
		{
			System.out.println("Can not retrieve variables from post_content_page");
			e.printStackTrace();
		}
		
		//Testing 
		System.out.println(txtComment + " " + txtUsername + " " + postID);
		
		//Open database connection
		databaseConnection dcon = new databaseConnection();
		dcon.openDatabaseConnection();
		
		//Adding comment information to table comment in database
		if (0 != postID && null != txtComment && null != txtUsername)
		{
			if (0 != txtComment.length() && 0 != txtUsername.length())
			{
				dcon.addingPostComment(postID, txtUsername, txtComment);
				System.out.println("Adding comment to table comment successfully - addingCommentServlet");
			}
		}
		else
		{
			System.out.println("Adding comment to table comment fail - addingCommentServlet");
		}
		
		//Close database connection
		dcon.closeDatabaseConnection();
		
		//Redirect back to post_contentPage
		String postPage = "/post?postID=" + postID;
		RequestDispatcher rd = getServletContext().getRequestDispatcher(postPage);
		rd.forward(request, response);
	}

}
