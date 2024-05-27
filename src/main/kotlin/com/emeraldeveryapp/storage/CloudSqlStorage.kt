package com.emeraldeveryapp.rentritebackend.storage

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.emeraldeveryapp.rentritebackend.RentalProfile
import com.emeraldeveryapp.rentritebackend.RentalFeature
import java.sql.ResultSet
import java.util.stream.Stream
import kotlinx.serialization.json.Json
import kotlin.reflect.typeOf
import kotlin.streams.toList


@Component
class CloudSqlStorage (@Autowired val jdbcTemplate: JdbcTemplate) {
    val GET_PROFILE_QUERY =
        """
            SELECT
                rp.address AS address,
                rp.pics AS pics,
                ARRAY_AGG(ft) AS tags,
                ARRAY_AGG(c.comment_text) AS comment_list
            FROM rental_profiles AS rp
            LEFT JOIN comments c
                ON (rp.address = c.address)
            LEFT JOIN LATERAL UNNEST(c.tags) ft ON true
            WHERE rp.address = ?
            GROUP BY rp.address, rp.pics
        """
    val INSERT_PROFILE_QUERY =
        """
            INSERT INTO rental_profiles (address, pics)
            VALUES
                ROW(?, JSON_ARRAY(?));
        """
    val INSERT_COMMENT_QUERY =
        """
            INSERT INTO comments
                (address, user_name, comment_text, tags)
            VALUES
                ROW(?, ?, ?, JSON_ARRAY(?));
        """
    val INSERT_USER_QUERY =
        """
        """
    
    val rentalProfileMapper = 
        { rs: ResultSet, _: Int -> 
            val addressText = rs.getString("address")
            val pics = rs.getArray("pics").getArray() as Array<String>
            val tagsArray = rs.getArray("tags").getArray() as Array<String>
            val tags = tagsArray.asSequence().map { tagFromStorageToApi(it) }.toList()
            val comments = rs.getArray("comment_list").getArray() as Array<String>
            RentalProfile(addressText, pics.toList(), tags, comments.toList())
        }

    fun getRentalProfile(address: String): RentalProfile? {
        val rentalResult = jdbcTemplate.queryForObject(
            GET_PROFILE_QUERY, rentalProfileMapper, address)
        println(rentalResult)
        return rentalResult
    }
    
    // fun insertRentalProfile(profile: RentalProfile) {
    //     val formattedPicList = profile.picIds.joinToString(", ") { String.format("\'$it\'") }

    //     jdbcTemplate.update(INSERT_PROFILE_QUERY, profile.address, formattedPicList)
    //     for (comment in profile.comments) {
    //         insertComment(profile.address, "placeholder", comment, listOf())
    //     }
    // }
    
    // fun insertComment(address: String, userName: String, commentText: String, tags: List<RentalFeature>) {
    //     val formattedTagList = tags.joinToString(", ") { String.format("\'$it\'") }
    //     jdbcTemplate.update(INSERT_COMMENT_QUERY, address, userName, commentText, formattedTagList)
    // }
}