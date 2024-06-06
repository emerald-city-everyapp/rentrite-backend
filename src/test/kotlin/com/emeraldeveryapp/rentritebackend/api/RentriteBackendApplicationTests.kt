package com.emeraldeveryapp.rentritebackend.api

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
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator
import org.springframework.core.io.ClassPathResource
import io.zonky.test.db.AutoConfigureEmbeddedDatabase
import javax.sql.DataSource

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureEmbeddedDatabase
class RestTestApplicationTests(@Autowired val restTemplate: TestRestTemplate, @Autowired val jdbcTemplate: JdbcTemplate) {
    val SEEDED_TEST_ADDRESS = "123 King Street, London"
    val SEEDED_TEST_PIC_LIST = listOf("pic1", "pic2")
    val TEST_COMMENT = "some mean comment"
    val TEST_PROFILE = RentalProfile(
        "Some new addres yeah",
        listOf("first pic name", "Second pic name"),
        listOf(RentalFeature.AccessoryDwellingUnit),
        listOf(TEST_COMMENT)
    )

    @BeforeEach
    fun createTables(@Autowired dataSource: DataSource) {
        val populator = ResourceDatabasePopulator()
        populator.addScripts(
            ClassPathResource("storage/test-schema.sql"),
            ClassPathResource("storage/insert-test-data.sql"))
        populator.execute(dataSource)
    }

	@Test
	fun `Assert content for getRentalProfile and status code`() {
		val entity = restTemplate.getForEntity("/rentalprofile?address=$SEEDED_TEST_ADDRESS", RentalProfile::class.java)
		assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(entity.body!!.address).isEqualTo(SEEDED_TEST_ADDRESS)
		assertThat(entity.body!!.picIds).containsExactlyInAnyOrderElementsOf(SEEDED_TEST_PIC_LIST)
	}
	
	@Test
	fun `Assert content and status code for newRentalProfile`() {
		val result = restTemplate.postForEntity("/rentalprofile", TEST_PROFILE, RentalProfile::class.java)
		assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(result.body!!.address).isEqualTo(TEST_PROFILE.address)
	}
}
