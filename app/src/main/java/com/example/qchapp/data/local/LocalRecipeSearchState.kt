package com.example.qchapp.data.local

object LocalRecipeSearchState {

    var recipes: List<LocalRecipeEntity> = emptyList()

    var isLocalMode: Boolean = false

    var searchQuery: String = ""
}