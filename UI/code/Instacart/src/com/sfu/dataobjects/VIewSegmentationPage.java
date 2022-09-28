package com.sfu.dataobjects;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sfu.databases.SegmentationDao;
import com.sfu.databases.TrainInstacartDao;
import com.sfu.object.Segmentation;
import com.sfu.object.TrainInstacart;

/**
 * Servlet implementation class VIewSegmentationPage
 */
@WebServlet("/VIewSegmentationPage")
public class VIewSegmentationPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VIewSegmentationPage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String target = "/viewSegmentation.jsp";
		
		List<Segmentation> allSegmentations = SegmentationDao.getAllSegmentation();
		
		request.setAttribute("allSegmentations", allSegmentations);
		
		Integer trainStatus = (Integer) this.getServletConfig().getServletContext().getAttribute("trainStatus");
		
		if (!allSegmentations.isEmpty()) {
			Segmentation segmentation = allSegmentations.get(0);
			
			TrainInstacart trainObject = TrainInstacartDao.getTrainInstacartByUserDataId(segmentation.getDataObject().get_id());
			
			request.setAttribute("trainObject", trainObject);
		}		
		
		request.setAttribute("trainStatus", trainStatus);
		RequestDispatcher view = request.getRequestDispatcher(target);
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
