package com.example.repasoexamen.data

data class RecipeResponse(
    val recipes: List<Recipe>
) {

}

data class Recipe(
    val id: Int,
    val name: String,
    val image: String
) {

}