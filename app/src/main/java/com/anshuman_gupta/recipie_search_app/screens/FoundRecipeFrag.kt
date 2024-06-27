package com.anshuman_gupta.recipie_search_app.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.anshuman_gupta.recipie_search_app.MainActivity
import com.anshuman_gupta.recipie_search_app.R
import com.anshuman_gupta.recipie_search_app.SearchRecipeActivity
import com.anshuman_gupta.recipie_search_app.adapter.EquipmentAdapter
import com.anshuman_gupta.recipie_search_app.adapter.IngredientAdapter
import com.anshuman_gupta.recipie_search_app.adapter.SimilarRecipeAdapter
import com.anshuman_gupta.recipie_search_app.dataModel.sub_data_model.Equipment
import com.anshuman_gupta.recipie_search_app.databinding.FragmentFoundRecipeBinding
import com.anshuman_gupta.recipie_search_app.repository.RecipeViewModel
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("StaticFieldLeak")
class FoundRecipeFrag : BottomSheetDialogFragment() {

    private lateinit var  binding:FragmentFoundRecipeBinding
    private var equipment:ArrayList<Equipment> = ArrayList()
    private lateinit var ingredientAdapter: IngredientAdapter
    private lateinit var equipmentAdapter: EquipmentAdapter
    companion object{

        var similarAdapter: SimilarRecipeAdapter ?= null
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding = FragmentFoundRecipeBinding.inflate(inflater,container,false)
        getEquipment()
        binding.foundBack.setOnClickListener {
            onDestroyView()
        }

        binding.foundRecipeTitle.text = SearchRecipeActivity.searchedRecipe?.title
        Glide.with(requireContext()).load(SearchRecipeActivity.searchedRecipe?.image).into(binding.foundRecipeImage)
        binding.foundRecipeTime.text = SearchRecipeActivity.searchedRecipe?.readyInMinutes.toString() + " min"
        binding.foundRecipeServing.text = SearchRecipeActivity.searchedRecipe?.servings.toString()
        binding.foundRecipePrice.text = "₹ " +  SearchRecipeActivity.searchedRecipe?.pricePerServing.toString()

        ingredientAdapter = IngredientAdapter(SearchRecipeActivity.searchedRecipe!!.extendedIngredients)
        binding.foundRecipeIngredientRecycler.adapter = ingredientAdapter
        binding.foundRecipeIngredientRecycler.layoutManager = LinearLayoutManager(requireContext())

        equipmentAdapter = EquipmentAdapter(equipment)
        binding.foundEquipmentRecyclerView.adapter = equipmentAdapter
        binding.foundEquipmentRecyclerView.layoutManager = GridLayoutManager(requireContext(),2)

        chkFavourite()

        binding.foundRecipeFavBtn.setOnClickListener {
            if(chkFavourite()){
                binding.foundRecipeFavBtn.setImageResource(R.drawable.fav_inactive)
                removeRecipe()
            }else{
                binding.foundRecipeFavBtn.setImageResource(R.drawable.fav_active)
                saveRecipe()
            }
        }

        val instructionTxt = Html.fromHtml(SearchRecipeActivity.searchedRecipe?.instructions)
        binding.foundRecipeInstructions.text = instructionTxt
        binding.foundRecipeInstructions.setAnimationDuration(500L)
        binding.foundRecipeInstructions.setInterpolator(OvershootInterpolator())
        binding.foundRecipeInstructions.setOnClickListener {
            if (binding.foundRecipeInstructions.isExpanded) {
                binding.foundRecipeInstructions.collapse()
                binding.foundInstructionsToggle.text = resources.getText(R.string.see_more)
            } else {
                binding.foundRecipeInstructions.expand()
                binding.foundInstructionsToggle.text = resources.getText(R.string.see_less)
            }
        }

        val summaryTxt = Html.fromHtml(SearchRecipeActivity.searchedRecipe?.summary)
        binding.foundRecipeSummary.text = summaryTxt
        binding.foundRecipeSummary.setAnimationDuration(500L)
        binding.foundRecipeSummary.setInterpolator(OvershootInterpolator())
        binding.foundRecipeSummary.setOnClickListener {
            if (binding.foundRecipeSummary.isExpanded) {
                binding.foundRecipeSummary.collapse()
                binding.foundSummaryToggle.text = resources.getText(R.string.see_more)
            } else {
                binding.foundRecipeSummary.expand()
                binding.foundSummaryToggle.text = resources.getText(R.string.see_less)
            }
        }




        binding.navigateBTN.setOnClickListener {
            if(binding.navigateBTN.tag == "1"){
                binding.basicDetailsLayout.visibility = View.GONE
                binding.foundIngredientLayoutToggle.visibility = View.VISIBLE
                binding.foundIngredientLayout.visibility = View.VISIBLE
                binding.navigateFoundRecipeText.text = context?.resources?.getText(R.string.get_full_recipe)
                binding.navigateBTN.tag = "2"
            }
            else if(binding.navigateBTN.tag == "2"){
                binding.foundIngredientLayout.visibility = View.GONE
                binding.foundFullRecipeLayoutToggle.visibility = View.VISIBLE
                binding.foundFullRecipeLayout.visibility = View.VISIBLE
                binding.navigateFoundRecipeText.text = context?.resources?.getText(R.string.get_similar_recipe)
                binding.navigateBTN.tag = "3"
                binding.foundImg1.setBackgroundResource(R.drawable.down)
            }
            else if(binding.navigateBTN.tag == "3"){
                binding.parentLayout.visibility = View.GONE
                binding.progressLayout.visibility = View.VISIBLE

                RecipeViewModel.getSimilarRecipe(SearchRecipeActivity.searchedRecipe!!.id.toString())



                Handler(Looper.getMainLooper()).postDelayed({
                    FoundRecipeFrag.similarAdapter = SimilarRecipeAdapter(SearchRecipeActivity.similarRecipeArray)
                    binding.SimilarRecipeRecycler.adapter = similarAdapter
                    binding.SimilarRecipeRecycler.layoutManager = LinearLayoutManager(requireContext())
                }, 2000)

                binding.parentLayout.visibility = View.VISIBLE
                binding.progressLayout.visibility = View.GONE

                binding.foundFullRecipeLayout.visibility = View.GONE
                binding.SimilarRecipeLayoutToggle.visibility = View.VISIBLE
//                binding.SimilarRecipeLayout.visibility = View.VISIBLE

                binding.navigateBTN.visibility = View.GONE
            }
        }

        binding.foundImg1.setOnClickListener {
            if(binding.foundIngredientLayout.visibility == View.GONE){
                binding.foundIngredientLayout.visibility = View.VISIBLE
                binding.foundImg1.setBackgroundResource(R.drawable.up)
                //collapsing similar recipe
                binding.SimilarRecipeLayout.visibility = View.GONE
                binding.SimilarRecipeToggle.setBackgroundResource(R.drawable.down)
                // collapsing full recipe
                binding.foundFullRecipeLayout.visibility = View.GONE
                binding.foundImg2.setBackgroundResource(R.drawable.down)
            }else{
                // collapsing ingredients
                binding.foundIngredientLayout.visibility = View.GONE
                binding.foundImg1.setBackgroundResource(R.drawable.down)
            }
        }

        binding.foundImg2.setOnClickListener {
            if(binding.foundFullRecipeLayout.visibility == View.GONE){
                binding.foundFullRecipeLayout.visibility = View.VISIBLE
                binding.foundImg2.setBackgroundResource(R.drawable.up)
                // collapsing ingredients
                binding.foundIngredientLayout.visibility = View.GONE
                binding.foundImg1.setBackgroundResource(R.drawable.down)
                //collapsing similar recipe
                binding.SimilarRecipeLayout.visibility = View.GONE
                binding.SimilarRecipeToggle.setBackgroundResource(R.drawable.down)
            }else{
                // collapsing full recipe
                binding.foundFullRecipeLayout.visibility = View.GONE
                binding.foundImg2.setBackgroundResource(R.drawable.down)

            }
        }

        binding.SimilarRecipeToggle.setOnClickListener {
            if(binding.SimilarRecipeLayout.visibility == View.GONE){
                binding.SimilarRecipeLayout.visibility = View.VISIBLE
                binding.SimilarRecipeToggle.setBackgroundResource(R.drawable.up)
                // collapsing full recipe
                binding.foundFullRecipeLayout.visibility = View.GONE
                binding.foundImg2.setBackgroundResource(R.drawable.down)
                // collapsing ingredients
                binding.foundIngredientLayout.visibility = View.GONE
                binding.foundImg1.setBackgroundResource(R.drawable.down)
            }else{
                //collapsing similar recipe
                binding.SimilarRecipeLayout.visibility = View.GONE
                binding.SimilarRecipeToggle.setBackgroundResource(R.drawable.down)
            }
        }

        return binding.root
    }
    private fun getEquipment(){
        for( i in SearchRecipeActivity.searchedRecipe!!.analyzedInstructions){
            for(j in i.steps){
                for(k in j.equipment){
                    if(!equipment.contains(k)){
                        equipment.add(k)
                    }
                }
            }
        }
    }

