package com.example.tableassistant_restaurant

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tableassistant_restaurant.adapters.ItemAdapterReservations
import com.example.tableassistant_restaurant.models.Reservation
import com.example.tableassistant_restaurant.models.UserRestaurant
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Access a Cloud Firestore instance from your Activity
        val db = Firebase.firestore

        val reservations: MutableList<Reservation> = mutableListOf()

        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {
            val userRef = db.collection("UsersRestaurants").document(user.email.toString())
            userRef.get().addOnSuccessListener { documentSnapshot ->
                val userRestaurant = documentSnapshot.toObject<UserRestaurant>()

                val restaurantRef=userRestaurant?.restaurantRef
                restaurantRef?.collection("Reservations")
                        ?.get()
                        ?.addOnSuccessListener { result ->
                            for (document in result) {
                                Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                                val reservation: Reservation = document.toObject<Reservation>()
                                reservations.add(reservation)
                            }
                            val manager = LinearLayoutManager(this)

                            val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewReservations)
                            recyclerView.setLayoutManager(manager)
                            recyclerView.adapter = ItemAdapterReservations(this, reservations)
                            recyclerView.setHasFixedSize(true)
                        }
                        ?.addOnFailureListener { exception ->
                            Log.d(ContentValues.TAG, "Error getting documents: ", exception)
                        }
                  }
        }
    }
}