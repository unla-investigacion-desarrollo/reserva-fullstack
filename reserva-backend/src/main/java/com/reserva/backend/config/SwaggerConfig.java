package com.reserva.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@OpenAPIDefinition
@Configuration
public class SwaggerConfig {
	
	@Bean
	public OpenAPI api() {
		return new OpenAPI()
				.info(new Info()
						.title("com.reserva.backend")
						.version("1.0.0")
						.description("api reserva ecologica"));
	}

}
