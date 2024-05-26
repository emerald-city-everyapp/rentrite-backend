package com.emeraldeveryapp.rentritebackend

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.beans.factory.annotation.Autowired
import java.util.concurrent.atomic.AtomicLong

@CrossOrigin
@RestController
class RentalProfileController (@Autowired val jdbcTemplate: JdbcTemplate) {
    val counter: AtomicLong = AtomicLong()

    @GetMapping("/rentalprofile")
    fun getRentalProfile(@RequestParam(value = "address", defaultValue = "") address: String): RentalProfile {
        return RentalProfile(
            address = "123 First Street, Arkham, MA",
            listOf("pic1", "pic2"),
            listOf(RentalFeature.Parking, RentalFeature.PetFriendly),
            listOf(
                "Excellent view of the downtown. The neighbors are loud with chanting at night.",
                "The doors and fixtures seem to move around at night. Seriously, I swear the sink used to be over there...",
                "Request addres is $address",
                "Counter is ${counter.incrementAndGet()}"
            ))
    }
    
    @PostMapping("/rentalprofile")
    fun newRentalProfile(@RequestBody rentalProfile: RentalProfile): RentalProfile {
        return rentalProfile
    }
}