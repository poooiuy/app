package com.sung.mask;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SendMessage {
	
	@Autowired
	GroupNotifyLINE groupNotifyLINE;
	
	@Value("${line.token}")
    private String lineToken;
	
	@Value("${url.costco}")
    private String url;
	
	public void send(String message) {
		
		// send the message to notify
		try {
			int resultCode = groupNotifyLINE.postNotify(lineToken, makeMessage(message));
			switch ( resultCode ) {
				case 200:
					System.out.println("->   SendMessage ["+ URLEncoder.encode(message, "ISO-8859-1") + "] at : " + new Date());
					break;
				default:
					System.out.println("->   SendMessage Fail at : " + new Date());
					break;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println( "Error Message : " + e.getMessage() );
		}
	}
	
	private String makeMessage(String message) {
		String result = "";
		String[] messageArr = message.split(",");
		for(String str : messageArr) {
			result += "\n";
			try {
				result += "[" + URLEncoder.encode(str, "ISO-8859-1") + "]";
				result += "\n";
				result += url + URLEncoder.encode(str, "ISO-8859-1");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}

}
