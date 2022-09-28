package com.sfu.request;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class HttpClient {
	// one instance, reuse
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    
    /*
    public static void main(String[] args) throws Exception {

        HttpClient obj = new HttpClient();

        try {
            System.out.println("Testing 1 - Send Http GET request");
            obj.sendGet();

            System.out.println("Testing 2 - Send Http POST request");
            obj.sendPost();
        } finally {
            obj.close();
        }
    }
    */

    public void close() throws IOException {
        httpClient.close();
    }
    

    public String sendGet(String tenantId) throws Exception {
    	//String oldUri = "https://www.google.com/search?q=mkyong";
        HttpGet request = new HttpGet("http://localhost:8072/realEstateListing/" + tenantId);
        String result = "[111373431, 120900430, 2084491383, 120901374]";
        // add request headers
        
        //request.addHeader("custom-key", "mkyong");
        //request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");

        try (CloseableHttpResponse response = httpClient.execute(request)) {

            // Get HttpResponse Status
            System.out.println(response.getStatusLine().toString());

            HttpEntity entity = response.getEntity();
            Header headers = entity.getContentType();
            //System.out.println(headers);

            if (entity != null) {
                // return it as a String
                result = EntityUtils.toString(entity);
                //System.out.println(result);
                
            }

        }
        
        return result;

    }

    public void sendPost(String viewedListing) throws Exception {
    	//String oldURI = "https://httpbin.org/post";
        HttpPost post = new HttpPost("http://localhost:8072/" + viewedListing);

        // add request parameter, form parameters        
        //List<NameValuePair> urlParameters = new ArrayList<>();
        //urlParameters.add(new BasicNameValuePair("username", "abc"));
        //urlParameters.add(new BasicNameValuePair("password", "123"));
        //urlParameters.add(new BasicNameValuePair("custom", "secret"));

        //post.setEntity(new UrlEncodedFormEntity(urlParameters));

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {

            System.out.println(EntityUtils.toString(response.getEntity()));
        }

    }
    
    public void sendPosting(String posting) throws Exception {
    	//String oldURI = "https://httpbin.org/post";
        HttpPost post = new HttpPost("http://localhost:8072/" + posting + "/");

        // add request parameter, form parameters
        //List<NameValuePair> urlParameters = new ArrayList<>();
        //urlParameters.add(new BasicNameValuePair("username", "abc"));
        //urlParameters.add(new BasicNameValuePair("password", "123"));
        //urlParameters.add(new BasicNameValuePair("custom", "secret"));

        //post.setEntity(new UrlEncodedFormEntity(urlParameters));

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {

            System.out.println(EntityUtils.toString(response.getEntity()));
        }

    }
    
    public static String getDataObject(String url) {
    	
        HttpGet request = new HttpGet(url);
        String result = "";        

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
                CloseableHttpResponse response = httpClient.execute(request)) {

            // Get HttpResponse Status
            System.out.println(response.getStatusLine().toString());

            HttpEntity entity = response.getEntity();            

            if (entity != null) {
                // return it as a String
                result = EntityUtils.toString(entity);
                System.out.println(result);
                
            }

        } catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return result;

    }
    
    public static String createDataObject(String url, String payload) {
    	
    	HttpPost post = new HttpPost(url);
    	
    	StringEntity entity;
		try {
			entity = new StringEntity(payload);
			post.setEntity(entity);
			post.setHeader("Accept", "application/json");
			post.setHeader("Content-type", "application/json");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	
    	String result = "";
    	
    	try (CloseableHttpClient httpClient = HttpClients.createDefault();
                CloseableHttpResponse response = httpClient.execute(post)) {
	    		HttpEntity responseEntity = response.getEntity();
	            Header headers = responseEntity.getContentType();
	            //System.out.println(headers);
	            System.out.println(response.getStatusLine().getStatusCode());
	            
	
	            if (responseEntity != null) {
	                // return it as a String
	                result = EntityUtils.toString(responseEntity);
	                System.out.println(result);
	                
	            }
    		
               //System.out.println(EntityUtils.toString(response.getEntity()));
           } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return result;
    }
}
