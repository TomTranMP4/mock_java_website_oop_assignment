package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.databaseConnection;

public class adminPortalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String DIRECT_PAGE = "WEB-INF/admin_portal.jsp";
	private static final String DIRECT_PAGE_ERROR = "WEB-INF/login_error.jsp";
  
    public adminPortalServlet() {
        super();
        System.out.println("adminPortalServlet is running");
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Variables
		String username = null;
		String password = null;
		HttpSession userSession = request.getSession(true);
		Object temp = userSession.getAttribute("userAuthentication");
		int directResult = 0; //1 - to admin_portal
		int userAuthentication = 0;
		
		//Getting parameters
		try {
			username = request.getParameter("txtUsername");
			password = request.getParameter("txtPassword");
			System.out.println("Getting parameters successfully - adminPortalServlet");
			System.out.println("Username: " + username + " " + "Password: " + password);
		}catch(Exception e) {
			System.out.println("Getting parameters fail - adminPortalServlet");
			e.printStackTrace();
		}
		
		//Checking session
		if (null != temp)
		{
			try {
				userAuthentication = Integer.parseInt(temp.toString());
				System.out.println("Getting userAuthentication successfully - adminPortalServlet");
			}catch(Exception e) {
				System.out.println("Getting userAuthentication fail - adminPortalServlet");
				e.printStackTrace();
			}
			if (1 == userAuthentication)
			{
				directResult = 1; //to admin_portal
			}
		}
		else
		{
			//Authentication process
			//Open database
			databaseConnection dcon = new databaseConnection();
			dcon.openDatabaseConnection();
			
			//Authentication
			if (null != username && null != password)
			{
				if (0 != username.length() && 0 != password.length())
				{
					boolean resultAuthentication = dcon.userAuthentication(username, password);
					if (true == resultAuthentication)
					{
						directResult = 1; //to admin_portal
						//Setting up session
						userSession.setAttribute("userAuthentication", 1);
					}
				}
			}
			
			//Close database
			dcon.closeDatabaseConnection();
		}
		
		//Direct page
		if (1 == directResult)
		{
			request.getRequestDispatcher(DIRECT_PAGE).forward(request, response);
		}
		else
		{
			request.getRequestDispatcher(DIRECT_PAGE_ERROR).forward(request, response);
		}
		
	}

}
