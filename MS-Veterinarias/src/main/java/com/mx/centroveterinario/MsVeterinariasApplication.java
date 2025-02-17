package com.mx.centroveterinario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.mx.centroveterinario.FeignClients")
@SpringBootApplication
public class MsVeterinariasApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsVeterinariasApplication.class, args);
	}

}
