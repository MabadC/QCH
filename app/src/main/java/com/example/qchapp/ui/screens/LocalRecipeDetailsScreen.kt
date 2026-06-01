package com.example.qchapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import com.example.qchapp.data.local.LocalRecipeEntity
import com.example.qchapp.data.local.LocalRecipeRepository
import com.example.qchapp.ui.components.TopBar
import com.example.qchapp.ui.theme.Dimens
import com.example.qchapp.ui.theme.QCHOrange

@Composable
fun LocalRecipeDetailsScreen(
    recipeId: Int,
    onBackClick: () -> Unit = {}
) {

    val context = LocalContext.current

    var recipe by remember {
        mutableStateOf<LocalRecipeEntity?>(null)
    }

    LaunchedEffect(recipeId) {

        recipe =
            LocalRecipeRepository
                .getRecipeById(
                    context,
                    recipeId
                )
    }

    val currentRecipe = recipe ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.ScreenPadding)
            .verticalScroll(rememberScrollState())
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

            Text(
                text = "Se muestra receta local.",
                color = Color.DarkGray
            )
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = currentRecipe.title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text("Tiempo: ${currentRecipe.readyInMinutes} minutos")
        Text("Dificultad: ${currentRecipe.difficulty}")
        Text("Raciones: ${currentRecipe.servings}")
        Text("Categoría: ${currentRecipe.category}")

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = "Ingredientes",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(currentRecipe.ingredients)

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = "Preparación",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(currentRecipe.instructions)

        Spacer(
            modifier = Modifier.height(80.dp)
        )
    }
}