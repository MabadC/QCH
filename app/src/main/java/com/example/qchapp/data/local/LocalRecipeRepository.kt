package com.example.qchapp.data.local

import android.content.Context

object LocalRecipeRepository {

    private fun dao(context: Context): LocalRecipeDao {
        return DatabaseProvider
            .getDatabase(context)
            .localRecipeDao()
    }

    suspend fun insertRecipes(
        context: Context,
        recipes: List<LocalRecipeEntity>
    ) {
        dao(context).insertRecipes(recipes)
    }

    suspend fun getAllRecipes(
        context: Context
    ): List<LocalRecipeEntity> {
        return dao(context).getAllRecipes()
    }

    suspend fun getRecipeById(
        context: Context,
        recipeId: Int
    ): LocalRecipeEntity? {
        return dao(context).getRecipeById(recipeId)
    }

    suspend fun searchRecipes(
        context: Context,
        query: String
    ): List<LocalRecipeEntity> {
        return dao(context).searchRecipes(query)
    }

    suspend fun getRecipesCount(
        context: Context
    ): Int {
        return dao(context).getRecipesCount()
    }

    suspend fun searchRecipesByIngredients(
        context: Context,
        ingredients: List<String>,
        excludedIngredients: List<String>
    ): List<LocalRecipeEntity> {

        val recipes = getAllRecipes(context)

        return recipes
            .filter { recipe ->

                excludedIngredients.none { excluded ->

                    excluded.isNotBlank() &&
                            recipe.ingredients.contains(
                                excluded,
                                ignoreCase = true
                            )
                }
            }
            .map { recipe ->

                val matches =
                    ingredients.count { ingredient ->

                        ingredient.isNotBlank() &&
                                recipe.ingredients.contains(
                                    ingredient,
                                    ignoreCase = true
                                )
                    }

                recipe to matches
            }
            .filter { it.second > 0 }
            .sortedByDescending { it.second }
            .map { it.first }
    }
}