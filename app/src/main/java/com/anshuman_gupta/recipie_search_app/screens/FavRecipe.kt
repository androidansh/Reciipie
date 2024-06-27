package com.anshuman_gupta.recipie_search_app.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.anshuman_gupta.recipie_search_app.MainActivity
import com.anshuman_gupta.recipie_search_app.R
import com.anshuman_gupta.recipie_search_app.adapter.AllRecipeAdapter
import com.anshuman_gupta.recipie_search_app.databinding.FragmentFavRecipeBinding

class FavRecipe : Fragment() {

    private lateinit var binding: FragmentFavRecipeBinding
    companion object{
        var favAdapter: AllRecipeAdapter ?= null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {

       binding = FragmentFavRecipeBinding.inflate(inflater, container, false)


        favAdapter = AllRecipeAdapter(MainActivity.userFavRecipe)
        binding.favRecycler.adapter =  favAdapter
        binding.favRecycler.setHasFixedSize(true)
        binding.favRecycler.layoutManager = LinearLayoutManager(requireContext())


        return binding.root
    }

    override fun onResume() {
        super.onResume()
        favAdapter!!.notifyDataSetChanged()
    }

}