package com.example.qchapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SpoonacularApi {

    @GET("recipes/complexSearch")
    suspend fun searchRecipes(
        @Header("apikey") apiKey: String,
        @Query("query") query: String,
        @Query("number") number: Int = 10
    ): RecipeResponse
}