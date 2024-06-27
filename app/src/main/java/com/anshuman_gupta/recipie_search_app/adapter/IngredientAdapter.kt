package com.anshuman_gupta.recipie_search_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anshuman_gupta.recipie_search_app.R
import com.anshuman_gupta.recipie_search_app.dataModel.sub_data_model.ExtendedIngredient

class IngredientAdapter(private val ingredientList: List<ExtendedIngredient>) : RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {

    inner class IngredientViewHolder (view:View): RecyclerView.ViewHolder(view){
        val ingredientName: TextView = view.findViewById(R.id.ingredient_name)
        val ingredientAmount: TextView = view.findViewById(R.id.ingredient_amount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_ingredients, parent, false)
        return IngredientViewHolder(view)
    }

    override fun getItemCount(): Int {
        return ingredientList.size
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val item = ingredientList[position]
        holder.ingredientName.text = item.name
        holder.ingredientAmount.text = "${item.amount} ${item.unit}"
    }

}