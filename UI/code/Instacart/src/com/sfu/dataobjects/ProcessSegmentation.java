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
import com.sfu.object.TrainInstacart;
import com.sfu.object.TrainModelResponse;
import com.sfu.request.HttpClient;

/**
 * Servlet implementation class ProcessSegmentation
 */
@WebServlet("/ProcessSegmentation")
public class ProcessSegmentation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProcessSegmentation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		String target = "/viewSegmentation.jsp";
		
		int segmentationId = Integer.parseInt(request.getParameter("segmentationId"));
		
		Segmentation segmentation = SegmentationDao.getSegmentationById(segmentationId);
		
		
		Gson gson = new Gson();
		
		System.out.println("Instacart Process Segnmentation");
		String url = "http://localhost:5000/" + "segment/1/" + segmentation.getResponseId() + "/process";
		//url = "https://reqres.in/api/users";
		String payload = "{}";
		String result = HttpClient.createDataObject(url, payload);
		
		TrainModelResponse object = gson.fromJson(result, TrainModelResponse.class);
		String responseId = object.id;
		
		segmentation.setResponseId(responseId);
		segmentation.setStatus(2);
		SegmentationDao.updateSegmentationResponseId(segmentation);
		SegmentationDao.updateSegmentationStatus(segmentation);
		
		TrainInstacart trainObject = TrainInstacartDao.getTrainInstacartByUserDataId(segmentation.getDataObject().get_id());
		
		request.setAttribute("trainObject", trainObject);
		
		Integer trainStatus = (Integer) this.getServletConfig().getServletContext().getAttribute("trainStatus");
		
		request.setAttribute("trainStatus", trainStatus);
		
		List<Segmentation> allSegmentations = SegmentationDao.getAllSegmentation();
		
		request.setAttribute("allSegmentations", allSegmentations);
		
		RequestDispatcher view = request.getRequestDispatcher(target);
		view.forward(request, response);
	}

}
