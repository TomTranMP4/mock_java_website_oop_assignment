package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.databaseConnection;
import model.categoryModel;

public class addNewCategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String ADD_NEW_CATAGORY_PAGE = "WEB-INF/addNewCategory.jsp";
	private static final String ADD_NEW_CATAGORY_PAGE_ERROR = "WEB-INF/addNewCategoryError.jsp";
	private static final String ADD_NEW_CATAGORY_PAGE_SUCCESSFULLY = "addNewCategory?function=redirect";
	private static final String ADMIN_PAGE = "login";
       
    public addNewCategoryServlet() {
        super();
        System.out.println("addNewCategoryServlet is running");
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Getting parameters
		String function = null;
		try {
			function = request.getParameter("function");
			if(0 != function.length()) {
				System.out.println("Getting function successfully - add new category servlet");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		//Performing function
		if("redirect".equals(function)) {
			//open database connection
			databaseConnection dcon = new databaseConnection();
			dcon.openDatabaseConnection();
			//retrieving all categories
			ArrayList<categoryModel> categories = null;
			categories = dcon.gettingCategories();
			request.setAttribute("categories", categories);
			//close database connection
			dcon.closeDatabaseConnection();
			request.getRequestDispatcher(ADD_NEW_CATAGORY_PAGE).forward(request, response);
		}else if("addNewCategory".equals(function)) {
			int redirectResult = 0; //Error page
			//getting parameters
			String categoryName = null;
			try {
				categoryName = request.getParameter("txtCategoryName");
				if(0 != categoryName.length()) {
					System.out.println("Getting categoryName from form successfully - add new category servlet");
				}
			}catch(Exception e) {
				System.out.println("Getting categoryName from form fail - add new category servlet");
				e.printStackTrace();
			}
			//open database
			databaseConnection dcon = new databaseConnection();
			dcon.openDatabaseConnection();
			//adding new categoryName to database
			//choosing redirect page correspondingly
			boolean returnResult = dcon.addingNewCategory(categoryName);
			if(returnResult) {
				redirectResult = 1;
				System.out.println("Adding category successfully - add new category servlet");
			}else {
				System.out.println("Adding category fail - add new category servlet");
			}
			//close database
			dcon.closeDatabaseConnection();
			//redirect page
			if(0 != redirectResult) {
				request.getRequestDispatcher(ADD_NEW_CATAGORY_PAGE_SUCCESSFULLY).forward(request, response);
			}else {
				request.getRequestDispatcher(ADD_NEW_CATAGORY_PAGE_ERROR).forward(request, response);
			}
		}else {
			request.getRequestDispatcher(ADMIN_PAGE).forward(request, response);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
