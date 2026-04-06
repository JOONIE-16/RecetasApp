package com.yoonie.recetasapp.network

import com.yoonie.recetasapp.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("users")
    suspend fun getUserByEmail(
        @Query("email")  email: String,
        @Query("select") select: String = "*"
    ): Response<List<User>>

    @POST("users")
    suspend fun createUser(
        @Body user: Map<String, String>
    ): Response<List<User>>

    @GET("recipes")
    suspend fun getRecipes(
        @Query("select") select: String = "*",
        @Query("order")  order: String = "created_at.desc",
        @Query("title")  title: String = ""
    ): Response<List<Recipe>>

    @POST("recipes")
    suspend fun createRecipe(
        @Body recipe: Map<String, Any>
    ): Response<List<Recipe>>

    @PATCH("recipes")
    suspend fun updateRecipe(
        @Query("id") id: String,
        @Body recipe: Map<String, Any>
    ): Response<List<Recipe>>

    @DELETE("recipes")
    suspend fun deleteRecipe(
        @Query("id")      id: String,
        @Query("user_id") userId: String
    ): Response<Unit>

    @GET("categories")
    suspend fun getCategories(
        @Query("select") select: String = "*",
        @Query("order")  order: String = "name.asc"
    ): Response<List<Category>>
}