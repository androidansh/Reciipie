package com.anshuman_gupta.recipie_search_app.repository

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.anshuman_gupta.recipie_search_app.MainActivity
import com.anshuman_gupta.recipie_search_app.RecipeDetailActivity
import com.anshuman_gupta.recipie_search_app.SearchRecipeActivity
import com.anshuman_gupta.recipie_search_app.dataModel.AutocompleteResponse
import com.anshuman_gupta.recipie_search_app.dataModel.RandomRecipeData
import com.anshuman_gupta.recipie_search_app.dataModel.RecipeResult
import com.anshuman_gupta.recipie_search_app.dataModel.SimilarRecipeModel
import com.anshuman_gupta.recipie_search_app.screens.FoundRecipeFrag
import com.anshuman_gupta.recipie_search_app.screens.HomeFrag
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RecipeViewModel :ViewModel(){

    fun getRecipeDetails(recipeID:String){

        RetrofitInstance.getRecipes.getRecipeInformation(recipeID).enqueue(object :
            Callback<RecipeResult?> {
            override fun onResponse(call: Call<RecipeResult?>, response: Response<RecipeResult?>) {
                if(response.body()!= null){
                    SearchRecipeActivity.searchedRecipe =  response.body()!!
                    val foundRecipeFrag = FoundRecipeFrag()
                    SearchRecipeActivity.lottieSearch!!.visibility = View.GONE
                    SearchRecipeActivity.searchRecyclerView!!.visibility = View.VISIBLE
                    foundRecipeFrag.show(SearchRecipeActivity.fragMgr, "FoundRecipeFrag")
                }
            }
            override fun onFailure(call: Call<RecipeResult?>, t: Throwable) {
                if(t.localizedMessage != null){
                    Log.d("response => ", t.localizedMessage.toString())
                }
            }
        })
    }

    fun getPopularRecipeData(){
        RetrofitInstance.getRecipes.getRandomRecipe().enqueue(object : Callback<RandomRecipeData> {
            override fun onResponse(
                call: Call<RandomRecipeData>,
                response: Response<RandomRecipeData>
            ) {
                if (response.isSuccessful) {
                    val recipeResults: RandomRecipeData?= response.body()
                    Log.d("response =>","Thread package array size = ${recipeResults!!.recipes.size}")
                    if(MainActivity.popularRecipe.size == 0){
                        for( i  in recipeResults.recipes){
                            MainActivity.popularRecipe.add(i)
                        }
                    }
                    HomeFrag.parentAdapter!!.notifyDataSetChanged()
                }
            }
            override fun onFailure(call: Call<RandomRecipeData>, t: Throwable) {
//                t.localizedMessage?.let { Log.d("response => ", it.toString())
                if(t.localizedMessage != null){
                    Log.d("response => ", t.localizedMessage.toString())
                }

            }
        })
    }

    fun getAllRecipeData(){
        RetrofitInstance.getRecipes.getRandomRecipe().enqueue(object : Callback<RandomRecipeData> {
            override fun onResponse(
                call: Call<RandomRecipeData>,
                response: Response<RandomRecipeData>
            ) {
                if (response.isSuccessful) {
                    val recipeResults: RandomRecipeData?= response.body()
                    Log.d("response =>","Thread package array size = ${recipeResults!!.recipes.size}")
                    if(MainActivity.allRecipe.size == 0){
                        for( i  in recipeResults.recipes){

                            MainActivity.allRecipe.add(i)
                        }
                    }
                    HomeFrag.parentAdapter!!.notifyDataSetChanged()
                }
            }
            override fun onFailure(call: Call<RandomRecipeData>, t: Throwable) {
//                t.localizedMessage?.let { Log.d("response => ", it.toString()) }
                if(t.localizedMessage != null){
                    Log.d("response => ", t.localizedMessage.toString())
                }
            }

        })
    }

    fun getMoreData(){
        RetrofitInstance.getRecipes.getRandomRecipe().enqueue(object : Callback<RandomRecipeData> {
            override fun onResponse(
                call: Call<RandomRecipeData>,
                response: Response<RandomRecipeData>
            ) {
                if (response.isSuccessful) {
                    val recipeResults: RandomRecipeData?= response.body()
                    Log.d("response =>","Thread package array size = ${recipeResults!!.recipes.size}")
                    for( i  in recipeResults.recipes){
                        MainActivity.allRecipe.add(i)
                    }
                    HomeFrag.parentAdapter!!.notifyDataSetChanged()
                }
            }
            override fun onFailure(call: Call<RandomRecipeData>, t: Throwable) {
//                t.localizedMessage?.let { Log.d("response => ", it.toString()) }
                if(t.localizedMessage != null){
                    Log.d("response => ", t.localizedMessage.toString())
                }
            }

        })
    }

    fun getAutoCompleteSuggestions2(word:String){
        RetrofitInstance.getRecipes.getAutocomplete(query = word, number = "10").enqueue(object : Callback<List<AutocompleteResponse>?> {
            override fun onResponse(
                call: Call<List<AutocompleteResponse>?>,
                response: Response<List<AutocompleteResponse>?>
            ) {
                if (response.isSuccessful) {

                    if(SearchRecipeActivity.foundRecipes.size == 0){
                        for( i  in response.body()!!){
                            SearchRecipeActivity.foundRecipes.add(i)
                        }
                    }else{
                        SearchRecipeActivity.foundRecipes.removeAll(SearchRecipeActivity.foundRecipes.toSet())
                        for( i  in response.body()!!){
                            SearchRecipeActivity.foundRecipes.add(i)
                        }
                    }

                    SearchRecipeActivity.foundRecipeAdapter!!.notifyDataSetChanged()

                }
            }
            override fun onFailure(call: Call<List<AutocompleteResponse>?>, t: Throwable) {
                if(t.localizedMessage != null){
                    Log.d("response => ", t.localizedMessage.toString())
                }
            }
        })
    }

    fun getSimilarRecipe(recipeID:String){

        RetrofitInstance.getRecipes.getSimilar(recipeID).enqueue(object : Callback<List<SimilarRecipeModel>?> {
            override fun onResponse(
                call: Call<List<SimilarRecipeModel>?>,
                response: Response<List<SimilarRecipeModel>?>
            ) {
                if(response.isSuccessful){
                    if(SearchRecipeActivity.similarRecipeArray.size == 0){
                        for( i  in response.body()!!){
                            SearchRecipeActivity.similarRecipeArray.add(i)
                        }
                    }else{
                        SearchRecipeActivity.similarRecipeArray.removeAll(SearchRecipeActivity.similarRecipeArray.toSet())
                        for( i  in response.body()!!){
                            SearchRecipeActivity.similarRecipeArray.add(i)
                        }
                    }

                }
            }

            override fun onFailure(call: Call<List<SimilarRecipeModel>?>, t: Throwable) {
                if(t.localizedMessage != null){
                    Log.d("response => ", t.localizedMessage.toString())
                }
            }
        })

    }

    fun getRecipeInformation(recipeID:String, context:Context){
        RetrofitInstance.getRecipes.getRecipeInformation(recipeID).enqueue(object : Callback<RecipeResult?> {
            override fun onResponse(call: Call<RecipeResult?>, response: Response<RecipeResult?>) {
                if(response.isSuccessful){
                    MainActivity.clickedRecipe = response.body()
                    context.startActivity(Intent(context,RecipeDetailActivity::class.java))
                }
            }
            override fun onFailure(call: Call<RecipeResult?>, t: Throwable) {
                if(t.localizedMessage != null){
                    Log.d("response => ", t.localizedMessage.toString())
                }
            }
        })

    }
}