package com.akamensi;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AmsRestBillcomApplication {
	
	public static String uploadDirectory =
			System.getProperty("user.dir")+"/src/main/resources/static/uploads";

	public static void main(String[] args) {
		SpringApplication.run(AmsRestBillcomApplication.class, args);
		new File(uploadDirectory).mkdir();
		System.out.println("Holla!!");
	}

}
