package com.qa.tests;

import java.io.IOException;
import java.util.HashMap;

import javax.swing.text.html.parser.Entity;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;

public class GetAPITest extends TestBase {
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
  public void getAPITest() throws ClientProtocolException, IOException, JSONException {
	  restClient = new RestClient();
	  hr = restClient.get(url);
	  
	  int statusCode = hr.getStatusLine().getStatusCode();
		System.out.println(statusCode);
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200);
		
		int majVersion = hr.getProtocolVersion().getMajor();
		int minVersion = hr.getProtocolVersion().getMinor();
		
		System.out.println(majVersion+" "+minVersion);
		
		String resp = EntityUtils.toString(hr.getEntity(), "UTF-8");
		
		JSONObject jo = new JSONObject(resp);
		System.out.println("Json response: "+jo);
		
		String jresult = TestUtil.getValueByJpath(jo,"/per_page");
		System.out.println("Json result: "+jresult);
		
		String data = TestUtil.getValueByJpath(jo,"/data[0]/last_name");
		System.out.println("Json result data : "+data);
		
		
		Header[] headers = hr.getAllHeaders();
		
		HashMap<String,String> hm = new HashMap<String,String>();
		
		for(Header hd:headers) {
			hm.put(hd.getName(), hd.getValue());
		}
		
		System.out.println(hm);
	
		for(HashMap.Entry<String, String> e:hm.entrySet()) {
			if(e.getKey().equalsIgnoreCase("server"))
			System.out.println(e.getKey()+" "+e.getValue());
		}
	}

  
  @Test
  public void getAPITestWithHeader() throws ClientProtocolException, IOException, JSONException {
	  restClient = new RestClient();
	 
	  
	  HashMap<String, String> headermap = new HashMap<String, String>();
	  headermap.put("Content-Type", "application/json");
	  
	  hr = restClient.get(headermap,url);
	  int statusCode = hr.getStatusLine().getStatusCode();
		System.out.println(statusCode);
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200);
		
		int majVersion = hr.getProtocolVersion().getMajor();
		int minVersion = hr.getProtocolVersion().getMinor();
		
		System.out.println(majVersion+" "+minVersion);
		
		String resp = EntityUtils.toString(hr.getEntity(), "UTF-8");
		
		JSONObject jo = new JSONObject(resp);
		System.out.println("Json response: "+jo);
		
		String jresult = TestUtil.getValueByJpath(jo,"/per_page");
		System.out.println("Json result: "+jresult);
		
		String data = TestUtil.getValueByJpath(jo,"/data[0]/last_name");
		System.out.println("Json result data : "+data);
		
		
		Header[] headers = hr.getAllHeaders();
		
		HashMap<String,String> hm = new HashMap<String,String>();
		
		for(Header hd:headers) {
			hm.put(hd.getName(), hd.getValue());
		}
		
		System.out.println(hm);
	
		for(HashMap.Entry<String, String> e:hm.entrySet()) {
			if(e.getKey().equalsIgnoreCase("server"))
			System.out.println(e.getKey()+" "+e.getValue());
		}
	}


  }
