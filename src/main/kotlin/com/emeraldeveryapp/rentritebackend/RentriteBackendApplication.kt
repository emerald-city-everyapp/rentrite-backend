package com.emeraldeveryapp.rentritebackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
class RentriteBackendApplication

fun main(args: Array<String>) {
	runApplication<RentriteBackendApplication>(*args)
}

@Bean
fun corsConfigurer(): WebMvcConfigurer {
	return object: WebMvcConfigurer {
		override fun addCorsMappings(registry: CorsRegistry) {
			registry.addMapping("/greeting").allowedOrigins("*").allowCredentials(true)
		}
	}
}