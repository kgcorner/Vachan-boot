package com.kgcorner.vachan.vachan;

import com.kgcorner.vachan.vachan.data.QuoteDB;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VachanApplication {

	public static void main(String[] args) {
		SpringApplication.run(VachanApplication.class, args);
		QuoteDB.getInstance();
	}

}
