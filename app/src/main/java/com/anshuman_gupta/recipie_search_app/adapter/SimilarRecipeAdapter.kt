package com.anshuman_gupta.recipie_search_app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.anshuman_gupta.recipie_search_app.R
import com.anshuman_gupta.recipie_search_app.dataModel.SimilarRecipeModel
import com.anshuman_gupta.recipie_search_app.repository.RecipeViewModel
import com.anshuman_gupta.recipie_search_app.repository.RetrofitInstance

class SimilarRecipeAdapter(private var recipeList: ArrayList<SimilarRecipeModel>) : RecyclerView.Adapter<SimilarRecipeAdapter.ViewHolder>() {
    private lateinit var adapterContext: Context
    inner  class ViewHolder(view:View):RecyclerView.ViewHolder(view){
//        val recipeImage: ImageView = itemView.findViewById(R.id.allRecipeImage)
        val recipeName: TextView = itemView.findViewById(R.id.allRecipeName)
        val recipeTime: TextView = itemView.findViewById(R.id.allRecipePreparationTime)
        val recipeLayout: ConstraintLayout = itemView.findViewById(R.id.allRecipeLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        adapterContext = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.all_recipe_item,parent,false)
        return  ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = recipeList[position]
        holder.recipeName.text = item.title
        holder.recipeTime.text =  "Ready in ${item.readyInMinutes} mins"
        holder.recipeLayout.setOnClickListener {
            RecipeViewModel.getRecipeInformation(item.id.toString(),adapterContext)
        }
    }
}