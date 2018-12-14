package com.qa.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class RestClient {
	CloseableHttpResponse hr;
	
	public CloseableHttpResponse get(String url) throws ClientProtocolException, IOException, JSONException {
		CloseableHttpClient hc = HttpClients.createDefault();
		HttpGet hg = new HttpGet(url);
		hr  = hc.execute(hg);
		return hr;
	}
	
	public CloseableHttpResponse get(HashMap<String, String> header,String url) throws ClientProtocolException, IOException, JSONException {
		CloseableHttpClient hc = HttpClients.createDefault();
		HttpGet hg = new HttpGet(url);
		
		
		
		for(Map.Entry<String, String> e:header.entrySet()) {
			hg.addHeader(e.getKey(),e.getValue());
		}
		CloseableHttpResponse hr  = hc.execute(hg);
		return hr;
	}
	
	public CloseableHttpResponse post(String url, String entity, HashMap<String, String> headermap) throws ClientProtocolException, IOException {
		CloseableHttpClient hc = HttpClients.createDefault();
		HttpPost ps = new HttpPost(url);
		ps.setEntity(new StringEntity(entity));
		
		for(Map.Entry<String, String> e: headermap.entrySet()) {
			ps.addHeader(e.getKey(),e.getValue());
		}
		CloseableHttpResponse hr = hc.execute(ps);
		return hr;
	}
}
