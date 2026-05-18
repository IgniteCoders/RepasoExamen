package com.example.repasoexamen.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.repasoexamen.R
import com.example.repasoexamen.data.Recipe
import com.example.repasoexamen.data.RecipesService
import com.example.repasoexamen.databinding.ActivityDetailBinding
import com.example.repasoexamen.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailBinding

    var recipe: Recipe? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val id = intent.getIntExtra(EXTRA_RECIPE_ID, -1)

        getRecipe(id)

    }

    fun getRecipe(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            recipe = RecipesService.getInstance().getRecipeById(id)

            //Log.i("API", recipe.toString())

            CoroutineScope(Dispatchers.Main).launch {
                loadData()
            }
        }
    }

    fun loadData() {
        recipe?.let {
            binding.nameTextView.text = it.name
            Picasso.get().load(it.image).into(binding.recipeImageView)
        }
    }

    companion object {
        const val EXTRA_RECIPE_ID = "RECIPE_ID"
    }
}