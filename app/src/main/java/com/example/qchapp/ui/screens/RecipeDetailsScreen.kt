package com.example.qchapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.HourglassBottom
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.qchapp.R
import com.example.qchapp.data.remote.ApiRecipeDetail
import com.example.qchapp.data.remote.TestRepository
import com.example.qchapp.ui.components.BottomBar
import com.example.qchapp.ui.components.RecipeInfo
import com.example.qchapp.ui.theme.Dimens
import com.example.qchapp.ui.theme.QCHGreen
import kotlinx.coroutines.launch
import com.example.qchapp.data.FavoriteRecipe
import com.example.qchapp.data.FavoriteRepository
import com.example.qchapp.ui.components.TopBar
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.IconButton


@Composable
fun RecipeDetailsScreen(
    recipeId: Int = 1,
    onBackClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onFavoritesClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var recipe by remember {
        mutableStateOf<ApiRecipeDetail?>(null)
    }

    var loading by remember {
        mutableStateOf(true)
    }

    var isSaved by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(recipeId) {
        coroutineScope.launch {
            try {
                recipe = TestRepository.getRecipeInformation(recipeId)

                FavoriteRepository.isFavorite(recipeId) { saved ->
                    isSaved = saved
                }

            } catch (e: Exception) {
                Toast.makeText(
                    context,
                    "Error al cargar receta: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            } finally {
                loading = false
            }
        }
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

            Spacer(modifier = Modifier.height(36.dp))

            TopBar(
                onBackClick = onBackClick
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {

                IconButton(
                    onClick = {

                        val user = FirebaseAuth.getInstance().currentUser

                        if (user == null || user.isAnonymous) {
                            Toast.makeText(
                                context,
                                "Debes iniciar sesión para guardar recetas",
                                Toast.LENGTH_LONG
                            ).show()
                            return@IconButton
                        }

                        val currentRecipe = recipe ?: return@IconButton

                        if (isSaved) {

                            isSaved = false

                            FavoriteRepository.deleteFavorite(
                                recipeId = currentRecipe.id,
                                onSuccess = {
                                    Toast.makeText(
                                        context,
                                        "Receta eliminada de favoritos",
                                        Toast.LENGTH_LONG
                                    ).show()
                                },
                                onError = {
                                    isSaved = true
                                    Toast.makeText(
                                        context,
                                        "No se pudo eliminar la receta",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            )

                        } else {

                            isSaved = true

                            val favoriteRecipe = FavoriteRecipe(
                                id = currentRecipe.id,
                                title = currentRecipe.title,
                                image = currentRecipe.image ?: "",
                                readyInMinutes = currentRecipe.readyInMinutes ?: 0,
                                servings = currentRecipe.servings ?: 1
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
                                    isSaved = false
                                    Toast.makeText(
                                        context,
                                        "No se pudo guardar la receta",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            )
                        }
                    }
                ) {
                    Icon(
                        imageVector =
                            if (isSaved)
                                Icons.Default.Bookmark
                            else
                                Icons.Default.BookmarkBorder,
                        contentDescription = "Guardar",
                        tint = QCHGreen,
                        modifier = Modifier.size(38.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(
                        rememberScrollState()
                    )
            ) {

                if (loading) {

                    CircularProgressIndicator(
                        color = QCHGreen,
                        modifier = Modifier.align(
                            Alignment.CenterHorizontally
                        )
                    )

                } else {

                    val currentRecipe = recipe

                    if (currentRecipe == null) {

                        Text(
                            text = "No se pudo cargar la receta",
                            style = MaterialTheme.typography.bodyLarge
                        )

                    } else {

                        AsyncImage(
                            model = currentRecipe.image,
                            contentDescription = currentRecipe.title,
                            placeholder = painterResource(
                                id = R.drawable.recipe_placeholder
                            ),
                            error = painterResource(
                                id = R.drawable.recipe_placeholder
                            ),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp)
                        )

                        Spacer(
                            modifier = Modifier.height(18.dp)
                        )

                        Text(
                            text = currentRecipe.title,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                        val minutes =
                            currentRecipe.readyInMinutes ?: 0

                        val difficulty =
                            when {
                                currentRecipe.readyInMinutes == null -> "fácil"
                                minutes <= 20 -> "fácil"
                                minutes <= 45 -> "media"
                                else -> "difícil"
                            }

                        Row(
                            horizontalArrangement =
                                Arrangement.spacedBy(20.dp)
                        ) {

                            RecipeInfo(
                                icon = Icons.Default.HourglassBottom,
                                text = "$minutes minutos"
                            )

                            RecipeInfo(
                                icon = Icons.AutoMirrored.Filled.Help,
                                text = difficulty
                            )

                            RecipeInfo(
                                icon = Icons.Default.Groups,
                                text = "${currentRecipe.servings ?: 1} personas"
                            )
                        }

                        Spacer(
                            modifier = Modifier.height(20.dp)
                        )

                        Text(
                            text = "Ingredientes",
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        Text(
                            text = currentRecipe.extendedIngredients
                                ?.mapNotNull { it.original }
                                ?.joinToString("\n") { "• $it" }
                                ?: "No hay ingredientes disponibles"
                        )

                        Spacer(
                            modifier = Modifier.height(20.dp)
                        )

                        Text(
                            text = "Preparación",
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        Text(
                            text = currentRecipe.instructions
                                ?.replace(
                                    Regex("<.*?>"),
                                    ""
                                )
                                ?.ifBlank {
                                    "No hay instrucciones disponibles"
                                }
                                ?: "No hay instrucciones disponibles"
                        )

                        Spacer(
                            modifier = Modifier.height(80.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeDetailsPreview() {
    RecipeDetailsScreen()
}