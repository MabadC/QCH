package com.example.qchapp.validation

import org.junit.Assert.*
import org.junit.Test

class ValidationsTest {

    private val validations = Validations()

    @Test
    fun registerForm_isInvalid_whenNameIsEmpty() {

        val result = validations.isRegisterFormValid(
            name = "",
            email = "test@test.com",
            password = "abc123456",
            termsAccepted = true
        )

        assertFalse(result)
    }

    @Test
    fun registerForm_isValid_whenAllFieldsAreCompleted() {

        val result = validations.isRegisterFormValid(
            name = "Prueba2",
            email = "test@test.com",
            password = "123456",
            termsAccepted = true
        )

        assertTrue(result)
    }

    @Test
    fun cleanIngredients_removesSpacesAndEmptyValues() {

        val result = validations.cleanIngredients(
            listOf(" pollo ", "", " arroz ", "   ")
        )

        assertEquals(
            listOf("pollo", "arroz"),
            result
        )
    }

    @Test
    fun registerForm_isInvalid_whenEmailIsEmpty() {

        val result = validations.isRegisterFormValid(
            name = "Prueba3",
            email = "",
            password = "123456",
            termsAccepted = true
        )

        assertFalse(result)
    }

    @Test
    fun registerForm_isInvalid_whenPasswordIsEmpty() {

        val result = validations.isRegisterFormValid(
            name = "Prueba4",
            email = "test@test.com",
            password = "",
            termsAccepted = true
        )

        assertFalse(result)
    }

    @Test
    fun registerForm_isInvalid_whenTermsAreNotAccepted() {

        val result = validations.isRegisterFormValid(
            name = "Prueba5",
            email = "test@test.com",
            password = "123456",
            termsAccepted = false
        )

        assertFalse(result)
    }

    @Test
    fun canSearchRecipes_returnsFalse_whenIngredientsAreEmpty() {

        val result = validations.canSearchRecipes(
            listOf("", "   ")
        )

        assertFalse(result)
    }

    @Test
    fun canSearchRecipes_returnsTrue_whenThereIsAtLeastOneIngredient() {

        val result = validations.canSearchRecipes(
            listOf("", " huevo ")
        )

        assertTrue(result)
    }

    @Test
    fun calculateDifficulty_returnsEasy_whenTimeIsLow() {

        val result = validations.calculateDifficulty(15)

        assertEquals("fácil", result)
    }

    @Test
    fun calculateDifficulty_returnsMedium_whenTimeIsIntermediate() {

        val result = validations.calculateDifficulty(35)

        assertEquals("media", result)
    }

    @Test
    fun calculateDifficulty_returnsHard_whenTimeIsHigh() {

        val result = validations.calculateDifficulty(60)

        assertEquals("difícil", result)
    }

    @Test
    fun guestCannotAccessPrivateFeatures() {

        val result = validations.canGuestAccessPrivateFeatures(
            isAnonymous = true
        )

        assertFalse(result)
    }

    @Test
    fun registeredUserCanAccessPrivateFeatures() {

        val result = validations.canGuestAccessPrivateFeatures(
            isAnonymous = false
        )

        assertTrue(result)
    }

    @Test
    fun reducedModeCannotUseFavorites() {

        val result = validations.canUseFavoritesInReducedMode(
            isReducedMode = true
        )

        assertFalse(result)
    }

    @Test
    fun normalModeCanUseFavorites() {

        val result = validations.canUseFavoritesInReducedMode(
            isReducedMode = false
        )

        assertTrue(result)
    }

    @Test
    fun termsCannotBeAcceptedIfDialogWasNotOpened() {

        val result = validations.canAcceptTerms(
            termsOpened = false,
            accepted = true
        )

        assertFalse(result)
    }

    @Test
    fun termsCanBeAcceptedAfterDialogWasOpened() {

        val result = validations.canAcceptTerms(
            termsOpened = true,
            accepted = true
        )

        assertTrue(result)
    }

}