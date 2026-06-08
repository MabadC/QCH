package com.example.qchapp.validation

import android.util.Patterns

class Validations {

    fun isRegisterFormValid(
        name: String,
        email: String,
        password: String,
        termsAccepted: Boolean
    ): Boolean {
        return name.isNotBlank()
                && email.isNotBlank()
                && password.isNotBlank()
                && termsAccepted
    }

    fun cleanIngredients(ingredients: List<String>): List<String> {
        return ingredients
            .map { it.trim() }
            .filter { it.isNotBlank() }
    }

    fun canSearchRecipes(ingredients: List<String>): Boolean {
        return cleanIngredients(ingredients).isNotEmpty()
    }

    fun calculateDifficulty(readyInMinutes: Int?): String {
        val minutes = readyInMinutes ?: return "fácil"

        return when {
            minutes <= 20 -> "fácil"
            minutes <= 45 -> "media"
            else -> "difícil"
        }
    }

    fun canGuestAccessPrivateFeatures(isAnonymous: Boolean): Boolean {
        return !isAnonymous
    }

    fun canUseFavoritesInReducedMode(isReducedMode: Boolean): Boolean {
        return !isReducedMode
    }

    fun canAcceptTerms(termsOpened: Boolean, accepted: Boolean): Boolean {
        return termsOpened && accepted
    }
}