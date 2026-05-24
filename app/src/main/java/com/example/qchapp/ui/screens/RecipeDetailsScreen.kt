package com.example.qchapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.qchapp.R
import com.example.qchapp.ui.components.BottomBar
import com.example.qchapp.ui.theme.*
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.HourglassBottom
import com.example.qchapp.ui.components.RecipeInfo

@Composable
fun RecipeDetailsScreen() {

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
                .verticalScroll(rememberScrollState())
        ) {

            Spacer(
                modifier = Modifier.height(36.dp)
            )

            // Cabecera

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Image(
                    painter = painterResource(
                        id = R.drawable.flecha
                    ),
                    contentDescription = "Volver",
                    modifier = Modifier.size(32.dp)
                )

                Image(
                    painter = painterResource(
                        id = R.drawable.qch_logo
                    ),
                    contentDescription = "Logo",
                    modifier = Modifier.size(70.dp)
                )

                Icon(
                    imageVector = Icons.Default.BookmarkBorder,
                    contentDescription = "Guardar",
                    tint = QCHGreen,
                    modifier = Modifier.size(38.dp)
                )
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            // Imagen receta

            Image(
                painter = painterResource(
                    id = R.drawable.recipe_placeholder
                ),
                contentDescription = "Imagen receta",

                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            Text(
                text = "Ensalada de lentejas con verduras",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                RecipeInfo(
                    icon = Icons.Default.HourglassBottom,
                    text = "15 minutos"
                )

                RecipeInfo(
                    icon = Icons.AutoMirrored.Filled.Help,
                    text = "fácil"
                )

                RecipeInfo(
                    icon = Icons.Default.Groups,
                    text = "2 personas"
                )
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Text(
                text = "Ingredientes",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = """
• 1 bote de lentejas cocidas
• 2 huevos
• 2 zanahorias
• 10 tomates cherry
• Aceite de oliva
• Sal
                """.trimIndent()
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Text(
                text = "Preparación",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text =
                    "Poner a hervir los huevos durante 8 minutos.\n\n" +
                            "Escurrir las lentejas y mezclar.\n\n" +
                            "Añadir verduras y servir."
            )

            Spacer(
                modifier = Modifier.height(30.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeDetailsPreview() {
    RecipeDetailsScreen()
}