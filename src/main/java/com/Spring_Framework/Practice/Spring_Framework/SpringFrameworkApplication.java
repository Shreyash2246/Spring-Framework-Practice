package com.Spring_Framework.Practice.Spring_Framework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringFrameworkApplication implements CommandLineRunner{

	// singleton scope by default
	@Autowired
	demo d1;

	@Autowired
	demo d2;

	public static void main(String[] args) {
		SpringApplication.run(SpringFrameworkApplication.class, args);
	
	}

	@Override
	public void run(String... args) throws Exception {
		// Using the demo bean
		d1.display();
		d2.display();

		// Print hashcodes to show they are different instances(prototype scope)
		System.out.println(d1.hashCode());
		System.out.println(d2.hashCode());
	}

}
