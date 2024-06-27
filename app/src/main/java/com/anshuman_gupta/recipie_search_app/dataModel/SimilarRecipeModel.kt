package com.anshuman_gupta.recipie_search_app.dataModel

data class SimilarRecipeModel(
    val id: Int,
    val imageType: String,
    val readyInMinutes: Int,
    val servings: Int,
    val sourceUrl: String,
    val title: String
)