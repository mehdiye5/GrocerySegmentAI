package com.sfu.dataobjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.sfu.databases.InstacartDataObjectDao;
import com.sfu.databases.InstacartFeaturesDao;
import com.sfu.databases.TrainInstacartDao;
import com.sfu.exception.DatabaseInsertException;
import com.sfu.object.AdditionalFeaturesLoad;
import com.sfu.object.InstacartDataObject;
import com.sfu.object.InstacartFeatures;
import com.sfu.object.OrderProductsLoad;
import com.sfu.object.OrdersLoad;
import com.sfu.object.ProductsLoad;
import com.sfu.object.TrainInstacart;
import com.sfu.request.HttpClient;

/**
 * Servlet implementation class CreateInstacart
 */
@WebServlet("/CreateInstacart")
public class CreateInstacart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateInstacart() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String target = "/index.jsp";
		
		String orders_user_id = request.getParameter("orders_user_id");
		String orders_order_id = request.getParameter("orders_order_id");
		String orders_timestamp = request.getParameter("orders_timestamp");
		
		String product_product_id = request.getParameter("product_product_id");
		List<String> product_additional_features = Arrays.asList(request.getParameterValues("product_additional_features"));
		
		List<AdditionalFeaturesLoad> additionalFeaturesProduct = new ArrayList<AdditionalFeaturesLoad>();
		for (String feature : product_additional_features) {
			AdditionalFeaturesLoad tempFeat = new AdditionalFeaturesLoad();
			tempFeat.columnName = feature;
			additionalFeaturesProduct.add(tempFeat);
		}
		
		String product_orders_order_id = request.getParameter("product_orders_order_id");
		String product_orders_product_id = request.getParameter("product_orders_product_id");
		List<String> order_product_additional_features = Arrays.asList(request.getParameterValues("order_product_additional_features"));
		
		List<AdditionalFeaturesLoad> additionalFeaturesOrderProduct = new ArrayList<AdditionalFeaturesLoad>();
		for (String feature : order_product_additional_features) {
			AdditionalFeaturesLoad tempFeat = new AdditionalFeaturesLoad();
			tempFeat.columnName = feature;
			additionalFeaturesOrderProduct.add(tempFeat);
		}
		
		int instacart_product_id = Integer.parseInt(request.getParameter("instacart_product_id"));
		int instacart_orders_id = Integer.parseInt(request.getParameter("instacart_orders_id"));
		int instacart_pruduct_orders_id = Integer.parseInt(request.getParameter("instacart_pruduct_orders_id"));
		
		InstacartDataObject product_instacart = InstacartDataObjectDao.getInstacartDataObjectById(instacart_product_id);
		InstacartDataObject orders_instacart = InstacartDataObjectDao.getInstacartDataObjectById(instacart_orders_id);
		InstacartDataObject product_orders_instacart = InstacartDataObjectDao.getInstacartDataObjectById(instacart_pruduct_orders_id);
		
		OrdersLoad ordersLoad = new OrdersLoad(orders_instacart.getBucketName(), orders_instacart.getBucketKey(), 1, orders_user_id, orders_order_id, orders_timestamp);
		ProductsLoad productsLoad = new ProductsLoad(product_instacart.getBucketName(), product_instacart.getBucketKey(), 1, product_product_id, additionalFeaturesProduct);
		OrderProductsLoad orderProductsLoad = new OrderProductsLoad(product_orders_instacart.getBucketName(), product_orders_instacart.getBucketKey(), 1, product_orders_order_id, product_orders_product_id, additionalFeaturesOrderProduct);
		
		Gson gson = new Gson();
		
		String ordersLoadJson = gson.toJson(ordersLoad);
		String productsLoadJson = gson.toJson(productsLoad);
		String orderProductsLoadJson = gson.toJson(orderProductsLoad);
		
		
		
		
		try {
			TrainInstacartDao.insertTrainInstacart(new TrainInstacart(0, 1, product_instacart.getUserData(), "0"));
			
			InstacartFeaturesDao.insertInstacartFeatures(new InstacartFeatures(0, orders_instacart.get_id(), "orders_user_id", orders_user_id));
			InstacartFeaturesDao.insertInstacartFeatures(new InstacartFeatures(0, orders_instacart.get_id(), "orders_order_id", orders_order_id));
			InstacartFeaturesDao.insertInstacartFeatures(new InstacartFeatures(0, orders_instacart.get_id(), "orders_timestamp", orders_timestamp));
			
			InstacartFeaturesDao.insertInstacartFeatures(new InstacartFeatures(0, product_instacart.get_id(), "product_product_id", product_product_id));
			InstacartFeaturesDao.insertInstacartFeatures(new InstacartFeatures(0, product_instacart.get_id(), "product_product_id", gson.toJson(additionalFeaturesProduct)));
			
			InstacartFeaturesDao.insertInstacartFeatures(new InstacartFeatures(0, product_orders_instacart.get_id(), "product_orders_order_id", product_orders_order_id));
			InstacartFeaturesDao.insertInstacartFeatures(new InstacartFeatures(0, product_orders_instacart.get_id(), "product_orders_product_id", product_orders_product_id));
			InstacartFeaturesDao.insertInstacartFeatures(new InstacartFeatures(0, product_orders_instacart.get_id(), "order_product_additional_features", gson.toJson(additionalFeaturesOrderProduct)));
		} catch (DatabaseInsertException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.getServletConfig().getServletContext().setAttribute("trainStatus", 1);
		
		System.out.println("Instacart Orders");
		// createOrdersDataObject
		String url = "http://localhost:5000/" + "data/orders/1";
		HttpClient.createDataObject(url, ordersLoadJson);
		
		System.out.println("Instacart Products");
		// createProductsDataObject
		url = "http://localhost:5000/" + "data/products/1";
		HttpClient.createDataObject(url, productsLoadJson);
		
		System.out.println("Instacart OrderProducts");
		// createOrderProductsDataObject
		url = "http://localhost:5000/" + "data/orderproducts/1";
		HttpClient.createDataObject(url, orderProductsLoadJson);
		
		System.out.println(ordersLoadJson);
		System.out.println(productsLoadJson);
		System.out.println(orderProductsLoadJson);
		
		RequestDispatcher view = request.getRequestDispatcher(target);
		view.forward(request, response);
	}

}
