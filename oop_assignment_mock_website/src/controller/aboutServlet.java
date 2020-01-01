package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.databaseConnection;
import model.userModel;

public class aboutServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final String REDIRECT_PAGE = "WEB-INF/about.jsp";
       
    public aboutServlet() {
        super();
        System.out.println("aboutServlet is running");
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Open database connection
		databaseConnection dcon = new databaseConnection();
		dcon.openDatabaseConnection();
		
		//Retrieving information of user
		userModel user = dcon.gettingUserInformation();
		if (null == user)
		{
			System.out.println("Getting user information fail - aboutServlet");
		}
		else
		{
			System.out.println("Getting user information successfully - aboutServlet");
		}
		
		//Attaching user information to attribute
		request.setAttribute("user", user);
		
		//Close database connection
		dcon.closeDatabaseConnection();
		
		//Redirect to about page
		request.getRequestDispatcher(REDIRECT_PAGE).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
