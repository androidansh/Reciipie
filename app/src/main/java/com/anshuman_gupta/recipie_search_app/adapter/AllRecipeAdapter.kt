package com.anshuman_gupta.recipie_search_app.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.anshuman_gupta.recipie_search_app.MainActivity
import com.anshuman_gupta.recipie_search_app.R
import com.anshuman_gupta.recipie_search_app.RecipeDetailActivity
import com.anshuman_gupta.recipie_search_app.dataModel.RecipeResult
import com.bumptech.glide.Glide

class AllRecipeAdapter(private val recipes: List<RecipeResult>): RecyclerView.Adapter<AllRecipeAdapter.ViewHolder>() {

    private lateinit var adapterContext: Context

    inner class ViewHolder( itemView: View): RecyclerView.ViewHolder(itemView) {
        val recipeImage: ImageView = itemView.findViewById(R.id.allRecipeImage)
        val recipeName: TextView = itemView.findViewById(R.id.allRecipeName)
        val recipeTime: TextView = itemView.findViewById(R.id.allRecipePreparationTime)
        val recipeLayout: ConstraintLayout = itemView.findViewById(R.id.allRecipeLayout)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        adapterContext = parent.context
        val view  = LayoutInflater.from(adapterContext).inflate(R.layout.all_recipe_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.recipeName.text = recipe.title
        holder.recipeTime.text = "Ready in ${recipe.readyInMinutes} mins"
        Glide.with(adapterContext).load(recipe.image).into(holder.recipeImage)
        holder.recipeLayout.setOnClickListener {
            MainActivity.clickedRecipe = recipe
            adapterContext.startActivity(Intent(adapterContext, RecipeDetailActivity::class.java))
        }
    }
}