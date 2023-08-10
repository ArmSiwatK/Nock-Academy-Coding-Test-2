package com.example.nockacademycodingtest2.ui.menuList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nockacademycodingtest2.R
import com.example.nockacademycodingtest2.model.MenuItem
import com.squareup.picasso.Picasso

class MenuListAdapter(private val menuItems: List<MenuItem>) :
    RecyclerView.Adapter<MenuListAdapter.MenuViewHolder>() {

    inner class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val menuImage: ImageView = itemView.findViewById(R.id.menuImage)
        val menuName: TextView = itemView.findViewById(R.id.menuName)
        val menuPrice: TextView = itemView.findViewById(R.id.menuPrice)
        val checkIcon: ImageView = itemView.findViewById(R.id.checkIcon)
    }

    private fun calculateTotalPrice(): Double {
        var total = 0.0
        for (menuItem in menuItems) {
            if (menuItem.isSelected) {
                total += menuItem.price.toDouble()
            }
        }
        return total
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_menu, parent, false)
        return MenuViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val currentMenuItem = menuItems[position]

        if (currentMenuItem.isSelected) {
            holder.checkIcon.visibility = View.VISIBLE
        } else {
            holder.checkIcon.visibility = View.GONE
        }

        Picasso.get().load(currentMenuItem.image).into(holder.menuImage)
        holder.menuName.text = currentMenuItem.name
        holder.menuPrice.text = holder.itemView.context.getString(R.string.menu_price_format, currentMenuItem.price)

        holder.itemView.setOnClickListener {
            currentMenuItem.isSelected = !currentMenuItem.isSelected
            notifyDataSetChanged()

            val totalLayout = holder.itemView.rootView.findViewById<LinearLayout>(R.id.totalLayout)
            val totalPriceTextView = holder.itemView.rootView.findViewById<TextView>(R.id.totalPriceTextView)
            val nextButton = holder.itemView.rootView.findViewById<Button>(R.id.nextButton)

            val total = calculateTotalPrice()
            if (total > 0) {
                totalLayout.visibility = View.VISIBLE
                totalPriceTextView.text = "Total Price: $total THB"
            } else {
                totalLayout.visibility = View.GONE
            }
        }
    }

    override fun getItemCount() = menuItems.size
}
