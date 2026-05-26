package com.example.qchapp.data.remote

import com.example.qchapp.BuildConfig

object TestRepository {

    suspend fun searchRecipes(
        query: String
    ): RecipeResponse {

        return RetrofitInstance.api.searchRecipes(
            apiKey = BuildConfig.SPOONACULAR_API_KEY,
            query = query
        )
    }
}