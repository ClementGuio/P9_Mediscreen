package com.mediscreen.riskreport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class RiskreportApplication{
	
	Logger logger = LoggerFactory.getLogger(RiskreportApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(RiskreportApplication.class, args);
	}

}
//HTML: Mettre en valeur le niveau de risque ????