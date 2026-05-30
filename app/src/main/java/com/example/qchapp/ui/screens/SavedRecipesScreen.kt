package com.example.qchapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.qchapp.ui.components.BottomBar
import com.example.qchapp.ui.components.QCHButton
import com.example.qchapp.ui.components.RecipePreview
import com.example.qchapp.ui.components.TopBar
import com.example.qchapp.ui.theme.*
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.runtime.*
import com.example.qchapp.data.FavoriteRecipe
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.example.qchapp.data.FavoriteRepository

@Composable
fun SavedRecipesScreen(
    onBackClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onFavoritesClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onRecipeClick: (Int) -> Unit = {},
    onLoginClick: () -> Unit = {}
) {
    val user = FirebaseAuth.getInstance().currentUser
    val isAnonymous = user == null || user.isAnonymous

    val context = LocalContext.current

    var recipes by remember {
        mutableStateOf<List<FavoriteRecipe>>(emptyList())
    }

    LaunchedEffect(Unit) {

        FavoriteRepository.getFavorites(

            onSuccess = { favorites ->
                recipes = favorites
            },

            onError = {
                recipes = emptyList()
            }
        )
    }

    Scaffold(
        bottomBar = {
            BottomBar(
                selectedItem = "favorites",
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

            Spacer(modifier = Modifier.height(36.dp))

            TopBar(
                onBackClick = onBackClick
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Recetas guardadas",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (isAnonymous) {

                Spacer(modifier = Modifier.height(60.dp))

                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Favoritos",
                    tint = QCHGreen,
                    modifier = Modifier.size(90.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Inicia sesión para guardar recetas",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Tus recetas favoritas aparecerán aquí cuando accedas con tu cuenta.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                QCHButton(
                    text = "Iniciar sesión",
                    color = QCHGreen,
                    onClick = onLoginClick,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(Dimens.ButtonHeight)
                )

            } else {

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {

                    items(
                        items = recipes,
                        key = { recipe ->
                            recipe.id
                        }
                    ) { recipe ->

                        RecipePreview(
                            title = recipe.title,
                            time = "${recipe.readyInMinutes} minutos",
                            difficulty = "",
                            imageUrl = recipe.image,
                            isSaved = true,
                            showDelete = true,
                            onClick = { onRecipeClick(recipe.id)},
                            onDeleteClick = {

                                recipes = recipes.filter {
                                    it.id != recipe.id
                                }

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

                                        recipes = recipes + recipe

                                        Toast.makeText(
                                            context,
                                            "No se pudo eliminar la receta",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SavedRecipesScreenPreview() {
    SavedRecipesScreen()
}