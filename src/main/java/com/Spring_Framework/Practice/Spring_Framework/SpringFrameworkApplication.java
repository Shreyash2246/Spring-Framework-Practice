package com.Spring_Framework.Practice.Spring_Framework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.Spring_Framework.Practice.Spring_Framework.dbExample.*;

@SpringBootApplication
public class SpringFrameworkApplication implements CommandLineRunner{

	@Autowired
	dbService devService;

	public static void main(String[] args) {
		SpringApplication.run(SpringFrameworkApplication.class, args);
	
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(devService.getData());
	}

}
