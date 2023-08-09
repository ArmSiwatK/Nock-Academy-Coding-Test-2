package com.example.nockacademycodingtest2.model

data class MenuItem(
    val image: String,
    val name: String,
    val price: String,
    var isSelected: Boolean = false
)
