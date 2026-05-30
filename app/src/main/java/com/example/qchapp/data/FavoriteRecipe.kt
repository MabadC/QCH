package com.example.qchapp.data

data class FavoriteRecipe(
    val id: Int = 0,
    val title: String = "",
    val image: String = "",
    val readyInMinutes: Int = 0,
    val servings: Int = 1
)