package com.example.qchapp.data.local

import com.google.gson.annotations.SerializedName

data class LocalRecipeJson(

    val id: Int,

    @SerializedName("nombre")
    val title: String,

    @SerializedName("ingredientes")
    val ingredients: List<String>,

    @SerializedName("categoria")
    val category: String,

    @SerializedName("dificultad")
    val difficulty: String,

    @SerializedName("tiempo_minutos")
    val readyInMinutes: Int,

    @SerializedName("porciones")
    val servings: Int,

    @SerializedName("instrucciones")
    val instructions: String,

    @SerializedName("calorias_aprox")
    val calories: Int
)