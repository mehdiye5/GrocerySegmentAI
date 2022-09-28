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
import com.sfu.databases.SegmentationDao;
import com.sfu.databases.TrainInstacartDao;
import com.sfu.object.Segmentation;
import com.sfu.object.StatusResponse;
import com.sfu.object.TrainInstacart;
import com.sfu.request.HttpClient;

/**
 * Servlet implementation class TrainModelStatusUpdate
 */
@WebServlet("/TrainModelStatusUpdate")
public class TrainModelStatusUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TrainModelStatusUpdate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String target = "/viewSegmentation.jsp";
		
		int trainObjectId = Integer.parseInt(request.getParameter("trainObjectId"));
		TrainInstacart trainObject = TrainInstacartDao.getTrainInstacartById(trainObjectId);
		
		Gson gson = new Gson();
		
		System.out.println("Instacart Train Status Update");
		String url = "http://localhost:5000/" + "segment/1/" + trainObject.getTrainId() + "/status";
		
		String result = HttpClient.getDataObject(url);
		
		StatusResponse object = gson.fromJson(result, StatusResponse.class);
		String statusResponse = object.status;
		
		if (statusResponse.equalsIgnoreCase("IN_PROGRESS")) {
			trainObject.setStatus(2);
		} else if (statusResponse.equalsIgnoreCase("SUCCESS")) {
			trainObject.setStatus(3);
		} else if (statusResponse.equalsIgnoreCase("FAILED")) {
			trainObject.setStatus(4);
		} else {
			trainObject.setStatus(1);
		}
		
		TrainInstacartDao.updateTrainInstacartStatus(trainObject);
		
		request.setAttribute("trainObject", trainObject);
		
		List<Segmentation> allSegmentations = SegmentationDao.getAllSegmentation();
		
		request.setAttribute("allSegmentations", allSegmentations);
		
		RequestDispatcher view = request.getRequestDispatcher(target);
		view.forward(request, response);
	}

}
