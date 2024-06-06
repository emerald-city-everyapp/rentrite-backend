package com.emeraldeveryapp.rentritebackend.api

enum class RentalFeature {
    RegisteredToRent,
    Parking,
    Mold,
    PetFriendly,
    SharedWalls,
    Accessibility,
    AccessToUtilityPanels,
    AccessoryDwellingUnit
}

data class User (
    val name: String,
    val email: String
)

data class Comment (
    val tags: List<RentalFeature>,
    val comment_text: String,
    val user_name: String
)

data class RentalProfile(
    val address: String,
    val picIds: List<String>,
    val tags: List<RentalFeature>,
    val comments: List<String>
)