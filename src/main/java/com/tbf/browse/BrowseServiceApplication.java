package com.tbf.browse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
//@EnableHystrix
@EnableCaching
public class BrowseServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BrowseServiceApplication.class, args);
	}

}
