package com.kgcorner.vachan;

import com.kgcorner.vachan.data.QuoteDB;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = {"com.kgcorner.vachan.services"})
public class VachanApplication {

	public static void main(String[] args) {
		SpringApplication.run(VachanApplication.class, args);
		QuoteDB.getInstance();
	}

}
