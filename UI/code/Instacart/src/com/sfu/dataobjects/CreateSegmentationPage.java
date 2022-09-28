package com.sfu.dataobjects;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.sfu.databases.InstacartDataObjectDao;
import com.sfu.databases.UserDataObjectDao;
import com.sfu.object.InstacartDataObject;
import com.sfu.object.UserDataObject;

/**
 * Servlet implementation class CreateSegmentationPage
 */
@WebServlet("/CreateSegmentationPage")
public class CreateSegmentationPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateSegmentationPage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String target = "/createNewSegmentation.jsp";
		
		List<UserDataObject> dataObject = UserDataObjectDao.getAllUserDataObject();
		
		
		request.setAttribute("dataObjects", dataObject);
		RequestDispatcher view = request.getRequestDispatcher(target);
		view.forward(request, response);
	}

}
