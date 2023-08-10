package com.example.nockacademycodingtest2.ui.menuList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.nockacademycodingtest2.R
import com.example.nockacademycodingtest2.model.MenuItem
import com.example.nockacademycodingtest2.model.RestaurantDetail
import com.squareup.picasso.Picasso
import org.json.JSONObject

class MenuListFragment : Fragment() {

    private lateinit var menuRecyclerView: RecyclerView
    private lateinit var menuAdapter: MenuListAdapter
    private lateinit var restaurantId: String
    private lateinit var requestQueue: RequestQueue

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_menu_list, container, false)

        val args = MenuListFragmentArgs.fromBundle(requireArguments())
        restaurantId = args.restaurantId

        menuRecyclerView = rootView.findViewById(R.id.menuRecyclerView)
        menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        requestQueue = Volley.newRequestQueue(requireContext())

        val url = "https://apiv2-uat.nockacademy.com/restaurants/$restaurantId"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                val restaurant = parseRestaurantResponse(response)

                val menuItems = restaurant.menu

                val restaurantImage = rootView.findViewById<ImageView>(R.id.restaurantImage)
                val restaurantName = rootView.findViewById<TextView>(R.id.restaurantName)
                val restaurantType = rootView.findViewById<TextView>(R.id.restaurantType)

                Picasso.get().load(restaurant.image).into(restaurantImage)
                restaurantName.text = restaurant.name
                restaurantType.text = restaurant.type

                menuAdapter = MenuListAdapter(menuItems)
                menuRecyclerView.adapter = menuAdapter
            },
            { _ ->
                // Handle error
            })

        requestQueue.add(jsonObjectRequest)

        return rootView
    }

    private fun parseRestaurantResponse(response: JSONObject): RestaurantDetail {
        val restaurantId = response.getString("id")
        val restaurantImage = response.getString("image")
        val restaurantName = response.getString("name")
        val restaurantType = response.getString("type")

        val menuArray = response.getJSONArray("menu")
        val menuItems = mutableListOf<MenuItem>()

        for (i in 0 until menuArray.length()) {
            val menuItemObject = menuArray.getJSONObject(i)
            val image = menuItemObject.getString("image")
            val name = menuItemObject.getString("name")
            val price = menuItemObject.getString("price")
            menuItems.add(MenuItem(image, name, price))
        }

        return RestaurantDetail(restaurantId, restaurantImage, restaurantName, restaurantType, menuItems)
    }
}
