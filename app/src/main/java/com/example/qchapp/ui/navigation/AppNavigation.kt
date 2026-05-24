package com.example.qchapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.qchapp.ui.screens.*

object Routes {

    const val WELCOME = "welcome"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val PASSWORD_RECOVERY = "password_recovery"

    const val SEARCH = "search"
    const val RESULTS = "results"
    const val RECIPE_DETAILS = "recipe_details/{recipeId}"

    const val SAVED_RECIPES = "saved_recipes"
    const val PROFILE = "profile"
    const val CHANGE_PASSWORD = "change_password"

    const val NETWORK_ERROR = "network_error"

    fun recipeDetails(recipeId: Int): String {
        return "recipe_details/$recipeId"
    }
}

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.WELCOME
    ) {

        composable(Routes.WELCOME) {
            WelcomeScreen(
                onLoginClick = {
                    navController.navigate(Routes.LOGIN)
                },
                onRegisterClick = {
                    navController.navigate(Routes.REGISTER)
                },
                onGuestClick = {
                    navController.navigate(Routes.SEARCH)
                }
            )
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(
                        Routes.SEARCH
                    )
                }
            )
        }

        composable(Routes.REGISTER) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(
                        Routes.SEARCH
                    )
                }
            )
        }

        composable(Routes.PASSWORD_RECOVERY) {
            PasswordRecoveryScreen()
        }

        composable(Routes.SEARCH) {
            SearchScreen(
                onSearchClick = {
                    navController.navigate(Routes.SEARCH)
                },
                onFavoritesClick = {
                    navController.navigate(Routes.SAVED_RECIPES)
                },
                onProfileClick = {
                    navController.navigate(Routes.PROFILE)
                },
                onSearchRecipesClick = {
                    navController.navigate(Routes.RESULTS)
                }
            )
        }

        composable(Routes.RESULTS) {
            ResultsScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onSearchClick = {
                    navController.navigate(Routes.SEARCH)
                },
                onFavoritesClick = {
                    navController.navigate(Routes.SAVED_RECIPES)
                },
                onProfileClick = {
                    navController.navigate(Routes.PROFILE)
                },
                onRecipeClick = { recipeId ->
                    navController.navigate(
                        Routes.recipeDetails(recipeId)
                    )
                }
            )
        }

        composable(Routes.RECIPE_DETAILS) { backStackEntry ->

            val recipeId = backStackEntry.arguments
                ?.getString("recipeId")
                ?.toIntOrNull()
                ?: 1

            RecipeDetailsScreen(
                recipeId = recipeId,
                onBackClick = {
                    navController.popBackStack()
                },
                onSearchClick = {
                    navController.navigate(Routes.SEARCH)
                },
                onFavoritesClick = {
                    navController.navigate(Routes.SAVED_RECIPES)
                },
                onProfileClick = {
                    navController.navigate(Routes.PROFILE)
                }
            )
        }

        composable(Routes.SAVED_RECIPES) {
            SavedRecipesScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onSearchClick = {
                    navController.navigate(Routes.SEARCH)
                },
                onFavoritesClick = {
                    navController.navigate(Routes.SAVED_RECIPES)
                },
                onProfileClick = {
                    navController.navigate(Routes.PROFILE)
                },
                onRecipeClick = { recipeId ->
                    navController.navigate(
                        Routes.recipeDetails(recipeId)
                    )
                }
            )
        }

        composable(Routes.PROFILE) {
            ProfileScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onSearchClick = {
                    navController.navigate(Routes.SEARCH)
                },
                onFavoritesClick = {
                    navController.navigate(Routes.SAVED_RECIPES)
                },
                onProfileClick = {
                    navController.navigate(Routes.PROFILE)
                },
                onChangePasswordClick = {
                    navController.navigate(Routes.CHANGE_PASSWORD)
                },
                onLogoutClick = {
                    navController.navigate(Routes.WELCOME) {
                        popUpTo(0)
                    }
                }
            )
        }

        composable(Routes.CHANGE_PASSWORD) {
            ChangePasswordScreen()
        }

        composable(Routes.NETWORK_ERROR) {
            NetworkErrorScreen()
        }
    }
}