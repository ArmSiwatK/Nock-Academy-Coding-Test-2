package com.example.nockacademycodingtest2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.nockacademycodingtest2.model.Restaurant

class RestaurantListFragment : Fragment() {

    private lateinit var restaurantRecyclerView: RecyclerView
    private lateinit var restaurantAdapter: RestaurantListAdapter
    private lateinit var requestQueue: RequestQueue

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_restaurant_list, container, false)

        restaurantRecyclerView = rootView.findViewById(R.id.restaurantRecyclerView)
        restaurantRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        requestQueue = Volley.newRequestQueue(requireContext())

        val url = "https://apiv2-uat.nockacademy.com/restaurants"
        val restaurants = mutableListOf<Restaurant>()

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                for (i in 0 until response.length()) {
                    val jsonObject = response.getJSONObject(i)
                    val id = jsonObject.getString("id")
                    val image = jsonObject.getString("image")
                    val name = jsonObject.getString("name")
                    val type = jsonObject.getString("type")
                    restaurants.add(Restaurant(id, image, name, type))
                }
                restaurantAdapter = RestaurantListAdapter(restaurants)
                restaurantAdapter.onItemClick = { selectedRestaurant ->
                    val action = RestaurantListFragmentDirections
                        .actionRestaurantListFragmentToMenuListFragment(restaurantId = selectedRestaurant.id)
                    findNavController().navigate(action)
                }
                restaurantRecyclerView.adapter = restaurantAdapter
            },
            { _ ->
                // Handle error
                restaurantAdapter = RestaurantListAdapter(emptyList())
                restaurantRecyclerView.adapter = restaurantAdapter
            })

        requestQueue.add(jsonArrayRequest)

        return rootView
    }
}
