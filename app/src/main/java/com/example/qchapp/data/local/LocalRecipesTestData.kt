package com.example.qchapp.data.local

object LocalRecipeTestData {

    val recipes = listOf(
        LocalRecipeEntity(
            id = 1,
            title = "Tortilla de patatas",
            image = "",
            ingredients = "patatas, huevos, cebolla, aceite, sal",
            instructions = "Pelar y cortar las patatas. Freírlas con la cebolla. Batir los huevos, mezclar y cuajar en la sartén.",
            readyInMinutes = 35,
            category = "cocina española",
            difficulty = "facil",
            calories = 250,
            servings = 4
        ),
        LocalRecipeEntity(
            id = 2,
            title = "Ensalada de pasta",
            image = "",
            ingredients = "pasta, tomate, atún, maíz, aceitunas",
            instructions = "Cocer la pasta. Enfriar y mezclar con tomate, atún, maíz y aceitunas. Servir fría.",
            readyInMinutes = 20,
            category = "cocina española",
            difficulty = "facil",
            calories = 250,
            servings = 2
        )
    )
}