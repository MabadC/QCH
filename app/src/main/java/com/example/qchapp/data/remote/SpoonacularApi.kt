package com.example.qchapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.Path

interface SpoonacularApi {

    @GET("recipes/complexSearch")
    suspend fun searchRecipes(
        @Header("apikey") apiKey: String,
        @Query("includeIngredients") includeIngredients: String,
        @Query("excludeIngredients") excludeIngredients: String? = null,
        @Query("number") number: Int = 10,
        @Query("offset") offset: Int = 0,
        @Query("addRecipeInformation") addRecipeInformation: Boolean = true
    ): RecipeResponse

    @GET("recipes/{id}/information")
    suspend fun getRecipeInformation(
        @Header("apikey") apiKey: String,
        @Path("id") recipeId: Int
    ): ApiRecipeDetail
}
