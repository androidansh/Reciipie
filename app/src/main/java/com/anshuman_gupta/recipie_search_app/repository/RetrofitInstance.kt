package com.anshuman_gupta.recipie_search_app.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {
    private val BASE_URL = "https://api.spoonacular.com/recipes/"
    val API_KEY = "23c1414d4c6946198d679d79459d4990"

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