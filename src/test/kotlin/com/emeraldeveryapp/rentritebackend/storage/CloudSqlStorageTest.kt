package com.emeraldeveryapp.rentritebackend.storage

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.assertj.core.api.Assertions.assertThat
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator
import org.springframework.core.io.ClassPathResource
import io.zonky.test.db.AutoConfigureEmbeddedDatabase
import javax.sql.DataSource
import com.emeraldeveryapp.rentritebackend.RentalProfile
import com.emeraldeveryapp.rentritebackend.RentalFeature

@SpringBootTest
@AutoConfigureEmbeddedDatabase
class CloudSqlStorageTest(@Autowired val jdbcTemplate: JdbcTemplate, @Autowired val cloudSqlStorage: CloudSqlStorage) {
    val SEEDED_TEST_ADDRESS = "123 King Street, London"
    val SEEDED_TEST_PIC_LIST = listOf("pic1", "pic2")
    val SEEDED_TEST_TAG_LIST =
        listOf(RentalFeature.PetFriendly, RentalFeature.Parking, RentalFeature.Mold)
    val SEEDED_TEST_COMMENT_LIST =
        listOf("comment text", "extra comment info")
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
    
    @AfterEach
    fun destroyTables(@Autowired dataSource: DataSource) {
        val populator = ResourceDatabasePopulator()
        populator.addScript(ClassPathResource("storage/drop-tables.sql"))
        populator.execute(dataSource)
    }

    @Test
    fun `Assert get rental profile succeeds`() {
        val resultProfile = cloudSqlStorage.getRentalProfile(SEEDED_TEST_ADDRESS)
        assertThat(resultProfile!!.address).isEqualTo(SEEDED_TEST_ADDRESS)
        assertThat(resultProfile.picIds).containsAll(SEEDED_TEST_PIC_LIST)
        assertThat(resultProfile.tags).containsAll(SEEDED_TEST_TAG_LIST)
        assertThat(resultProfile.comments).containsAll(SEEDED_TEST_COMMENT_LIST)
    }
    
    @Test
    fun `Assert insert comment succeeds`() {
        val newUser = "new user name"
        val newComment = "Some new comment text"
        val newTags = listOf(RentalFeature.SharedWalls)
        cloudSqlStorage.insertComment(SEEDED_TEST_ADDRESS, newUser, newComment, newTags)
    }
    
    @Test
    fun `Assert new rental profile insert succeeds`() {
        cloudSqlStorage.insertRentalProfile(TEST_PROFILE)
        
        val resultProfile = cloudSqlStorage.getRentalProfile(TEST_PROFILE.address)
        assertThat(resultProfile!!.address).isEqualTo(TEST_PROFILE.address)
        assertThat(resultProfile.picIds).containsExactlyInAnyOrderElementsOf(TEST_PROFILE.picIds)
        assertThat(resultProfile.comments.get(0)).isEqualTo(TEST_COMMENT)
        assertThat(resultProfile.comments.size).isEqualTo(1)
    }
}