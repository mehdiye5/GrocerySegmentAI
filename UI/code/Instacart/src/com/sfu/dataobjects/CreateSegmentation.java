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
import com.sfu.databases.UserDataObjectDao;
import com.sfu.exception.DatabaseInsertException;
import com.sfu.object.SegmentLoadResponse;
import com.sfu.object.Segmentation;
import com.sfu.object.SegmentationLoad;
import com.sfu.object.TrainInstacart;
import com.sfu.object.UserDataObject;
import com.sfu.request.HttpClient;

/**
 * Servlet implementation class CreateSegmentation
 */
@WebServlet("/CreateSegmentation")
public class CreateSegmentation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateSegmentation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String target = "/viewSegmentation.jsp";
		
		int instacartObjectId = Integer.parseInt(request.getParameter("instacartObjectId"));
		String productId = request.getParameter("productId");		
		String destinationBucket = request.getParameter("destinationBucket");
		String destinationObject = request.getParameter("destinationObject");
		String segmentationName = request.getParameter("segmentationName");
		
		UserDataObject dataObject = UserDataObjectDao.getUserDataObjectById(instacartObjectId);
		
		Gson gson = new Gson();
		
		
		SegmentationLoad segmentationLoad = new SegmentationLoad(productId, destinationBucket, destinationObject);
		String payload = gson.toJson(segmentationLoad);
		
		System.out.println("Instacart Create Segmentation");
		String url = "http://localhost:5000/" + "segment/1";
		//url = "https://reqres.in/api/users";
		//payload = "{}";
		
		String result = HttpClient.createDataObject(url, payload);
		
		SegmentLoadResponse object = gson.fromJson(result, SegmentLoadResponse.class);
		String responseId = object.id;
		
		System.out.println(responseId);
		
		Segmentation segmentation = new Segmentation(0, dataObject, productId, destinationBucket, destinationObject, responseId, 1, segmentationName);
		
		try {
			SegmentationDao.insertSegmentation(segmentation);
		} catch (DatabaseInsertException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		TrainInstacart trainObject = TrainInstacartDao.getTrainInstacartByUserDataId(dataObject.get_id());
		
		request.setAttribute("trainObject", trainObject);
		
		List<Segmentation> allSegmentations = SegmentationDao.getAllSegmentation();
		
		request.setAttribute("allSegmentations", allSegmentations);
		
		Integer trainStatus = (Integer) this.getServletConfig().getServletContext().getAttribute("trainStatus");
		
		request.setAttribute("trainStatus", trainStatus);
		
		RequestDispatcher view = request.getRequestDispatcher(target);
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
