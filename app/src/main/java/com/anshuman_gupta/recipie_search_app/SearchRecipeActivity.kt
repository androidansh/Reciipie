package com.anshuman_gupta.recipie_search_app

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.anshuman_gupta.recipie_search_app.adapter.FoundRecipeAdapter
import com.anshuman_gupta.recipie_search_app.dataModel.AutocompleteResponse
import com.anshuman_gupta.recipie_search_app.dataModel.RecipeResult
import com.anshuman_gupta.recipie_search_app.dataModel.SimilarRecipeModel
import com.anshuman_gupta.recipie_search_app.databinding.ActivitySearchRecipeBinding
import com.anshuman_gupta.recipie_search_app.repository.RecipeViewModel
import com.anshuman_gupta.recipie_search_app.screens.FoundRecipeFrag
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchRecipeActivity : AppCompatActivity() {

    companion object{
        var similarRecipeArray:ArrayList<SimilarRecipeModel> = ArrayList()
        var foundRecipes:ArrayList<AutocompleteResponse> = ArrayList()
        var foundRecipeAdapter: FoundRecipeAdapter? = null
        lateinit var fragMgr:FragmentManager
        var searchedRecipe:RecipeResult ?= null
        var searchRecyclerView: RecyclerView? = null
        var lottieSearch: LottieAnimationView? = null
    }

    private lateinit var binding: ActivitySearchRecipeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fragMgr = supportFragmentManager
        lottieSearch = binding.root.findViewById(R.id.searchLoading)
        searchRecyclerView = binding.root.findViewById(R.id.searchRecycler)
        foundRecipeAdapter = FoundRecipeAdapter(foundRecipes)
        binding.searchRecycler.adapter = foundRecipeAdapter
        binding.searchRecycler.layoutManager = LinearLayoutManager(applicationContext)

        binding.searchBackIcon.setOnClickListener {
            finish()
        }

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var toSearch = s.toString()
                CoroutineScope(Dispatchers.IO).launch {
                    RecipeViewModel.getAutoCompleteSuggestions2(toSearch)
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

    }

}