package com.sfu.dataobjects;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;
import com.amazonaws.services.s3.model.S3Object;
import com.google.gson.Gson;
import com.sfu.databases.DataObjectTypeDao;
import com.sfu.databases.InstacartDataObjectDao;
import com.sfu.databases.UserDataObjectDao;
import com.sfu.exception.DatabaseInsertException;
import com.sfu.object.DataObjectType;
import com.sfu.object.InstacartDataObject;
import com.sfu.object.UserDataObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Servlet implementation class ReadS3Data
 */
@WebServlet("/ReadS3Data")
public class ReadS3Data extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReadS3Data() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String target = "/createFeatures.jsp";
		
		String name = request.getParameter("name");
		String product_bucket_name = request.getParameter("product_bucket_name");
		String product_bucket_key = request.getParameter("product_bucket_key") + ".csv"; 
		 
		
		String orders_bucket_name = request.getParameter("orders_bucket_name");
		String orders_bucket_key = request.getParameter("orders_bucket_key")  + ".csv";
		
		String product_orders_bucket_name = request.getParameter("product_orders_bucket_name");
		String product_orders_bucket_key = request.getParameter("product_orders_bucket_key")  + ".csv";
		
		Gson gson = new Gson();
		
		
		//Regions clientRegion = Regions.DEFAULT_REGION;
		Regions clientRegion = Regions.US_WEST_2;
        //String bucketName = "*** Bucket name ***";
		//String bucketName = "cmpt733-final-project";
        //String key = "*** Object key ***";
		//String key = "aisles.csv";
		
		  try { System.out.println("pass"); 
		  int userDataId = UserDataObjectDao.insertUserDataObject(new UserDataObject(0, name, 1));
		  //List<String> features = getS3Features(clientRegion, product_bucket_name, product_bucket_key); 
		  //String featuresJson = gson.toJson(features);
		  UserDataObject userData = UserDataObjectDao.getUserDataObjectById(userDataId);
		  DataObjectType objectType = DataObjectTypeDao.getDataObjectTypeById(1);
		  
		  int instacart_product_id = InstacartDataObjectDao.insertInstacartDataObject(new InstacartDataObject(0, userData, objectType, product_bucket_name, product_bucket_key));
		  
		  objectType = DataObjectTypeDao.getDataObjectTypeById(2);
		  int instacart_orders_id = InstacartDataObjectDao.insertInstacartDataObject(new InstacartDataObject(0, userData, objectType, orders_bucket_name, orders_bucket_key));
		  
		  objectType = DataObjectTypeDao.getDataObjectTypeById(3);
		  int instacart_pruduct_orders_id = InstacartDataObjectDao.insertInstacartDataObject(new InstacartDataObject(0, userData, objectType, product_orders_bucket_name, product_orders_bucket_key));
		  
		  request.setAttribute("instacart_product_id", instacart_product_id);
		  request.setAttribute("instacart_orders_id", instacart_orders_id);
		  request.setAttribute("instacart_pruduct_orders_id", instacart_pruduct_orders_id);
		  
		  } catch (DatabaseInsertException e) { // TODO Auto-generated catch block
		  e.printStackTrace(); }
		 
		//List<String> productFeatures = getS3Features(clientRegion, product_bucket_name, product_bucket_key);
        
        
        
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
	
	private static void displayTextInputStream(InputStream input) throws IOException {
        // Read the text input stream one line at a time and display each line.
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String line = null;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        System.out.println();
    }
	
	private static List<String> getS3Features(Regions clientRegion, String bucketName, String key) throws IOException {
		S3Object fullObject = null, objectPortion = null, headerOverrideObject = null;
		List<String> features = new ArrayList<String>();
		
        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    .withCredentials(new ProfileCredentialsProvider())
                    .build();

            // Get an object and print its contents.
            System.out.println("Downloading an object");
            fullObject = s3Client.getObject(new GetObjectRequest(bucketName, key));
            System.out.println("Content-Type: " + fullObject.getObjectMetadata().getContentType());
            System.out.println("Content: ");
            //displayTextInputStream(fullObject.getObjectContent());
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(fullObject.getObjectContent()));
            String features_str = reader.readLine();
            features = Arrays.asList(features_str.split("\\s*,\\s*"));
            System.out.println(features);
            
            
			/*
			 * // Get a range of bytes from an object and print the bytes. GetObjectRequest
			 * rangeObjectRequest = new GetObjectRequest(bucketName, key) .withRange(0, 9);
			 * objectPortion = s3Client.getObject(rangeObjectRequest);
			 * System.out.println("Printing bytes retrieved.");
			 * //displayTextInputStream(objectPortion.getObjectContent());
			 * 
			 * // Get an entire object, overriding the specified response headers, and print
			 * the object's content. ResponseHeaderOverrides headerOverrides = new
			 * ResponseHeaderOverrides() .withCacheControl("No-cache")
			 * .withContentDisposition("attachment; filename=example.txt"); GetObjectRequest
			 * getObjectRequestHeaderOverride = new GetObjectRequest(bucketName, key)
			 * .withResponseHeaders(headerOverrides); headerOverrideObject =
			 * s3Client.getObject(getObjectRequestHeaderOverride);
			 * //displayTextInputStream(headerOverrideObject.getObjectContent());
			 */
            
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process 
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        } finally {
            // To ensure that the network connection doesn't remain open, close any open input streams.
            if (fullObject != null) {
                fullObject.close();
            }
            if (objectPortion != null) {
                objectPortion.close();
            }
            if (headerOverrideObject != null) {
                headerOverrideObject.close();
            }
        }
        
        return features;
	}

}
