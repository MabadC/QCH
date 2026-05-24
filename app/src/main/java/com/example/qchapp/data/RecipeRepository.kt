package com.example.qchapp.data

import com.example.qchapp.R

object RecipeRepository {

    val recipes = listOf(

        Recipe(
            id = 1,
            title = "Ensalada de garbanzos y huevo",
            image = R.drawable.recipe_placeholder,

            ingredients = listOf(
                "garbanzos",
                "huevo",
                "lechuga",
                "tomate"
            ),

            time = "15 minutos",
            difficulty = "fácil",
            servings = "2 personas",

            description =
                "Mezclar ingredientes y servir."
        ),

        Recipe(
            id = 2,
            title = "PokeBowl mediterráneo",
            image = R.drawable.recipe_placeholder,

            ingredients = listOf(
                "arroz",
                "aguacate",
                "pepino",
                "tomate"
            ),

            time = "20 minutos",
            difficulty = "fácil",
            servings = "2 personas",

            description =
                "Preparar ingredientes y montar bowl."
        ),

        Recipe(
            id = 3,
            title = "Desayuno bajo en hidratos",
            image = R.drawable.recipe_placeholder,

            ingredients = listOf(
                "huevo",
                "aguacate",
                "queso"
            ),

            time = "10 minutos",
            difficulty = "fácil",
            servings = "1 persona",

            description =
                "Preparar y servir."
        ),

        Recipe(
            id = 4,
            title = "Ensalada variada con huevo duro",
            image = R.drawable.recipe_placeholder,

            ingredients = listOf(
                "huevo",
                "lechuga",
                "cebolla",
                "zanahoria"
            ),

            time = "15 minutos",
            difficulty = "fácil",
            servings = "2 personas",

            description =
                "Cortar ingredientes y mezclar."
        ),
        Recipe(
            id = 5,
            title = "Pasta con atún y tomate",
            image = R.drawable.recipe_placeholder,

            ingredients = listOf(
                "pasta",
                "atún",
                "tomate",
                "queso"
            ),

            time = "20 minutos",
            difficulty = "fácil",
            servings = "2 personas",

            description =
                "Cocer pasta, añadir atún y tomate."
        ),

        Recipe(
            id = 6,
            title = "Tortilla de patatas ligera",
            image = R.drawable.recipe_placeholder,
            isSaved = true,

            ingredients = listOf(
                "huevo",
                "patata",
                "cebolla"
            ),

            time = "25 minutos",
            difficulty = "media",
            servings = "2 personas",

            description =
                "Preparar tortilla con menos aceite."
        ),

        Recipe(
            id = 7,
            title = "Ensalada de pollo y aguacate",
            image = R.drawable.recipe_placeholder,
            isSaved = true,

            ingredients = listOf(
                "pollo",
                "aguacate",
                "lechuga",
                "tomate"
            ),

            time = "15 minutos",
            difficulty = "fácil",
            servings = "2 personas",

            description =
                "Cortar ingredientes y mezclar."
        ),

        Recipe(
            id = 8,
            title = "Arroz con verduras",
            image = R.drawable.recipe_placeholder,

            ingredients = listOf(
                "arroz",
                "zanahoria",
                "cebolla",
                "pimiento"
            ),

            time = "30 minutos",
            difficulty = "media",
            servings = "3 personas",

            description =
                "Cocer arroz junto a las verduras."
        ),

        Recipe(
            id = 9,
            title = "Wrap de pollo y queso",
            image = R.drawable.recipe_placeholder,
            isSaved = true,

            ingredients = listOf(
                "pollo",
                "queso",
                "lechuga",
                "tomate"
            ),

            time = "15 minutos",
            difficulty = "fácil",
            servings = "1 persona",

            description =
                "Montar ingredientes y enrollar."
        ),

        Recipe(
            id = 10,
            title = "Yogur con frutas y avena",
            image = R.drawable.recipe_placeholder,

            ingredients = listOf(
                "yogur",
                "plátano",
                "fresas",
                "avena"
            ),

            time = "5 minutos",
            difficulty = "fácil",
            servings = "1 persona",

            description =
                "Mezclar ingredientes y servir."
        )
    )
}