package com.emeraldeveryapp.rentritebackend.storage

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.emeraldeveryapp.rentritebackend.api.RentalProfile
import com.emeraldeveryapp.rentritebackend.api.Comment
import com.emeraldeveryapp.rentritebackend.api.RentalFeature
import java.sql.ResultSet
import java.sql.Connection
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
    val GET_COMMENT_BY_USER_QUERY =
        """
            SELECT
                c.comment_id AS id,
                c.tags AS tags,
                c.comment_text AS text,
                c.user_name AS user_name
            FROM comments c
            WHERE c.user_name = ?
        """
    val INSERT_PROFILE_QUERY =
        """
            INSERT INTO rental_profiles
                (address, pics)
            VALUES
                (?, ?);
        """
    val INSERT_COMMENT_QUERY =
        """
            INSERT INTO comments
                (address, user_name, comment_text, tags)
            VALUES
                (?, ?, ?, ?);
        """
    val INSERT_USER_QUERY =
        """
            INSERT INTO users
                (user_name, email)
            VALUES
                (?, ?)
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
        return rentalResult
    }

    // fun getCommentByUser(userName: String): List<Comment> {
    //     val comments = jdbcTemplate.queryForList<List<Comment>>(GET_COMMENT_BY_USER_QUERY, , userName)
    // }
    
    
    fun insertRentalProfile(profile: RentalProfile) {
        if (profile.comments.size > 1) {
            throw IllegalArgumentException("Only one comment may be specified on a new profile.")
        }

        jdbcTemplate.update(
            INSERT_PROFILE_QUERY,
            profile.address,
            createPicsSqlArray(profile.picIds))
        // TODO: replace placeholder with user name and add user
        insertComment(profile.address, "placeholder", profile.comments.get(0), profile.tags)
    }

    fun insertComment(address: String, userName: String, commentText: String, tags: List<RentalFeature>) {
        jdbcTemplate.update(
            INSERT_COMMENT_QUERY,
            address,
            userName,
            commentText,
            createTagSqlArray(tags))
    }
    
    fun insertUser(userName: String, userEmail: String) {
        jdbcTemplate.update(
            INSERT_USER_QUERY,
            userName,
            userEmail
        )
    }
    
    private fun createTagSqlArray(tags: List<RentalFeature>): java.sql.Array? {
        val dbTagList = tags.asSequence().map { tagFromApiToStorage(it) }.toList()
        return jdbcTemplate.getDataSource()?.getConnection()
        ?.createArrayOf("comment_tag", dbTagList.toTypedArray())
    }

    private fun createPicsSqlArray(pics: List<String>): java.sql.Array? {
        return jdbcTemplate.getDataSource()?.getConnection()
        ?.createArrayOf("text", pics.toTypedArray())
    }
}