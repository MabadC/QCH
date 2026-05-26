package com.example.qchapp.data.remote

import com.example.qchapp.BuildConfig

object TestRepository {

    suspend fun searchRecipes(
        ingredients: List<String>,
        restrictedIngredients: List<String>,
        number: Int = 10,
        offset: Int = 0
    ): RecipeResponse {

        return RetrofitInstance.api.searchRecipes(
            apiKey = BuildConfig.SPOONACULAR_API_KEY,
            includeIngredients = ingredients.joinToString(","),
            excludeIngredients = restrictedIngredients
                .takeIf { it.isNotEmpty() }
                ?.joinToString(","),
            number = number,
            offset = offset
        )
    }
    suspend fun getRecipeInformation(
        recipeId: Int
    ): ApiRecipeDetail {

        return RetrofitInstance.api.getRecipeInformation(
            apiKey = BuildConfig.SPOONACULAR_API_KEY,
            recipeId = recipeId
        )
    }
}