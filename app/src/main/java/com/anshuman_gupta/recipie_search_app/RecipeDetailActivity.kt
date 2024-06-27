package com.anshuman_gupta.recipie_search_app

import android.os.Bundle
import android.text.Html
import android.view.animation.OvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.anshuman_gupta.recipie_search_app.adapter.EquipmentAdapter
import com.anshuman_gupta.recipie_search_app.adapter.IngredientAdapter
import com.anshuman_gupta.recipie_search_app.adapter.NutritionAdapter
import com.anshuman_gupta.recipie_search_app.dataModel.RecipeResult
import com.anshuman_gupta.recipie_search_app.dataModel.sub_data_model.Equipment
import com.anshuman_gupta.recipie_search_app.databinding.ActivityRecipeDetailBinding
import com.anshuman_gupta.recipie_search_app.repository.RecipeRepository
import com.anshuman_gupta.recipie_search_app.repository.RecipeViewModel
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeDetailBinding
    private var equipment:ArrayList<Equipment> = ArrayList()
    private lateinit var ingredientAdapter: IngredientAdapter
    private lateinit var equipmentAdapter:EquipmentAdapter
    private lateinit var nutritionAdapter: NutritionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getEquipment() // getting equipment list for recipe

        Glide.with(this).load(MainActivity.clickedRecipe!!.image).into(binding.detailRecipeImage)

        binding.recipeName.text = MainActivity.clickedRecipe!!.title

        if(MainActivity.clickedRecipe!!.vegetarian){
            binding.recipeType.setBackgroundResource(R.drawable.veg_icon)
        }else{
            binding.recipeType.setBackgroundResource(R.drawable.non_veg_icon)
        }

        chkFavourite()

        binding.recipeSave.setOnClickListener {
            if(chkFavourite()){
                removeRecipe()
            }else{
                saveRecipe()
            }
        }

// general details of recipe
        binding.clickedRecipeTime.text = MainActivity.clickedRecipe!!.readyInMinutes.toString() + " mins"
        binding.clickedRecipeServing.text = MainActivity.clickedRecipe!!.servings.toString()
        binding.clickedRecipePrice.text = "₹ " + MainActivity.clickedRecipe!!.pricePerServing.toString()

// ingredients of recipe
        ingredientAdapter = IngredientAdapter(MainActivity.clickedRecipe!!.extendedIngredients)
        binding.indredientRecyclerView.adapter = ingredientAdapter
        binding.indredientRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
// recipe nutrition
        nutritionAdapter = NutritionAdapter(MainActivity.clickedRecipe!!.nutrition.nutrients)
        binding.nutritionRecyclerView.adapter = nutritionAdapter
        binding.nutritionRecyclerView.layoutManager = LinearLayoutManager(this)

// instructions of recipe

        val instructionTxt = Html.fromHtml(MainActivity.clickedRecipe!!.instructions)
        binding.clickedRecipeInstructions.text = instructionTxt
        binding.clickedRecipeInstructions.setAnimationDuration(500L)
        binding.clickedRecipeInstructions.setInterpolator(OvershootInterpolator())

        binding.clickedRecipeInstructions.setOnClickListener {
            if (binding.clickedRecipeInstructions.isExpanded) {
                binding.clickedRecipeInstructions.collapse()
                binding.clickedRecipeInstructionsToggle.text = resources.getText(R.string.see_more)
            } else {
                binding.clickedRecipeInstructions.expand()
                binding.clickedRecipeInstructionsToggle.text = resources.getText(R.string.see_less)
            }
        }
// equipments of recipe
        equipmentAdapter = EquipmentAdapter(equipment)
        binding.equipmentRecyclerView.adapter = equipmentAdapter
        binding.equipmentRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
// summary of recipe
        val summaryTxt = Html.fromHtml(MainActivity.clickedRecipe!!.summary)
        binding.clickedRecipeSummary.text = summaryTxt
        binding.clickedRecipeSummary.setAnimationDuration(500L)
        binding.clickedRecipeSummary.setInterpolator(OvershootInterpolator())
        binding.clickedRecipeSummary.setOnClickListener {
            if (binding.clickedRecipeSummary.isExpanded) {
                binding.clickedRecipeSummary.collapse()
                binding.clickedRecipeSummaryToggle.text = resources.getText(R.string.see_more)
            } else {
                binding.clickedRecipeSummary.expand()
                binding.clickedRecipeSummaryToggle.text = resources.getText(R.string.see_less)
            }
        }

    }

    private fun getEquipment(){
        CoroutineScope(Dispatchers.IO).launch {
            for( i in MainActivity.clickedRecipe!!.analyzedInstructions){
                for(j in i.steps){
                    for(k in j.equipment){
                        equipment.add(k)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        MainActivity.clickedRecipe = null
    }

    private fun saveRecipe(){
        CoroutineScope(Dispatchers.IO).launch {
            MainActivity.userFavRecipe.add(MainActivity.clickedRecipe!!)
            val pref = getSharedPreferences("userFav", MODE_PRIVATE)
            val str = Gson().toJson(MainActivity.userFavRecipe)
            pref.edit().putString("userFav",str).apply()
            runOnUiThread {
                binding.recipeSave.setImageResource(R.drawable.fav_active)
            }

        }
    }

    private fun removeRecipe(){
        CoroutineScope(Dispatchers.IO).launch {

            for(i in 0..MainActivity.userFavRecipe.size){
                if(MainActivity.userFavRecipe[i].id == MainActivity.clickedRecipe!!.id){
                    MainActivity.userFavRecipe.removeAt(i)
                    break
                }
                val pref = getSharedPreferences("userFav", MODE_PRIVATE)
                val str = Gson().toJson(MainActivity.userFavRecipe)
                pref.edit().putString("userFav",str).apply()
                runOnUiThread {
                    binding.recipeSave.setImageResource(R.drawable.fav_inactive)
                }
            }
        }
    }

    private fun chkFavourite():Boolean{
        for(i in MainActivity.userFavRecipe){
            if(i.id == MainActivity.clickedRecipe!!.id){
                binding.recipeSave.setImageResource(R.drawable.fav_active)
                return true
            }
        }
        binding.recipeSave.setImageResource(R.drawable.fav_inactive)
        return false
    }
}