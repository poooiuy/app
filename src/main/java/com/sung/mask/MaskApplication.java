package com.sung.mask;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class MaskApplication {
	
	@Autowired
	CallWebService callWebService;
	
	@Autowired
	SendMessage sendMessage;

	public static void main(String[] args) {
		SpringApplication.run(MaskApplication.class, args);
	}
	
	@Scheduled(fixedRateString="${interval}")
	private void invoke() {
		System.out.println("==================>   Invoked at : " + new Date());
		String result = callWebService.execute();
		if(!result.equals("")) {
			sendMessage.send(result);
		}
	}
	

}
