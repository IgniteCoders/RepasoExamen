package com.example.repasoexamen.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.repasoexamen.data.Recipe
import com.example.repasoexamen.databinding.ItemRecipeBinding
import com.squareup.picasso.Picasso

class RecipeAdapter(var items: List<Recipe>, val onClick: (Int) -> Unit) : RecyclerView.Adapter<RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemRecipeBinding.inflate(layoutInflater, parent, false)
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = items[position]
        holder.render(recipe)
        holder.binding.cardView.setOnClickListener {
            onClick(position)
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateData(dataSet: List<Recipe>) {
        items = dataSet
        notifyDataSetChanged()
    }
}

class RecipeViewHolder(val binding: ItemRecipeBinding) : RecyclerView.ViewHolder(binding.root) {

    fun render(recipe: Recipe) {
        binding.nameTextView.text = recipe.name
        Picasso.get().load(recipe.image).into(binding.recipeImageView)
    }

}