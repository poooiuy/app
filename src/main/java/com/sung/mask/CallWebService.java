package com.sung.mask;

import java.net.URLEncoder;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CallWebService {
	
	@Value("${url.costco}")
    private String url;
	
	@Value("${url.costco.keyword}")
    private String keywordStr;
	
	public String execute() {
		
		String result = "";
		
		String[] keywordArr = keywordStr.split(",");
		for(String keyword : keywordArr) {
			if( getSubmit(keyword) ) {
				if(result.equals("")) {
					result=keyword;
				}else {
					result=result+","+keyword;
				}
			}
		}
		return result;
	}
	
	private boolean getSubmit(String keyword) {
		
	    try {
	        CloseableHttpClient httpclient = HttpClients.createDefault();
	        String requestUrl = url + URLEncoder.encode(keyword, "ISO-8859-1");
	        
	        //GET 방식으로 parameter를 전달
	        HttpGet httpGet = new HttpGet(requestUrl);

	        CloseableHttpResponse response = httpclient.execute(httpGet);
	        try {
	        	int responseCode = response.getStatusLine().getStatusCode();
	        	switch(responseCode) {
	        	case 200 :
	        		String responseStr = EntityUtils.toString(response.getEntity());
		            int idx = responseStr.lastIndexOf("검색 결과가 없습니다.");
		            if(idx == -1)	return true;
	        		break;
	        	default :
	        		System.out.println("Response Code : " + responseCode);
	        		break;
	        	}
	            
	            
//	            System.out.println(EntityUtils.toString(response.getEntity()));
//	            HttpEntity entity = response.getEntity();
//	            EntityUtils.consume(entity);
	        } finally {
	            response.close();
	        }   
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		return false;
	}



}
