package com.example.qchapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.qchapp.ui.components.*
import com.example.qchapp.ui.theme.*
import com.example.qchapp.data.local.LocalRecipeSearchState
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import com.example.qchapp.ui.components.RecipePreview
import android.widget.Toast
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
@Composable
fun LocalResultsScreen(
    onBackClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onFavoritesClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onRecipeClick: (Int) -> Unit = {}
) {

    val context = LocalContext.current

    val localRecipes = LocalRecipeSearchState.recipes

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

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = QCHOrange.copy(alpha = 0.12f),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(14.dp)
            ) {
                Text(
                    text = "⚠️ Modo reducido activado",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = QCHOrange
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = "Solo se muestran recetas locales temporalmente.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray
                )

                Text(
                    text = "Algunas funciones no están disponibles.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray
                )
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            LazyColumn {

                items(localRecipes) { recipe ->

                    RecipePreview(
                        title = recipe.title,
                        time = "${recipe.readyInMinutes} minutos",
                        difficulty = recipe.difficulty,
                        imageUrl = recipe.image,
                        showSaveIcon = false, // desactivamos la funcion guardado para el modo reducido
                        isSaved = false,

                        onClick = {
                            onRecipeClick(recipe.id)
                        },

                        onSaveClick = {
                            Toast.makeText(
                                context,
                                "Los favoritos no están disponibles en modo reducido",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    )
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun LocalResultsScreenPreview() {
    ResultsScreen()
}