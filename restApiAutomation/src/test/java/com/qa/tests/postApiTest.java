package com.qa.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.data.Users;
import com.qa.util.TestUtil;

public class postApiTest extends TestBase{
TestBase testBase;
	
	String apiUrl;
	String url ;
	RestClient restClient;
	String serviceUrl;
	CloseableHttpResponse hr;
	
	@BeforeMethod
	public void setUp()  {
		testBase=new TestBase();
		serviceUrl = Prop.getProperty("URL");
		apiUrl= Prop.getProperty("serviceURL");
		url = serviceUrl+apiUrl;
	}	
	
	
	 @Test
	  public void postApi() throws ClientProtocolException, IOException, JSONException {
		  restClient = new RestClient();
		  HashMap<String, String> headermap = new HashMap<String, String>();
		  headermap.put("Content-Type", "application/json");
		  
		  ObjectMapper mapper = new ObjectMapper();
		  Users users = new Users("Morpheus","Leader");
				  
		  mapper.writeValue(new File("C:\\Users\\ak1kumar\\eclipse-workspace\\restApiAutomation\\src\\main\\java\\com\\qa\\data\\users.json"), users);
		  
		  String userString = mapper.writeValueAsString(users);
		  
		  hr = restClient.post(url,userString,headermap);
		  
		  System.out.println(hr);
		  
		  int statusCode = hr.getStatusLine().getStatusCode();
		  
		  System.out.println(statusCode);
		  Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_201);
		  
		  String obj = EntityUtils.toString(hr.getEntity(),"UTF-8");
		  
		  JSONObject respJson = new JSONObject(obj);
		  
		  System.out.println(respJson);
		 
		  Users usr =  mapper.readValue(obj, Users.class);
		  
		  System.out.println(usr.getCreatedAt()+" "+usr.getName()+" "+usr.getId()+" "+usr.getJob());
		  
		  Assert.assertEquals(usr.getName(), users.getName());
	 }
}
