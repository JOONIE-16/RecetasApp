package com.yoonie.recetasapp.repository

import com.yoonie.recetasapp.network.RetrofitClient

class RecipeRepository {

    private val api = RetrofitClient.instance

    // ---- AUTH ----
    suspend fun getUserByEmail(email: String) =
        api.getUserByEmail("eq.$email")

    suspend fun createUser(name: String, email: String, password: String) =
        api.createUser(mapOf(
            "name"     to name,
            "email"    to email,
            "password" to password
        ))

    // ---- RECETAS ----
    suspend fun getRecipes(search: String = "") =
        api.getRecipes(
            title = if (search.isNotEmpty()) "ilike.*$search*" else ""
        )

    suspend fun createRecipe(
        userId: Int,
        categoryId: Int,
        title: String,
        description: String,
        ingredients: String,
        steps: String,
        prepTime: Int,
        servings: Int
    ) = api.createRecipe(mapOf(
        "user_id"     to userId,
        "category_id" to categoryId,
        "title"       to title,
        "description" to description,
        "ingredients" to ingredients,
        "steps"       to steps,
        "prep_time"   to prepTime,
        "servings"    to servings
    ))

    suspend fun updateRecipe(
        recipeId: Int,
        categoryId: Int,
        title: String,
        description: String,
        ingredients: String,
        steps: String,
        prepTime: Int,
        servings: Int
    ) = api.updateRecipe(
        id = "eq.$recipeId",
        recipe = mapOf(
            "category_id" to categoryId,
            "title"       to title,
            "description" to description,
            "ingredients" to ingredients,
            "steps"       to steps,
            "prep_time"   to prepTime,
            "servings"    to servings
        )
    )

    suspend fun deleteRecipe(recipeId: Int) =
        api.deleteRecipe(id = "eq.$recipeId", userId = "")

    suspend fun getCategories() = api.getCategories()
}