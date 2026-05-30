package com.example.qchapp.data.local

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object LocalRecipeImporter {

    suspend fun importRecipes(context: Context) {

        val dao = DatabaseProvider
            .getDatabase(context)
            .localRecipeDao()

        if (dao.getRecipesCount() > 0) {
            return
        }

        val jsonString = context.assets
            .open("recetas_espanolas.json")
            .bufferedReader()
            .use { it.readText() }

        val type = object : TypeToken<List<LocalRecipeJson>>() {}.type

        val recipes: List<LocalRecipeJson> =
            Gson().fromJson(jsonString, type)

        val entities = recipes.map { recipe ->

            LocalRecipeEntity(
                id = recipe.id,
                title = recipe.title,
                image = "",
                ingredients = recipe.ingredients.joinToString(", "),
                instructions = recipe.instructions,
                readyInMinutes = recipe.readyInMinutes,
                servings = recipe.servings,
                category = recipe.category,
                difficulty = recipe.difficulty,
                calories = recipe.calories
            )
        }

        dao.insertRecipes(entities)
    }
}