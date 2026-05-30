package com.example.qchapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LocalRecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipes: List<LocalRecipeEntity>)

    @Query("SELECT * FROM recipes")
    suspend fun getAllRecipes(): List<LocalRecipeEntity>

    @Query("SELECT * FROM recipes WHERE id = :recipeId LIMIT 1")
    suspend fun getRecipeById(recipeId: Int): LocalRecipeEntity?

    @Query(
        """
    SELECT * FROM recipes
    WHERE LOWER(ingredients) LIKE '%' || LOWER(:query) || '%'
    OR LOWER(title) LIKE '%' || LOWER(:query) || '%'
    OR LOWER(category) LIKE '%' || LOWER(:query) || '%'
    LIMIT 20
    """
    )
    suspend fun searchRecipes(query: String): List<LocalRecipeEntity>

    @Query("SELECT COUNT(*) FROM recipes")
    suspend fun getRecipesCount(): Int
}