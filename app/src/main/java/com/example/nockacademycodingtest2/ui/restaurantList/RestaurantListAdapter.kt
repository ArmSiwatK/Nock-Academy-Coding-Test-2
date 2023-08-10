package com.example.nockacademycodingtest2.ui.restaurantList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nockacademycodingtest2.R
import com.example.nockacademycodingtest2.model.Restaurant
import com.squareup.picasso.Picasso

class RestaurantListAdapter(private val restaurants: List<Restaurant>) :
    RecyclerView.Adapter<RestaurantListAdapter.RestaurantViewHolder>() {

    var onItemClick: ((Restaurant) -> Unit)? = null

    class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val restaurantImage: ImageView = itemView.findViewById(R.id.restaurantImage)
        val restaurantName: TextView = itemView.findViewById(R.id.restaurantName)
        val restaurantType: TextView = itemView.findViewById(R.id.restaurantType)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_restaurant, parent, false)
        return RestaurantViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val currentRestaurant = restaurants[position]

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(currentRestaurant)
        }

        Picasso.get().load(currentRestaurant.image).into(holder.restaurantImage)
        holder.restaurantName.text = currentRestaurant.name
        holder.restaurantType.text = currentRestaurant.type
    }

    override fun getItemCount() = restaurants.size
}
