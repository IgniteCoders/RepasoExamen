package com.example.repasoexamen.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.repasoexamen.R
import com.example.repasoexamen.adapters.RecipeAdapter
import com.example.repasoexamen.data.Recipe
import com.example.repasoexamen.data.RecipesService
import com.example.repasoexamen.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    var recipeList: List<Recipe> = emptyList()

    lateinit var adapter: RecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapter = RecipeAdapter(recipeList) { position ->
            val recipe = recipeList[position]
            // Toast.makeText(this, recipe.name, Toast.LENGTH_SHORT).show()

            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_RECIPE_ID, recipe.id)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        binding.recyclerView.adapter = adapter

        binding.searchBar.setOnQueryTextSubmitListener { query ->
            search(query)
        }

        search()
    }

    fun search(query: String = "") {
        CoroutineScope(Dispatchers.IO).launch {
            recipeList = RecipesService.getInstance().getRecipesListByName(query).recipes

            //Log.i("API", recipeList.toString())

            CoroutineScope(Dispatchers.Main).launch {
                adapter.updateData(recipeList)
            }
        }
    }
}