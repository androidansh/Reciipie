package com.anshuman_gupta.recipie_search_app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anshuman_gupta.recipie_search_app.R
import com.anshuman_gupta.recipie_search_app.SearchRecipeActivity
import com.anshuman_gupta.recipie_search_app.dataModel.AutocompleteResponse
import com.anshuman_gupta.recipie_search_app.repository.RecipeViewModel
import com.anshuman_gupta.recipie_search_app.screens.FoundRecipeFrag
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FoundRecipeAdapter(private var words:ArrayList<AutocompleteResponse>): RecyclerView.Adapter<FoundRecipeAdapter.ViewHolder>() {

    private lateinit var adapterContext: Context
    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val recipeName: TextView = view.findViewById(R.id.foundRecipeName)
        var recipeLayout:LinearLayout = view.findViewById(R.id.foundRecipeLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        adapterContext = parent.context
        val view = LayoutInflater.from(adapterContext).inflate(R.layout.searched_recipe_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return words.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.recipeName.text = words[position].title
        holder.recipeLayout.setOnClickListener {
            SearchRecipeActivity.searchRecyclerView!!.visibility = View.GONE
            SearchRecipeActivity.lottieSearch!!.visibility = View.VISIBLE
            CoroutineScope(Dispatchers.IO).launch {
                RecipeViewModel.getRecipeDetails(words[position].id)
            }
        }
    }
}