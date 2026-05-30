package com.example.qchapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qchapp.data.remote.ApiRecipeSearchState
import com.example.qchapp.data.remote.TestRepository
import com.example.qchapp.ui.components.BottomBar
import com.example.qchapp.ui.components.RecipePreview
import com.example.qchapp.ui.components.TopBar
import com.example.qchapp.ui.theme.Dimens
import com.example.qchapp.ui.theme.QCHGreen
import kotlinx.coroutines.launch
import androidx.compose.foundation.Image
import com.example.qchapp.R
import com.example.qchapp.ui.components.QCHButton
import com.example.qchapp.data.FavoriteRecipe
import com.example.qchapp.data.FavoriteRepository
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.runtime.LaunchedEffect

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

    var savedRecipeIds by remember {
        mutableStateOf<Set<Int>>(emptySet())
    }

    LaunchedEffect(Unit) {

        FavoriteRepository.getFavorites(

            onSuccess = { favorites ->

                savedRecipeIds =
                    favorites.map { it.id }.toSet()
            },

            onError = {
                savedRecipeIds = emptySet()
            }
        )
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

            if (recipes.isEmpty()) {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.sad),
                            contentDescription = "Sin resultados",
                            modifier = Modifier.size(180.dp)
                        )

                        Spacer(
                            modifier = Modifier.height(24.dp)
                        )

                        Text(
                            text = "Lo siento,",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.DarkGray
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        Text(
                            text = "No hemos encontrado ninguna\ncoincidencia con tu búsqueda",
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            color = Color.Gray
                        )

                        Spacer(
                            modifier = Modifier.height(24.dp)
                        )

                        QCHButton(
                            text = "Volver a buscar",
                            color = QCHGreen,
                            onClick = onBackClick,
                            modifier = Modifier.fillMaxWidth(0.7f)
                        )
                    }
                }

            } else {

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    items(recipes) { recipe ->

                        val isSaved = savedRecipeIds.contains(recipe.id)

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
                            isSaved = isSaved,
                            onClick = {
                                onRecipeClick(recipe.id)
                            },
                            onSaveClick = {

                                val user = FirebaseAuth.getInstance().currentUser

                                if (user == null || user.isAnonymous) {
                                    Toast.makeText(
                                        context,
                                        "Debes iniciar sesión para guardar recetas",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    return@RecipePreview
                                }

                                if (isSaved) {

                                    savedRecipeIds = savedRecipeIds - recipe.id

                                    FavoriteRepository.deleteFavorite(
                                        recipeId = recipe.id,
                                        onSuccess = {
                                            Toast.makeText(
                                                context,
                                                "Receta eliminada de favoritos",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        },
                                        onError = {
                                            savedRecipeIds = savedRecipeIds + recipe.id

                                            Toast.makeText(
                                                context,
                                                "No se pudo eliminar la receta",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    )

                                } else {

                                    savedRecipeIds = savedRecipeIds + recipe.id

                                    val favoriteRecipe = FavoriteRecipe(
                                        id = recipe.id,
                                        title = recipe.title,
                                        image = recipe.image ?: "",
                                        readyInMinutes = recipe.readyInMinutes ?: 0,
                                        servings = 1
                                    )

                                    FavoriteRepository.saveFavorite(
                                        recipe = favoriteRecipe,
                                        onSuccess = {
                                            Toast.makeText(
                                                context,
                                                "Receta guardada en favoritos",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        },
                                        onError = {
                                            savedRecipeIds = savedRecipeIds - recipe.id

                                            Toast.makeText(
                                                context,
                                                "No se pudo guardar la receta",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    )
                                }
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
}

@Preview(showBackground = true)
@Composable
fun ResultsScreenPreview() {
    ResultsScreen()
}