package com.example.qchapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.qchapp.R
import com.example.qchapp.data.remote.TestRepository
import com.example.qchapp.ui.components.BottomBar
import com.example.qchapp.ui.components.IngredientField
import com.example.qchapp.ui.components.QCHButton
import com.example.qchapp.ui.theme.Dimens
import com.example.qchapp.ui.theme.QCHGreen
import kotlinx.coroutines.launch
import com.example.qchapp.data.remote.ApiRecipeSearchState
import com.example.qchapp.data.remote.TranslationRepository
import com.example.qchapp.data.local.LocalRecipeRepository
import com.example.qchapp.data.local.LocalRecipeSearchState
import com.example.qchapp.validation.Validations
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll


@Composable
fun SearchScreen(
    onSearchClick: () -> Unit = {},
    onFavoritesClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onSearchRecipesClick: () -> Unit = {},
    onLocalResultsClick: () -> Unit = {}
) {

    var ingredients by remember {
        mutableStateOf(listOf(""))
    }

    var restrictedIngredients by remember {
        mutableStateOf(listOf(""))
    }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

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
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(
                modifier = Modifier.height(50.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.qch_logo),
                contentDescription = "QCH Logo",
                modifier = Modifier.size(95.dp)
            )

            Spacer(
                modifier = Modifier.height(35.dp)
            )

            Text(
                text = "¿Qué hay en tu despensa?",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            ingredients.forEachIndexed { index, ingredient ->

                IngredientField(
                    value = ingredient,

                    onValueChange = { newValue ->
                        ingredients = ingredients.toMutableList().also {
                            it[index] = newValue
                        }
                    },

                    isAddButton = index == 0,

                    onAddClick = {
                        ingredients = ingredients + ""
                    },

                    onRemoveClick = {
                        ingredients = ingredients.toMutableList().also {
                            it.removeAt(index)
                        }
                    }
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )
            }

            Spacer(
                modifier = Modifier.height(15.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom
            ) {

                Text(
                    text = "Restringir ingredientes",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = " (opcional)",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            restrictedIngredients.forEachIndexed { index, ingredient ->

                IngredientField(
                    value = ingredient,

                    onValueChange = { newValue ->
                        restrictedIngredients = restrictedIngredients.toMutableList().also {
                            it[index] = newValue
                        }
                    },

                    isAddButton = index == 0,

                    onAddClick = {
                        restrictedIngredients = restrictedIngredients + ""
                    },

                    onRemoveClick = {
                        restrictedIngredients = restrictedIngredients.toMutableList().also {
                            it.removeAt(index)
                        }
                    }
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )
            }

            Spacer(
                modifier = Modifier.height(40.dp)
            )

            QCHButton(
                text = "Buscar recetas",
                color = QCHGreen,

                onClick = {

                    coroutineScope.launch {

                        try {

                            val validations = Validations()

                            // Spoonacular trabaja en inglés, por lo que los ingredientes
                            // introducidos por el usuario se traducen antes de la búsqueda.
                            val selectedIngredients =
                                validations.cleanIngredients(ingredients)

                            val translatedIngredients =
                                selectedIngredients.map { ingredient ->

                                    TranslationRepository
                                        .translateToEnglish(ingredient)
                                }

                            val restricted = restrictedIngredients
                                .map { it.trim() }
                                .filter { it.isNotBlank() }

                            val translatedRestrictedIngredients =
                                restricted.map { ingredient ->
                                    TranslationRepository.translateToEnglish(ingredient)
                                }

                            if (
                                selectedIngredients.isEmpty() &&
                                restricted.isEmpty()
                            ) {

                                Toast.makeText(
                                    context,
                                    "No has elegido ni restringido ningún ingrediente",
                                    Toast.LENGTH_LONG
                                ).show()

                                return@launch
                            }

                            if (selectedIngredients.isEmpty()) {
                                Toast.makeText(
                                    context,
                                    "Elige al menos un ingrediente para buscar recetas",
                                    Toast.LENGTH_LONG
                                ).show()

                                return@launch
                            }

                            val response = TestRepository.searchRecipes(
                                ingredients = translatedIngredients,
                                restrictedIngredients = translatedRestrictedIngredients,
                                number = ApiRecipeSearchState.PAGE_SIZE,
                                offset = 0
                            )

                            ApiRecipeSearchState.recipes = response.results
                            ApiRecipeSearchState.ingredients = translatedIngredients
                            ApiRecipeSearchState.restrictedIngredients = translatedRestrictedIngredients
                            ApiRecipeSearchState.offset = ApiRecipeSearchState.PAGE_SIZE

                            onSearchRecipesClick()

                        } catch (_: Exception) {

                            // Si la API no está disponible, se realiza la búsqueda
                            // sobre el catálogo local y se activa el modo reducido.

                            val searchIngredients =
                                ingredients
                                    .map { it.trim() }
                                    .filter { it.isNotBlank() }

                            val searchRestrictedIngredients =
                                restrictedIngredients
                                    .map { it.trim() }
                                    .filter { it.isNotBlank() }

                            val localResults =
                                LocalRecipeRepository.searchRecipesByIngredients(
                                    context,
                                    searchIngredients,
                                    searchRestrictedIngredients
                                )

                            LocalRecipeSearchState.recipes = localResults
                            LocalRecipeSearchState.isLocalMode = true
                            LocalRecipeSearchState.searchQuery =
                                searchIngredients.joinToString(", ")

                            Toast.makeText(
                                context,
                                "Modo reducido activado",
                                Toast.LENGTH_LONG
                            ).show()

                            onLocalResultsClick()
                        }
                    }
                },

                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(Dimens.ButtonHeight)
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    SearchScreen()
}