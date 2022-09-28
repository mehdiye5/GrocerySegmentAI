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
import com.sfu.object.SegmentLoadResponse;
import com.sfu.object.Segmentation;
import com.sfu.object.StatusResponse;
import com.sfu.object.TrainInstacart;
import com.sfu.request.HttpClient;

/**
 * Servlet implementation class UpdateSegmentationStatus
 */
@WebServlet("/UpdateSegmentationStatus")
public class UpdateSegmentationStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateSegmentationStatus() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String target = "/viewSegmentation.jsp";
		
		int segmentationId = Integer.parseInt(request.getParameter("segmentationId"));
		
		Segmentation segmentation = SegmentationDao.getSegmentationById(segmentationId);
		
		Gson gson = new Gson();
		
		System.out.println("Instacart Update Segmentation Status");
		String url = "http://localhost:5000/" + "segment/1/" + segmentation.getResponseId() + "/status";		
		
		String result = HttpClient.getDataObject(url);
		
		StatusResponse object = gson.fromJson(result, StatusResponse.class);
		String statusResponse = object.status;
		
		System.out.println(statusResponse);
		if (statusResponse.equalsIgnoreCase("IN_PROGRESS")) {
			segmentation.setStatus(2);
		} else if (statusResponse.equalsIgnoreCase("SUCCESS")) {
			segmentation.setStatus(3);
		} else if (statusResponse.equalsIgnoreCase("FAILED")) {
			segmentation.setStatus(4);
		} else {
			segmentation.setStatus(1);
		}
		
		TrainInstacart trainObject = TrainInstacartDao.getTrainInstacartByUserDataId(segmentation.getDataObject().get_id());
		
		request.setAttribute("trainObject", trainObject);
		
		Integer trainStatus = (Integer) this.getServletConfig().getServletContext().getAttribute("trainStatus");
		
		request.setAttribute("trainStatus", trainStatus);
		
		SegmentationDao.updateSegmentationStatus(segmentation);
		
		
		List<Segmentation> allSegmentations = SegmentationDao.getAllSegmentation();
		
		request.setAttribute("allSegmentations", allSegmentations);
		
		RequestDispatcher view = request.getRequestDispatcher(target);
		view.forward(request, response);
	}

}
