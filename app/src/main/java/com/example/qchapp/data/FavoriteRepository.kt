package com.example.qchapp.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object FavoriteRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun saveFavorite(
        recipe: FavoriteRecipe,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        val userId = auth.currentUser?.uid ?: return onError()

        db.collection("users")
            .document(userId)
            .collection("favorites")
            .document(recipe.id.toString())
            .set(recipe)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onError()
            }
    }

    fun getFavorites(
        onSuccess: (List<FavoriteRecipe>) -> Unit,
        onError: () -> Unit
    ) {
        val userId = auth.currentUser?.uid ?: return onError()

        db.collection("users")
            .document(userId)
            .collection("favorites")
            .get()
            .addOnSuccessListener { result ->

                val favorites = result.documents.mapNotNull { document ->
                    document.toObject(FavoriteRecipe::class.java)
                }

                onSuccess(favorites)
            }
            .addOnFailureListener {
                onError()
            }
    }

    fun isFavorite(
        recipeId: Int,
        onResult: (Boolean) -> Unit
    ) {
        val userId = auth.currentUser?.uid ?: return onResult(false)

        db.collection("users")
            .document(userId)
            .collection("favorites")
            .document(recipeId.toString())
            .get()
            .addOnSuccessListener { document ->
                onResult(document.exists())
            }
            .addOnFailureListener {
                onResult(false)
            }
    }

    fun deleteFavorite(
        recipeId: Int,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        val userId = auth.currentUser?.uid ?: return onError()

        db.collection("users")
            .document(userId)
            .collection("favorites")
            .document(recipeId.toString())
            .delete()
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onError()
            }
    }
}