package com.example.qchapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class LocalRecipeEntity(

    @PrimaryKey
    val id: Int,

    val title: String,

    val image: String = "",

    val ingredients: String,

    val instructions: String,

    val readyInMinutes: Int,

    val servings: Int,

    val category: String,

    val difficulty: String,

    val calories: Int
)