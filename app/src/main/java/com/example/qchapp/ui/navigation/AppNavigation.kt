package com.example.qchapp.ui.navigation

//import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.qchapp.ui.screens.*
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.example.qchapp.data.local.LocalRecipeImporter
import com.example.qchapp.data.local.LocalRecipeCsvImporter
import android.widget.Toast

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
    const val EDIT_USERNAME = "edit_username"
    const val DELETE_ACCOUNT = "delete_account"
    const val NETWORK_ERROR = "network_error"

    const val LOCAL_RESULTS = "local_results"

    const val LOCAL_RECIPE_DETAILS = "local_recipe_details/{recipeId}"

    fun recipeDetails(recipeId: Int): String {
        return "recipe_details/$recipeId"
    }

    fun localRecipeDetails(recipeId: Int): String {
        return "local_recipe_details/$recipeId"
    }
}

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    val context = LocalContext.current

    LaunchedEffect(Unit) {

        LocalRecipeImporter.importRecipes(context)

        LocalRecipeCsvImporter.importRecipes(context)

        /* comprobación de que han cargado las recetas locales.
        Log.d(

            "ROOM_TEST",
            "Recetas en Room: ${
                LocalRecipeRepository.getRecipesCount(context)
            }"
        ) */

    }

    val currentUser = FirebaseAuth
        .getInstance()
        .currentUser

    val startDestination =

        if (currentUser != null)
            Routes.SEARCH
        else
            Routes.WELCOME


    NavHost(
        navController = navController,
        startDestination = startDestination
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

                onBackClick = {
                    navController.popBackStack()
                },

                onLoginSuccess = {
                    navController.navigate(Routes.SEARCH)
                },

                onForgotPasswordClick = {
                    navController.navigate(Routes.PASSWORD_RECOVERY)
                }
            )
        }

        composable(Routes.REGISTER) {
            RegisterScreen(

                onBackClick = {
                    navController.popBackStack()
                },

                onRegisterSuccess = {
                    navController.navigate(
                        Routes.SEARCH
                    )
                }
            )
        }

        composable(Routes.PASSWORD_RECOVERY) {
            PasswordRecoveryScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onLoginClick = {
                    navController.navigate(Routes.LOGIN)
                }
            )
        }

        composable(Routes.SEARCH) {
            SearchScreen(
                onSearchClick = {
                    navController.navigate(Routes.SEARCH)
                },
                onLocalResultsClick = {
                    navController.navigate(Routes.LOCAL_RESULTS)
                },
                onFavoritesClick = {
                    navController.navigate(Routes.SAVED_RECIPES)
                },
                onProfileClick = {
                    navController.navigate(Routes.PROFILE)
                },
                onSearchRecipesClick = {
                    navController.navigate(Routes.RESULTS)
                },
                onNetworkError = {
                    navController.navigate(Routes.NETWORK_ERROR)
                }
            )
        }

        composable(Routes.RESULTS) {
            ResultsScreen(
                onBackClick = {
                    navController.popBackStack(
                        route = Routes.SEARCH,
                        inclusive = false
                    )
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

        composable(Routes.LOCAL_RESULTS) {
            LocalResultsScreen(
                onBackClick = {
                    navController.popBackStack(
                        route = Routes.SEARCH,
                        inclusive = false
                    )
                },
                onSearchClick = {
                    navController.navigate(Routes.SEARCH)
                },
                onFavoritesClick = {
                    Toast.makeText(
                        context,
                        "Los favoritos no están disponibles en modo reducido",
                        Toast.LENGTH_LONG
                    ).show()
                },
                onProfileClick = {
                    Toast.makeText(
                        context,
                        "El perfil no está disponible en modo reducido",
                        Toast.LENGTH_LONG
                    ).show()
                },
                onRecipeClick = { recipeId ->
                    navController.navigate(
                        Routes.localRecipeDetails(recipeId)
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

        composable(Routes.LOCAL_RECIPE_DETAILS) { backStackEntry ->

            val recipeId = backStackEntry.arguments
                ?.getString("recipeId")
                ?.toIntOrNull()
                ?: 1

            LocalRecipeDetailsScreen(
                recipeId = recipeId,
                onBackClick = {
                    navController.popBackStack()
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
                onLoginClick = {
                    navController.navigate(Routes.LOGIN)
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
                onEditUsernameClick = {
                    navController.navigate(Routes.EDIT_USERNAME)
                },
                onDeleteAccountClick = {
                    navController.navigate(Routes.DELETE_ACCOUNT)
                },
                onLoginClick = {
                    navController.navigate(Routes.LOGIN)
                },
                onRegisterClick = {
                    navController.navigate(Routes.REGISTER)
                },
                onLogoutClick = {
                    FirebaseAuth.getInstance().signOut()

                    navController.navigate(Routes.WELCOME) {
                        popUpTo(0)
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Routes.CHANGE_PASSWORD) {
            ChangePasswordScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.EDIT_USERNAME) {
            EditUsernameScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.DELETE_ACCOUNT) {
            DeleteAccountScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onAccountDeleted = {
                    navController.navigate(Routes.WELCOME) {
                        popUpTo(0)
                        launchSingleTop = true
                    }
                }
            )
        }


        composable(Routes.NETWORK_ERROR) {
            NetworkErrorScreen(
                onRetryClick = {
                    navController.navigate(Routes.SEARCH) {
                        popUpTo(Routes.NETWORK_ERROR) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}