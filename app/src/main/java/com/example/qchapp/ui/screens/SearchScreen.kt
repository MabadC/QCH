package com.example.qchapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.qchapp.R
import com.example.qchapp.ui.components.BottomBar
import com.example.qchapp.ui.components.IngredientField
import com.example.qchapp.ui.components.QCHButton
import com.example.qchapp.ui.theme.*
import com.example.qchapp.data.RecipeSearchState

@Composable
fun SearchScreen(
    onSearchClick: () -> Unit = {},
    onFavoritesClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onSearchRecipesClick: () -> Unit = {}
) {

    var ingredients by remember {
        mutableStateOf(listOf(""))
    }

    var restrictedIngredients by remember {
        mutableStateOf(listOf(""))
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
                .padding(horizontal = Dimens.ScreenPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(
                modifier = Modifier.height(50.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.qch_logo),
                contentDescription = "QCH Logo",
                modifier = Modifier.size(95.dp)
            )

            Spacer(
                modifier = Modifier.height(35.dp)
            )

            Text(
                text = "¿Qué hay en tu despensa?",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            ingredients.forEachIndexed { index, ingredient ->

                IngredientField(
                    value = ingredient,

                    onValueChange = { newValue ->
                        ingredients = ingredients.toMutableList().also {
                            it[index] = newValue
                        }
                    },

                    isAddButton = index == 0,

                    onAddClick = {
                        ingredients = ingredients + ""
                    },

                    onRemoveClick = {
                        ingredients = ingredients.toMutableList().also {
                            it.removeAt(index)
                        }
                    }
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )
            }

            Spacer(
                modifier = Modifier.height(15.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom
            ) {

                Text(
                    text = "Restringir ingredientes",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = " (opcional)",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            restrictedIngredients.forEachIndexed { index, ingredient ->

                IngredientField(
                    value = ingredient,

                    onValueChange = { newValue ->
                        restrictedIngredients = restrictedIngredients.toMutableList().also {
                            it[index] = newValue
                        }
                    },

                    isAddButton = index == 0,

                    onAddClick = {
                        restrictedIngredients = restrictedIngredients + ""
                    },

                    onRemoveClick = {
                        restrictedIngredients = restrictedIngredients.toMutableList().also {
                            it.removeAt(index)
                        }
                    }
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )
            }

            Spacer(
                modifier = Modifier.height(40.dp)
            )

            QCHButton(
                text = "Buscar recetas",
                color = QCHGreen,
                onClick = {
                    RecipeSearchState.ingredients = ingredients
                        .map { it.trim().lowercase() }
                        .filter { it.isNotBlank() }

                    RecipeSearchState.restrictedIngredients = restrictedIngredients
                        .map { it.trim().lowercase() }
                        .filter { it.isNotBlank() }

                    onSearchRecipesClick()
                },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(Dimens.ButtonHeight)
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    SearchScreen()
}