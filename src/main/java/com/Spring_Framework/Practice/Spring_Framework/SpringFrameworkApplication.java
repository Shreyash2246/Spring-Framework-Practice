package com.Spring_Framework.Practice.Spring_Framework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringFrameworkApplication implements CommandLineRunner{

	// Autowiring the demo component
	@Autowired
	demo d;
	public static void main(String[] args) {
		SpringApplication.run(SpringFrameworkApplication.class, args);
	
	}

	@Override
	public void run(String... args) throws Exception {
		// Calling the display method of demo component
		d.display();
	}

}
