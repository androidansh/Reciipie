package com.anshuman_gupta.recipie_search_app.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
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

class PopularRecipeAdapter(private val recipes: List<RecipeResult>): RecyclerView.Adapter<PopularRecipeAdapter.PopularRecipeViewHolder>() {

    private lateinit var adapterContext:Context
    inner class PopularRecipeViewHolder( itemView: View): RecyclerView.ViewHolder(itemView) {
        val recipeImage:ImageView = itemView.findViewById(R.id.popularRecipeImage)
        val recipeName: TextView = itemView.findViewById(R.id.popularRecipeName)
        val recipeTime:TextView = itemView.findViewById(R.id.popularRecipeMakeTime)
        val recipeLayout:ConstraintLayout = itemView.findViewById(R.id.popularRecipeItem)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularRecipeViewHolder {
        adapterContext = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.popular_recipe_item,parent,false)
        return PopularRecipeViewHolder(view)
    }

    override fun getItemCount(): Int {

        return recipes.size
    }

    override fun onBindViewHolder(holder: PopularRecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.recipeName.text = recipe.title
        holder.recipeTime.text = "Ready in ${recipe.readyInMinutes} mins"
        Log.d("response =>","Item num = ${position} and title = ${recipe.title}")
        Glide.with(adapterContext).load(recipe.image).into(holder.recipeImage)
        holder.recipeLayout.setOnClickListener {
            MainActivity.clickedRecipe = recipe
            adapterContext.startActivity(Intent(adapterContext,RecipeDetailActivity::class.java))
        }
    }
}