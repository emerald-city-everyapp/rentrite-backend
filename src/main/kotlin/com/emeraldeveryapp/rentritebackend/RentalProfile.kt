package com.emeraldeveryapp.rentritebackend

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

data class RentalProfile(
    val address: String,
    val picIds: List<String>,
    val tags: List<RentalFeature>,
    val comments: List<String>
)