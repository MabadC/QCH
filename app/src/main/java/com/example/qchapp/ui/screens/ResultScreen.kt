package com.example.qchapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.qchapp.data.remote.ApiRecipeSearchState
import com.example.qchapp.ui.components.BottomBar
import com.example.qchapp.ui.components.RecipePreview
import com.example.qchapp.ui.theme.Dimens
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import com.example.qchapp.data.remote.TestRepository
import kotlinx.coroutines.launch
import com.example.qchapp.ui.theme.QCHGreen
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.ui.graphics.Color
import com.example.qchapp.ui.components.TopBar

@Composable
fun ResultsScreen(
    onBackClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onFavoritesClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onRecipeClick: (Int) -> Unit = {}
) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var recipes by remember {
        mutableStateOf(ApiRecipeSearchState.recipes)
    }

    var loadingMore by remember {
        mutableStateOf(false)
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

            TopBar(
                onBackClick = onBackClick
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(recipes) { recipe ->

                    val minutes = recipe.readyInMinutes ?: 0

                    val difficulty =
                        when {
                            recipe.readyInMinutes == null -> "fácil"
                            minutes <= 20 -> "fácil"
                            minutes <= 45 -> "media"
                            else -> "difícil"
                        }

                    RecipePreview(
                        title = recipe.title,
                        time = "$minutes minutos",
                        difficulty = difficulty,
                        imageUrl = recipe.image,
                        isSaved = false,
                        onClick = {
                            onRecipeClick(recipe.id)
                        }
                    )
                }

                item {

                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {

                        FilledIconButton(
                            onClick = {
                                coroutineScope.launch {

                                    try {

                                        loadingMore = true

                                        val response = TestRepository.searchRecipes(
                                            ingredients = ApiRecipeSearchState.ingredients,
                                            restrictedIngredients = ApiRecipeSearchState.restrictedIngredients,
                                            number = ApiRecipeSearchState.PAGE_SIZE,
                                            offset = ApiRecipeSearchState.offset
                                        )

                                        recipes = recipes + response.results

                                        ApiRecipeSearchState.recipes = recipes
                                        ApiRecipeSearchState.offset += ApiRecipeSearchState.PAGE_SIZE

                                    } catch (_: Exception) {

                                        Toast.makeText(
                                            context,
                                            "Error al cargar más recetas",
                                            Toast.LENGTH_LONG
                                        ).show()

                                    } finally {

                                        loadingMore = false
                                    }
                                }
                            },
                            enabled = !loadingMore,
                            colors = IconButtonDefaults.filledIconButtonColors(
                                containerColor = QCHGreen
                            ),
                            modifier = Modifier.size(52.dp)
                        ) {

                            if (loadingMore) {

                                CircularProgressIndicator(
                                    color = Color.White,
                                    modifier = Modifier.size(24.dp),
                                    strokeWidth = 2.dp
                                )

                            } else {

                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Cargar más recetas",
                                    tint = Color.White
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(80.dp))
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