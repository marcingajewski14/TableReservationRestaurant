package com.example.tableassistant_restaurant.models

data class UserRestaurant (
    var restaurantEmail: String?=null,
    var restaurantRef: com.google.firebase.firestore.DocumentReference?=null
){}