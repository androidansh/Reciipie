package com.anshuman_gupta.recipie_search_app.repository

import com.anshuman_gupta.recipie_search_app.dataModel.RandomRecipeData
import com.anshuman_gupta.recipie_search_app.dataModel.RecipeResult
import com.anshuman_gupta.recipie_search_app.dataModel.AutocompleteResponse
import com.anshuman_gupta.recipie_search_app.dataModel.SimilarRecipeModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeRepository {

    @GET("{id}/information")
    fun getRecipeInformation(
        @Path("id") id: String,
        @Query("apiKey") apiKey: String = RetrofitInstance.API_KEY,
        @Query("includeNutrition") includeNutrition: Boolean = true
    ): Call<RecipeResult>


    @GET("random")
    fun getRandomRecipe(
        @Query("apiKey") apiKey: String = RetrofitInstance.API_KEY,
        @Query("number") number: String="10",
        @Query("includeNutrition") includeNutrition: Boolean = true
    ):Call<RandomRecipeData>

    @GET("autocomplete")
    fun getAutocomplete(
        @Query("number") number: String = "5",
        @Query("query") query: String,
        @Query("apiKey") apiKey: String = RetrofitInstance.API_KEY
    ): Call<List<AutocompleteResponse>>

    @GET("{id}/similar")
    fun getSimilar(
        @Path("id") id: String,
        @Query("apiKey") apiKey: String = RetrofitInstance.API_KEY
    ):Call<List<SimilarRecipeModel>>

}