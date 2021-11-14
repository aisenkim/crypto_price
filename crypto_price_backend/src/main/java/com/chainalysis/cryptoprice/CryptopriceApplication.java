package com.chainalysis.cryptoprice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class CryptopriceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CryptopriceApplication.class, args);
	}

}
