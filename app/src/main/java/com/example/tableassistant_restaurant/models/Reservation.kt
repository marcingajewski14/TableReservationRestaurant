package com.example.tableassistant_restaurant.models

data class Reservation (    //var DateTime: DateTime?=null,
    var reservationId: String?=null,
    var reservationName: String?=null,
    var numberOfPeople: String?=null,
    var status: String?=null,
    var data: com.google.firebase.Timestamp?=null,
    var restaurantName: String?=null,
    var restaurantRef: com.google.firebase.firestore.DocumentReference?=null,
    var userRef: com.google.firebase.firestore.DocumentReference?=null
) {}