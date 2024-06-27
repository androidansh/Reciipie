package com.anshuman_gupta.recipie_search_app.dataModel.sub_data_model

data class Step(
    val equipment: List<Equipment>,
    val ingredients: List<Ingredient>,
    val length: Length,
    val number: Int,
    val step: String
)