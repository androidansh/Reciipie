package com.anshuman_gupta.recipie_search_app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anshuman_gupta.recipie_search_app.MainActivity
import com.anshuman_gupta.recipie_search_app.R
import com.anshuman_gupta.recipie_search_app.dataModel.ParentAdapterArgs

class ParentAdapter(private val args: List<ParentAdapterArgs>): RecyclerView.Adapter<ParentAdapter.ViewHolder>() {

    private lateinit var adapterContext: Context

    inner class ViewHolder (view: View):RecyclerView.ViewHolder(view){
        val childRecycler:RecyclerView = view.findViewById(R.id.childRecycler)
        val childHeading:TextView = view.findViewById(R.id.child_recycler_heading)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        adapterContext = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.child_recycler_layout,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  args.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val parentArgs = args[position]
        if(parentArgs.category == "all"){
            holder.childRecycler.adapter = AllRecipeAdapter(MainActivity.allRecipe)
            holder.childRecycler.layoutManager = LinearLayoutManager(adapterContext,LinearLayoutManager.VERTICAL,false)
            holder.childHeading.text = "All Recipes"
        }else{
            holder.childRecycler.adapter = PopularRecipeAdapter(MainActivity.popularRecipe)
            holder.childRecycler.layoutManager = LinearLayoutManager(adapterContext,LinearLayoutManager.HORIZONTAL,false)
            holder.childHeading.text = "Popular Recipes"
        }
    }
}