    private fun saveRecipe(){
        CoroutineScope(Dispatchers.IO).launch {
            MainActivity.userFavRecipe.add(SearchRecipeActivity.searchedRecipe!!)
            val pref = requireContext().getSharedPreferences("userFav", AppCompatActivity.MODE_PRIVATE)
            val str = Gson().toJson(MainActivity.userFavRecipe)
            pref.edit().putString("userFav",str).apply()

        }
    }

    private fun removeRecipe(){
        CoroutineScope(Dispatchers.IO).launch {

            for(i in 0..MainActivity.userFavRecipe.size){
                if(MainActivity.userFavRecipe[i].id == SearchRecipeActivity.searchedRecipe!!.id){
                    MainActivity.userFavRecipe.removeAt(i)
                    val pref = requireContext().getSharedPreferences("userFav", AppCompatActivity.MODE_PRIVATE)
                    val str = Gson().toJson(MainActivity.userFavRecipe)
                    pref.edit().putString("userFav",str).apply()
                    break

                }
            }
        }
    }

    private fun chkFavourite():Boolean{
        for(i in MainActivity.userFavRecipe){
            if(i.id == SearchRecipeActivity.searchedRecipe!!.id){
                binding.foundRecipeFavBtn.setImageResource(R.drawable.fav_active)
                return true
            }
        }
        binding.foundRecipeFavBtn.setImageResource(R.drawable.fav_inactive)
        return false
    }

}