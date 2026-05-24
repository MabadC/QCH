package com.example.qchapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
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
import com.example.qchapp.ui.theme.*

@Composable
fun SavedRecipesScreen() {

    val recipes = listOf(

        RecipePreviewData(
            "Ensalada de garbanzos y huevo",
            "15 minutos",
            "fácil",
            true
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
            true
        )

    )

    Scaffold(

        bottomBar = {
            BottomBar(
                selectedItem = "favorites"
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
                modifier = Modifier.size(32.dp)
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Image(
                painter = painterResource(
                    id = R.drawable.qch_logo
                ),

                contentDescription = "Logo",

                modifier = Modifier
                    .size(88.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Text(
                text = "Recetas guardadas",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {

                items(recipes) { recipe ->

                    RecipePreview(
                        title = recipe.title,
                        time = recipe.time,
                        difficulty = recipe.difficulty,
                        isSaved = true,
                        showDelete = true
                    )

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