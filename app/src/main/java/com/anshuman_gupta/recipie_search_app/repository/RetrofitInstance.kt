package com.anshuman_gupta.recipie_search_app.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {
    private val BASE_URL = "https://api.spoonacular.com/recipes/"
    val API_KEY = "Add Your Own Api Key"

    val retrofitInstance by lazy {
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
    }


    val getRecipes by lazy {
        retrofitInstance.create(RecipeRepository::class.java)
    }

    fun getInfo(): RecipeRepository {
        return retrofitInstance.create(RecipeRepository::class.java)
    }
}
