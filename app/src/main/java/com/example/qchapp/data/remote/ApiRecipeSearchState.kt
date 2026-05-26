package com.example.qchapp.data.remote

object ApiRecipeSearchState {

    var recipes: List<ApiRecipe> = emptyList()

    var ingredients: List<String> = emptyList()

    var restrictedIngredients: List<String> = emptyList()

    var offset: Int = 0

    const val PAGE_SIZE = 10
}