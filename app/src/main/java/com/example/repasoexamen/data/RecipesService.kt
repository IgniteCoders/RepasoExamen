package com.example.repasoexamen.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipesService {

    @GET("recipes")
    suspend fun getRecipesList() : RecipeResponse

    @GET("recipes/{recipe-id}")
    suspend fun getRecipeById(@Path("recipe-id") id: Int) : Recipe

    companion object {
        fun getInstance() : RecipesService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://dummyjson.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(RecipesService::class.java)
        }
    }
}