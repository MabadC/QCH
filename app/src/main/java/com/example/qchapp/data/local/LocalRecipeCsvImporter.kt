package com.example.qchapp.data.local

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object LocalRecipeCsvImporter {

    suspend fun importRecipes(context: Context) {

        withContext(Dispatchers.IO) {

            try {

                val dao =
                    DatabaseProvider
                        .getDatabase(context)
                        .localRecipeDao()

                val existingRecipes =
                    dao.getRecipesCount()

                if (existingRecipes > 100) {
                    return@withContext
                }

                val recipes = mutableListOf<LocalRecipeEntity>()

                context.assets
                    .open("test.csv")
                    .bufferedReader()
                    .useLines { lines ->

                        lines
                            .drop(1)
                            .forEachIndexed { index, line ->

                                try {

                                    val parts =
                                        line.split(",")

                                    if (parts.size < 4) {
                                        return@forEachIndexed
                                    }

                                    val title =
                                        parts[0]
                                            .trim()

                                    val ingredients =
                                        parts[2]
                                            .trim()

                                    val instructions =
                                        parts[3]
                                            .trim()

                                    recipes.add(

                                        LocalRecipeEntity(

                                            id = 100000 + index,

                                            title = title,

                                            image = "",

                                            ingredients = ingredients,

                                            instructions = instructions,

                                            readyInMinutes = 30,

                                            servings = 4,

                                            category = "General",

                                            difficulty = "media",

                                            calories = 0
                                        )
                                    )

                                } catch (_: Exception) {
                                }
                            }
                    }

                dao.insertRecipes(recipes)

                Log.d(
                    "CSV_IMPORT",
                    "Importadas ${recipes.size} recetas CSV"
                )

            } catch (e: Exception) {

                Log.e(
                    "CSV_IMPORT",
                    "Error importando CSV",
                    e
                )
            }
        }
    }
}