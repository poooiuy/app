package com.sung.mask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class GroupNotifyLINE {
	
	private static final String _NOTIFY_API = "https://notify-api.line.me/api/notify";

	public GroupNotifyLINE() {}
	
	public int postNotify(String token, String message) throws Exception 
	{
		int result = 0;	// can't get the result
   		try {
			HttpURLConnection connection = getConnection(token, new URL(_NOTIFY_API));
			String parameterString = new String("message="  + message);

			connection.getOutputStream().write(parameterString.getBytes("UTF-8"));
			connection.getInputStream();
            
			// Gets the status code from an HTTP response message.
			int statusCode = connection.getResponseCode();
        	result = statusCode;
        	if ( statusCode != 200 ) {
        		// Error occurs, before here
        		printHeaderFileds(connection);
        	}
	        connection.disconnect();
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
	
	private HttpURLConnection getConnection(String token, URL url) throws IOException 
	{
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setDoOutput( true );
		connection.setRequestMethod( "POST" );
		connection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded" );
		connection.addRequestProperty("Authorization",  "Bearer " + token);
		
		return connection;
	}

	private void printHeaderFileds(HttpURLConnection connection) {
		Map<String,List<String>> headerFields = connection.getHeaderFields();
		Set<String> keys = headerFields.keySet();
		Iterator<String> it = keys.iterator();
		while(it.hasNext()) {
			String key = it.next();
			List<String> values = headerFields.get(key);
			StringBuffer sb = new StringBuffer();
			sb.append( key ).append( ":" );
			Iterator<String> it2 = values.iterator();
			while( it2.hasNext() ) sb.append( " " ).append( it2.next() );
			System.out.println(sb);
		}
	}
}