package com.example.qchapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import com.example.qchapp.ui.components.IngredientField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.qchapp.R
import com.example.qchapp.ui.components.QCHButton
import com.example.qchapp.ui.components.QCHTextField
import com.example.qchapp.ui.theme.*

@Composable
fun SearchScreen() {

    var ingredient1 by remember { mutableStateOf("huevo") }
    var ingredient2 by remember { mutableStateOf("") }
    var restrictedIngredient by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.ScreenPadding),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(
            modifier = Modifier.height(50.dp)
        )

        // Logo

        Image(
            painter = painterResource(id = R.drawable.qch_logo),
            contentDescription = "QCH Logo",
            modifier = Modifier.size(95.dp)
        )

        Spacer(
            modifier = Modifier.height(35.dp)
        )

        // Título

        Text(
            text = "¿Qué hay en tu despensa?",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        // Ingredientes

        var ingredients by remember {
            mutableStateOf(listOf(""))
        }

        ingredients.forEachIndexed { index, ingredient ->

            IngredientField(
                value = ingredient,
                onValueChange = { newValue ->
                    ingredients = ingredients.toMutableList().also {
                        it[index] = newValue
                    }
                },
                onAddClick = {
                    ingredients = ingredients + ""
                }
            )

            Spacer(modifier = Modifier.height(10.dp))
        }

        // Restricción

        var restrictedIngredients by remember {
            mutableStateOf(listOf(""))
        }

        restrictedIngredients.forEachIndexed { index, ingredient ->

            IngredientField(
                value = ingredient,
                onValueChange = { newValue ->
                    restrictedIngredients = restrictedIngredients.toMutableList().also {
                        it[index] = newValue
                    }
                },
                onAddClick = {
                    restrictedIngredients = restrictedIngredients + ""
                }
            )

            Spacer(modifier = Modifier.height(10.dp))
        }

        // Botón búsqueda

        QCHButton(
            text = "Buscar recetas",
            color = QCHGreen,
            onClick = {},

            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(Dimens.ButtonHeight)
        )

        Spacer(
            modifier = Modifier.height(90.dp)
        )
    }
}

@Composable
fun IngredientField(
    value: String,
    onValueChange: (String) -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        QCHTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = "ingrediente",
            modifier = Modifier.weight(1f)
        )

        Spacer(
            modifier = Modifier.width(8.dp)
        )

        FilledIconButton(
            onClick = {},
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = QCHGreen
            )
        ) {

            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Añadir ingrediente",
                tint = Color.White
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    SearchScreen()
}