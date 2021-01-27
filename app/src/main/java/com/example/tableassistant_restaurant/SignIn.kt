package com.example.tableassistant_restaurant

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.tableassistant_restaurant.models.UserRestaurant
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignIn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)


        // Choose authentication providers
        val RC_SIGN_IN = 1
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build(),
            //AuthUI.IdpConfig.FacebookBuilder().build(),
            //AuthUI.IdpConfig.TwitterBuilder().build()
        )

        // Create and launch sign-in intent
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setIsSmartLockEnabled(!BuildConfig.DEBUG /* credentials */, true /* hints */)
                .setAvailableProviders(providers)
                    .setLogo(R.drawable._4_845658_dining_table_vector_logo_dinning_room) // Set logo drawable
                .build(),
            RC_SIGN_IN)



//                AuthUI.getInstance()
//            .signOut(this)
//            .addOnCompleteListener {
//                // ...
//            }

//                AuthUI.getInstance()
//            .delete(this)
//            .addOnCompleteListener {
//                // ...
//            }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        println("here")
        if (requestCode == 1) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                val userObj= UserRestaurant()
                userObj.restaurantEmail=user?.email
                val db = Firebase.firestore
                val docRef= db.collection("UsersRestaurants").document(userObj.restaurantEmail.toString()).get()
                    .addOnSuccessListener { doc ->
                        if (doc.exists()) {

                            val intentMainActivity = Intent(this, MainActivity::class.java)
                            startActivity(intentMainActivity)

                        } else {
                            Log.d(ContentValues.TAG, "No such restaurant, create one")
                            db.collection("UsersRestaurants").document(userObj.restaurantEmail.toString()).set(userObj)
                            val intentCreateRestaurantActivity =
                                Intent(this, CreateRestaurant::class.java)
                            intent.putExtra("restaurantEmail", userObj.restaurantEmail)
                            startActivity(intentCreateRestaurantActivity)
                        }

                    }
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }
}