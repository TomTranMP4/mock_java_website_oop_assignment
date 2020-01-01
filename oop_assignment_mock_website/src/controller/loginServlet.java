package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class loginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final String PAGE_DIRECT = "WEB-INF/login.jsp";
	private static final String PAGE_DIRECT_ADMIN = "/adminPortal";
    
    public loginServlet(){
        super();
        System.out.println("loginServlet is running");
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Checking session
		HttpSession userSession = request.getSession(true);
		
		//Checking userAuthentication attribute
		int userAuthentication = 0;
		try {
			Object tmp = userSession.getAttribute("userAuthentication");
			if (null != tmp)
			{
				try {
					userAuthentication = Integer.parseInt(tmp.toString());
					System.out.println("Getting userAuthentication successfully - loginServlet");
				}catch(Exception e) {
					System.out.println("Getting userAuthentication fail - loginServlet");
					e.printStackTrace();
				}
			}
			else
			{
				System.out.println("Getting no userAuthentication - loginServlet");
			}
		}catch(Exception e) {
			System.out.println("Getting userAuthentication fail - loginServlet");
			e.printStackTrace();
		}
		
		//User already logged in
		if (1 == userAuthentication)
		{
			RequestDispatcher rd = getServletContext().getRequestDispatcher(PAGE_DIRECT_ADMIN);
			rd.forward(request, response);
		}
		else
		{
			//Redirect page
			request.getRequestDispatcher(PAGE_DIRECT).forward(request, response);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
