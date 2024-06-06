package com.emeraldeveryapp.rentritebackend.api

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.beans.factory.annotation.Autowired
import java.util.concurrent.atomic.AtomicLong
import com.emeraldeveryapp.rentritebackend.storage.CloudSqlStorage

@CrossOrigin
@RestController
class RentalProfileController (@Autowired val cloudSqlStorage: CloudSqlStorage) {
    val counter: AtomicLong = AtomicLong()

    @GetMapping("/rentalprofile")
    fun getRentalProfile(@RequestParam(value = "address", defaultValue = "") address: String): RentalProfile {
        println("Looking up rental profile for address $address")
        val profile = cloudSqlStorage.getRentalProfile(address)
        if (profile != null) {
            return profile
        }
        throw ProfileNotFoundException()
    }
    
    @PostMapping("/rentalprofile")
    fun newRentalProfile(@RequestBody rentalProfile: RentalProfile): RentalProfile {
        println("Adding new rental profile for address ${rentalProfile.address}")
        try {
            cloudSqlStorage.insertRentalProfile(rentalProfile)
        } catch (e: RuntimeException) {
            throw PrfileAlreadyExistsException()
        }
        return rentalProfile
    }
}