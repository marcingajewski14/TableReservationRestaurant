package com.example.tableassistant_restaurant

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.tableassistant_restaurant.models.Restaurant
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CreateRestaurant : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_restaurant)



        val editTextRestaurantName=findViewById<EditText>(R.id.restaurant_provide_name)
        val editTextRestaurantCountry=findViewById<EditText>(R.id.restaurant_provide_country)
        val editTextRestaurantCity=findViewById<EditText>(R.id.restaurant_provide_city)
        val editTextRestaurantPostalCode=findViewById<EditText>(R.id.restaurant_provide_postal_code)
        val editTextRestaurantStreet=findViewById<EditText>(R.id.restaurant_provide_street)
        val editTextRestaurantNumber=findViewById<EditText>(R.id.restaurant_provide_number)
        val editTextRestaurantType=findViewById<EditText>(R.id.restaurant_type)
        val editTextRestaurantWorkingHours=findViewById<EditText>(R.id.restaurant_working_hours)
        val editTextRestaurantDescription=findViewById<EditText>(R.id.restaurant_description)

        var restaurant=Restaurant()
        val buttonMakeReservation = findViewById<Button>(R.id.buttonCreateRestaurant)
        buttonMakeReservation.setOnClickListener{
            val intentMakeReservation = Intent(this, MainActivity::class.java)
            restaurant.Name=editTextRestaurantName.text.toString()
            restaurant.Country=editTextRestaurantCountry.text.toString()
            restaurant.City=editTextRestaurantCity.text.toString()
            restaurant.PostalCode=editTextRestaurantPostalCode.text.toString()
            restaurant.Street=editTextRestaurantStreet.text.toString()
            restaurant.Number=editTextRestaurantNumber.text.toString()
            restaurant.Type=editTextRestaurantType.text.toString()
            restaurant.WorkingHours=editTextRestaurantWorkingHours.text.toString()
            restaurant.Description=editTextRestaurantDescription.text.toString()
            restaurant.id=editTextRestaurantCity.text.toString()+"_"+editTextRestaurantName.text.toString()


            val db = Firebase.firestore
            val restaurantRef=db.collection("Restaurants").document(restaurant.id!!)
            restaurantRef.set(restaurant)

            val user = FirebaseAuth.getInstance().currentUser
            db.collection("UsersRestaurants").document(user?.email.toString()).update("restaurantRef",restaurantRef )

            startActivity(intentMakeReservation)
        }






    }
}