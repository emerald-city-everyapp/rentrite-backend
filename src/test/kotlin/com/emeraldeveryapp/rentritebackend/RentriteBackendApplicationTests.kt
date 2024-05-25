package com.emeraldeveryapp.rentritebackend

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.assertj.core.api.Assertions.assertThat
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.jdbc.core.JdbcTemplate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase()
class RestTestApplicationTests(@Autowired val restTemplate: TestRestTemplate, @Autowired val jdbcTemplate: JdbcTemplate) {

	@BeforeEach
	fun before() {
		this.jdbcTemplate.execute("CREATE TABLE rental_profile (address VARCHAR(255), PRIMARY KEY (address));")
		this.jdbcTemplate.execute("INSERT INTO rental_profile VALUES ('123 first street');")
	}

	@AfterEach
	fun after() {
		this.jdbcTemplate.execute("DROP TABLE IF EXISTS rental_profile")
	}

	@Test
	fun `Assert content and status code`() {
		val entity = restTemplate.getForEntity("/greeting", Greeting::class.java)
		assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(entity.body).isEqualTo(Greeting(1, "Hello World! Query result 123 first street!"))
	}

}
