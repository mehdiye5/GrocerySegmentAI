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
 * Servlet implementation class TrainModel
 */
@WebServlet("/TrainModel")
public class TrainModel extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TrainModel() {
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
		
		
		System.out.println("Instacart Train");
		String url = "http://localhost:5000/" + "segment/1/train";		
		
		String payload = "{}";
		String result = HttpClient.createDataObject(url, payload);
		
		TrainModelResponse object = gson.fromJson(result, TrainModelResponse.class);
		String responseId = object.id;
		
		trainObject.setStatus(2);
		trainObject.setTrainId(responseId);
		TrainInstacartDao.updateTrainInstacartStatus(trainObject);
		
		request.setAttribute("trainObject", trainObject);
		
		/*
		 * List<Segmentation> userSegmentations =
		 * SegmentationDao.getAllSegmentationByUserDataId(trainObject.getDataObject().
		 * get_id());
		 * 
		 * for (Segmentation segmentation : userSegmentations) {
		 * segmentation.setResponseId(responseId);
		 * SegmentationDao.updateSegmentationResponseId(segmentation); }
		 */
		List<Segmentation> allSegmentations = SegmentationDao.getAllSegmentation();
		
		request.setAttribute("allSegmentations", allSegmentations);
		
		RequestDispatcher view = request.getRequestDispatcher(target);
		view.forward(request, response);
	}

}
