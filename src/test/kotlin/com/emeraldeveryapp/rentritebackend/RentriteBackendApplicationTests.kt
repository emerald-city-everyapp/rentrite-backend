package com.emeraldeveryapp.resttest

import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestTestApplicationTests(@Autowired val restTemplate: TestRestTemplate) {

	@Test
	fun `Assert content and status code`() {
		val entity = restTemplate.getForEntity("/greeting", Greeting::class.java)
		assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(entity.body).isEqualTo(Greeting(1, "Hello World!"))
	}

}
