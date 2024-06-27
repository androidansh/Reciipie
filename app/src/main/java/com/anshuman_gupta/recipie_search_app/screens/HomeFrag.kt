package com.anshuman_gupta.recipie_search_app.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.anshuman_gupta.recipie_search_app.MainActivity
import com.anshuman_gupta.recipie_search_app.R
import com.anshuman_gupta.recipie_search_app.SearchRecipeActivity
import com.anshuman_gupta.recipie_search_app.adapter.ParentAdapter
import com.anshuman_gupta.recipie_search_app.dataModel.ParentAdapterArgs
import com.anshuman_gupta.recipie_search_app.repository.RecipeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("StaticFieldLeak")
class HomeFrag : Fragment() {

    private lateinit var parentRecyclerView: RecyclerView
    private lateinit var search:LinearLayout
    private lateinit var user:TextView

    companion object{
        lateinit var parentAdapter: ParentAdapter
        lateinit var lottieAnime:LottieAnimationView
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        search = view.findViewById(R.id.search)
        user = view.findViewById(R.id.userName)
        lottieAnime = view.findViewById(R.id.homeLoading)
        if(MainActivity.userName == ""){
            user.text = "Hey User"
        }else{
            user.text = "Hey ${MainActivity.userName}"
        }
        
        search.setOnClickListener {
            requireContext().startActivity(Intent(context,SearchRecipeActivity::class.java))
        }

        parentRecyclerView = view.findViewById(R.id.parentRecyclerView)
        parentRecyclerView.layoutManager = LinearLayoutManager(context)
        val list:List<ParentAdapterArgs> = listOf(ParentAdapterArgs(MainActivity.popularRecipe,"popular"),
            ParentAdapterArgs(MainActivity.allRecipe,"all")
        )
        parentAdapter = ParentAdapter(list)
        parentRecyclerView.adapter = parentAdapter

        CoroutineScope(Dispatchers.IO).launch {
            RecipeViewModel.getPopularRecipeData()
            RecipeViewModel.getAllRecipeData()
            Handler(Looper.getMainLooper()).postDelayed({
                parentRecyclerView.visibility = View.VISIBLE
                lottieAnime.visibility = View.INVISIBLE
            },1500)
        }

        return view
    }

}