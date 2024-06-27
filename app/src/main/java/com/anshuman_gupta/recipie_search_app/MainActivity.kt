package com.anshuman_gupta.recipie_search_app

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.anshuman_gupta.recipie_search_app.adapter.AllRecipeAdapter
import com.anshuman_gupta.recipie_search_app.dataModel.RecipeResult
import com.anshuman_gupta.recipie_search_app.screens.FavRecipe
import com.anshuman_gupta.recipie_search_app.screens.HomeFrag
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var navigateFav:LinearLayout
    private lateinit var navigateHome:LinearLayout
    private lateinit var favImg:ImageView
    private lateinit var favTxt:TextView
    private lateinit var homeImg:ImageView
    private lateinit var homeTxt:TextView

    companion object{
        var clickedRecipe:RecipeResult ?= null
        var userName = ""

        var popularRecipe:ArrayList<RecipeResult>  = ArrayList()
        var allRecipe:ArrayList<RecipeResult>  = ArrayList()
        var userFavRecipe:ArrayList<RecipeResult>  = ArrayList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CoroutineScope(Dispatchers.Main).launch {
            val pref = getSharedPreferences("userFav", MODE_PRIVATE)
            val str = pref.getString("userFav", "")
            if(str  == ""){
                userFavRecipe = ArrayList()
            }else{
                userFavRecipe = Gson().fromJson(str, object : TypeToken<ArrayList<RecipeResult>>() {}.type)
            }
        }

        val pref = getSharedPreferences("user", MODE_PRIVATE)
        val str = pref.getString("user","")
        if(str != ""){
            userName = str!!
        }

        favTxt = findViewById(R.id.favTxt)
        favImg = findViewById(R.id.favImg)
        homeTxt = findViewById(R.id.homeTxt)
        homeImg = findViewById(R.id.homeImg)
        navigateFav = findViewById(R.id.navigateFav)
        navigateHome = findViewById(R.id.navigateHome)

        val fragManager = supportFragmentManager
        fragManager.beginTransaction().replace(R.id.mainFrame,HomeFrag()).commit()

        navigateFav.setOnClickListener {
            favImg.setBackgroundResource(R.drawable.fav_active)
            favTxt.setTextColor(resources.getColor(R.color.primary))

            homeImg.setBackgroundResource(R.drawable.home_inactive)
            homeTxt.setTextColor(resources.getColor(R.color.inactiveColor))

            fragManager.beginTransaction().replace(R.id.mainFrame,FavRecipe()).commit()

        }

        navigateHome.setOnClickListener {
            favImg.setBackgroundResource(R.drawable.fav_inactive)
            favTxt.setTextColor(resources.getColor(R.color.inactiveColor))

            homeImg.setBackgroundResource(R.drawable.home_active)
            homeTxt.setTextColor(resources.getColor(R.color.primary))

            fragManager.beginTransaction().replace(R.id.mainFrame,HomeFrag()).commit()
        }



    }
}