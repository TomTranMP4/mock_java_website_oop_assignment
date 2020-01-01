package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.databaseConnection;

public class deletingCategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String DIRECT_PAGE_ERROR = "WEB-INF/deletingCategoryError.jsp";
	private static final String DIRECT_PAGE_SUCCESSFUL = "addNewCategory?function=redirect";
       
    public deletingCategoryServlet() {
        super();
        System.out.println("deletingCategory is running");
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int cagetoryID = 0;
		String path = DIRECT_PAGE_ERROR;
		
		//Getting parameter from category delete form
		try{
			cagetoryID = Integer.parseInt(request.getParameter("intCategoryID"));
			System.out.println("Getting catagoryID successfully - deletingCategory servlet");
		}catch (Exception e){
			System.out.println("Getting catagoryID fail - deletingCategory servlet");
			e.printStackTrace();
		}
		
		//Deleting category from database
		//Opening database connection
		databaseConnection dcon = new databaseConnection();
		dcon.openDatabaseConnection();
		
		//Delete category
		if(0 != cagetoryID) {
			boolean booleanDeleteResult = dcon.deletingCategory(cagetoryID);
			if(true == booleanDeleteResult ) {
				path = DIRECT_PAGE_SUCCESSFUL;
			}
		}
		
		//Closing database connection
		dcon.closeDatabaseConnection();
		
		//Redirect to corresponding page
		request.getRequestDispatcher(path).forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
