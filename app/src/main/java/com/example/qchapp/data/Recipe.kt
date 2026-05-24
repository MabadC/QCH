package com.example.qchapp.data

data class Recipe(

    val id: Int,

    val title: String,

    val image: Int,

    val ingredients: List<String>,

    val restrictedIngredients: List<String> = emptyList(),

    val time: String,

    val difficulty: String,

    val servings: String,

    val description: String,

    val isSaved: Boolean = false
)