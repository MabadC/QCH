package com.example.qchapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.qchapp.R
import com.example.qchapp.data.RecipeRepository
import com.example.qchapp.ui.components.BottomBar
import com.example.qchapp.ui.components.RecipePreview
import com.example.qchapp.ui.theme.Dimens
import com.example.qchapp.data.RecipeSearchState

@Composable
fun ResultsScreen(
    onBackClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onFavoritesClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onRecipeClick: (Int) -> Unit = {}
) {

    val searchedIngredients = RecipeSearchState.ingredients
    val restrictedIngredients = RecipeSearchState.restrictedIngredients

    val recipes = RecipeRepository.recipes.filter { recipe ->

        val recipeIngredients = recipe.ingredients.map {
            it.lowercase()
        }

        val containsSearchedIngredient =
            searchedIngredients.isEmpty() ||
                    searchedIngredients.any { ingredient ->
                        recipeIngredients.any { it.contains(ingredient) }
                    }

        val containsRestrictedIngredient =
            restrictedIngredients.any { restricted ->
                recipeIngredients.any { it.contains(restricted) }
            }

        containsSearchedIngredient && !containsRestrictedIngredient
    }

    Scaffold(
        bottomBar = {
            BottomBar(
                selectedItem = "search",
                onSearchClick = onSearchClick,
                onFavoritesClick = onFavoritesClick,
                onProfileClick = onProfileClick
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = Dimens.ScreenPadding)
        ) {

            Spacer(
                modifier = Modifier.height(36.dp)
            )

            Image(
                painter = painterResource(
                    id = R.drawable.flecha
                ),
                contentDescription = "Volver",
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        onBackClick()
                    }
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Image(
                painter = painterResource(
                    id = R.drawable.qch_logo
                ),
                contentDescription = "QCH Logo",
                modifier = Modifier
                    .size(88.dp)
                    .align(
                        Alignment.CenterHorizontally
                    )
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = "Hemos encontrado ${recipes.size} recetas",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(recipes) { recipe ->

                    RecipePreview(
                        title = recipe.title,
                        time = recipe.time,
                        difficulty = recipe.difficulty,
                        image = recipe.image,
                        isSaved = recipe.isSaved,
                        onClick = {
                            onRecipeClick(recipe.id)
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResultsScreenPreview() {
    ResultsScreen()
}