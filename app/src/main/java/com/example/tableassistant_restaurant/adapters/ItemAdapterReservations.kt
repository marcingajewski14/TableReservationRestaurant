package com.example.tableassistant_restaurant.adapters

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tableassistant_restaurant.MainActivity
import com.example.tableassistant_restaurant.R
import com.example.tableassistant_restaurant.models.Reservation
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ItemAdapterReservations(private val context: Context, private val dataset: List<Reservation>): RecyclerView.Adapter<ItemAdapterReservations.ItemViewHolder>() {

    class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        //val textViewRestaurantId: TextView = view.findViewById(R.id.restaurant_id)
        val textViewReservationDate: TextView = view.findViewById(R.id.reservation_date)
        val textViewReservationName: TextView = view.findViewById(R.id.reservation_name)
        val textViewReservationNumberOfPeople: TextView = view.findViewById(R.id.reservation_no_of_people)
        val textViewReservationStatus: TextView = view.findViewById(R.id.reservation_status)
        val buttonConfirmReservation: Button =view.findViewById(R.id.buttonConfirmReservation)
        val buttonRejectReservation: Button =view.findViewById(R.id.buttonRejectReservation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.reservation_list_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        //holder.textViewRestaurantId.text =  context.resources.getString(item.Id)
        holder.textViewReservationDate.text = "Date: " + item.data?.toDate().toString()
        holder.textViewReservationName.text="Name: "+item.reservationName
        holder.textViewReservationNumberOfPeople.text="Number of people: "+item.numberOfPeople
        holder.textViewReservationStatus.text = "Status: "+ item.status
        val ID=item.reservationId

        val db = Firebase.firestore
        holder.buttonConfirmReservation.setOnClickListener{
            val context = holder.view.context
            val intent = Intent(context, MainActivity::class.java) //change here
            item.restaurantRef?.collection("Reservations")?.document(item.reservationId!!)?.update(mapOf(
                    "status" to "Accepted",
            ))
            item.userRef?.collection("Reservations")?.document(item.reservationId!!)?.update(mapOf(
                    "status" to "Accepted",
            ))

            context.startActivity(intent)
        }


        holder.buttonRejectReservation.setOnClickListener{
            val context = holder.view.context
            val intent = Intent(context, MainActivity::class.java) //change here
            println("resID")
            println(item.reservationId!!)
            Log.d(ContentValues.TAG, "Restaurant data: ${item.reservationId}")

            item.restaurantRef?.collection("Reservations")?.document(item.reservationId!!)?.update(mapOf(
                    "status" to "Rejected",
            ))
            item.userRef?.collection("Reservations")?.document(item.reservationId!!)?.update(mapOf(
                    "status" to "Rejected",
            ))
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = dataset.size



}