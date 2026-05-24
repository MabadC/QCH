package com.example.qchapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.HourglassBottom
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.qchapp.R
import com.example.qchapp.data.RecipeRepository
import com.example.qchapp.ui.components.BottomBar
import com.example.qchapp.ui.components.RecipeInfo
import com.example.qchapp.ui.theme.*

@Composable
fun RecipeDetailsScreen(
    recipeId: Int = 1,
    onBackClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onFavoritesClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onSaveClick: () -> Unit = {}
) {

    val recipe = RecipeRepository.recipes.find {
        it.id == recipeId
    } ?: RecipeRepository.recipes.first()

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
                .verticalScroll(rememberScrollState())
        ) {

            Spacer(
                modifier = Modifier.height(36.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(
                        id = R.drawable.flecha
                    ),
                    contentDescription = "Volver",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            onBackClick()
                        }
                )

                Image(
                    painter = painterResource(
                        id = R.drawable.qch_logo
                    ),
                    contentDescription = "Logo",
                    modifier = Modifier.size(70.dp)
                )

                Icon(
                    imageVector =
                        if (recipe.isSaved)
                            Icons.Default.Bookmark
                        else
                            Icons.Default.BookmarkBorder,
                    contentDescription = "Guardar",
                    tint = QCHGreen,
                    modifier = Modifier
                        .size(38.dp)
                        .clickable {
                            onSaveClick()
                        }
                )
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Image(
                painter = painterResource(
                    id = recipe.image
                ),
                contentDescription = recipe.title,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            Text(
                text = recipe.title,
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
                    text = recipe.time
                )

                RecipeInfo(
                    icon = Icons.AutoMirrored.Filled.Help,
                    text = recipe.difficulty
                )

                RecipeInfo(
                    icon = Icons.Default.Groups,
                    text = recipe.servings
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
                text = recipe.ingredients.joinToString(
                    separator = "\n"
                ) {
                    "• $it"
                }
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
                text = recipe.description
            )

            Spacer(
                modifier = Modifier.height(80.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeDetailsPreview() {
    RecipeDetailsScreen()
}