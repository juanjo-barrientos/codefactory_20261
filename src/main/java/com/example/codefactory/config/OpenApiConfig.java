package com.example.codefactory.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
	@Bean
	public OpenAPI logisticsTrackingOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("Logistics Tracking API")
						.version("v1")
						.description("API para gestión de envíos, paquetes y trazabilidad logística"));
	}
}
