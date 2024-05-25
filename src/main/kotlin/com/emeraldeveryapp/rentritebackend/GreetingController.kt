package com.emeraldeveryapp.rentritebackend

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.beans.factory.annotation.Autowired
import java.util.concurrent.atomic.AtomicLong


@CrossOrigin()
@RestController
class GreetingController (@Autowired val jdbcTemplate: JdbcTemplate) {
    val template = "Hello %s! Query result %s!"
    val counter: AtomicLong = AtomicLong()
    val dbName = System.getenv("DB_NAME")
    val dbUser = System.getenv("DB_USER")
    
    @GetMapping("/greeting")
    fun greeting(@RequestParam(value = "name", defaultValue = "World") name: String): Greeting {
        println("Db name: %s" + dbName)
        println("Db user: %s" + dbUser)
        val queryResult = this.jdbcTemplate.queryForObject("SELECT address FROM rental_profile", String::class.java)
        println("maeve" + queryResult)
        return Greeting(counter.incrementAndGet(), String.format(template, name, queryResult))
    }
}