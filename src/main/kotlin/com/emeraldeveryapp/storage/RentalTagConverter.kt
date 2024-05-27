package com.emeraldeveryapp.rentritebackend.storage

import com.emeraldeveryapp.rentritebackend.RentalFeature
import java.io.IOException

fun tagFromStorageToApi(tagVal: String): RentalFeature {
    return when (tagVal) {
        "registerred_to_rent" -> RentalFeature.RegisteredToRent
        "mold" -> RentalFeature.Mold
        "pet_friendly" -> RentalFeature.PetFriendly
        "parking" -> RentalFeature.Parking
        "shared_walls" -> RentalFeature.SharedWalls
        "accessibility" -> RentalFeature.Accessibility
        "access_to_utility_panels" -> RentalFeature.AccessToUtilityPanels
        "accessory_dwelling_unit" -> RentalFeature.AccessoryDwellingUnit
        else -> throw IOException("Unknown Rental Feature returned from query.")
    }
}

fun tagFromApiToStorage(feature: RentalFeature): String {
    return when (feature) {
        RentalFeature.RegisteredToRent -> "registerred_to_rent" 
        RentalFeature.Mold -> "mold"
        RentalFeature.PetFriendly -> "pet_friendly"
        RentalFeature.Parking -> "parking"
        RentalFeature.SharedWalls -> "shared_walls"
        RentalFeature.Accessibility -> "accessibility"
        RentalFeature.AccessToUtilityPanels -> "access_to_utility_panels"
        RentalFeature.AccessoryDwellingUnit -> "accessory_dwelling_unit"
    }
}