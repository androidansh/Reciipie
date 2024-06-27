package com.anshuman_gupta.recipie_search_app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anshuman_gupta.recipie_search_app.R
import com.anshuman_gupta.recipie_search_app.dataModel.sub_data_model.Equipment
import com.bumptech.glide.Glide

class EquipmentAdapter(private val equipmentList: ArrayList<Equipment>):RecyclerView.Adapter<EquipmentAdapter.ViewHolder>() {
    private lateinit var adapterContext: Context
    inner class ViewHolder(view : View):RecyclerView.ViewHolder(view){
        val equipmentImg:ImageView = view.findViewById(R.id.clickedRecipeEquipmentImg)
        val equipmentName: TextView = view.findViewById(R.id.clickedRecipeEquipmentName)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        adapterContext = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_equiment_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return equipmentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = equipmentList[position]
        Glide.with(adapterContext).load(item.image).into(holder.equipmentImg)
        holder.equipmentName.text = item.name
    }

}