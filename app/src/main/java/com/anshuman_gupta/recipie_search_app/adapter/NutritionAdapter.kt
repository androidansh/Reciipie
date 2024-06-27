package com.anshuman_gupta.recipie_search_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anshuman_gupta.recipie_search_app.R
import com.anshuman_gupta.recipie_search_app.dataModel.sub_data_model.RecipeNutrients

class NutritionAdapter(val nutrition:List<RecipeNutrients>): RecyclerView.Adapter<NutritionAdapter.ViewHolder>() {

    inner class ViewHolder (view: View): RecyclerView.ViewHolder(view){
        val nutrientName: TextView = view.findViewById(R.id.ingredient_name)
        val nutrientAmount: TextView = view.findViewById(R.id.ingredient_amount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_ingredients,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return nutrition.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = nutrition[position]
        holder.nutrientName.text = item.name
        holder.nutrientAmount.text = "${item.amount} ${item.unit}"
    }
}