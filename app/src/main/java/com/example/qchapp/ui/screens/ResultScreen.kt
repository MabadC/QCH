package com.example.qchapp.ui.screens

import androidx.compose.foundation.Image
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
import com.example.qchapp.ui.components.BottomBar
import com.example.qchapp.ui.components.RecipePreview
import com.example.qchapp.ui.theme.Dimens

@Composable
fun ResultsScreen() {

    val recipes = listOf(
        RecipePreviewData(
            "Desayuno bajo en hidratos",
            "10 minutos",
            "fácil",
            false
        ),

        RecipePreviewData(
            "Ensalada de garbanzos y huevo",
            "15 minutos",
            "fácil",
            false
        ),

        RecipePreviewData(
            "PokeBowl mediterráneo",
            "15 minutos",
            "fácil",
            true
        ),

        RecipePreviewData(
            "Ensalada variada con huevo duro",
            "15 minutos",
            "fácil",
            false
        )
    )

    Scaffold(

        bottomBar = {
            BottomBar(
                selectedItem = "search"
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

            // Flecha volver

            Image(
                painter = painterResource(
                    id = R.drawable.flecha
                ),
                contentDescription = "Volver",

                modifier = Modifier.size(32.dp)
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            // Logo

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

            // Título

            Text(
                text = "Hemos encontrado ${recipes.size} recetas",

                style = MaterialTheme.typography.titleLarge,

                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            // Lista resultados

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(recipes) { recipe ->

                    RecipePreview(

                        title = recipe.title,
                        time = recipe.time,
                        difficulty = recipe.difficulty,
                        isSaved = recipe.isSaved

                    )

                }
            }
        }
    }
}

data class RecipePreviewData(
    val title: String,
    val time: String,
    val difficulty: String,
    val isSaved: Boolean
)

@Preview(showBackground = true)
@Composable
fun ResultsScreenPreview() {
    ResultsScreen()
}