package com.example.qchapp.data.remote

data class RecipeResponse(
    val results: List<ApiRecipe>
)

data class ApiRecipe(

    val id: Int,
    val title: String,
    val image: String?,
    val readyInMinutes: Int?,
    val servings: Int?
)

data class ApiRecipeDetail(
    val id: Int,
    val title: String,
    val image: String?,
    val readyInMinutes: Int?,
    val servings: Int?,
    val extendedIngredients: List<ApiIngredient>?,
    val instructions: String?
)

data class ApiIngredient(
    val original: String?
)