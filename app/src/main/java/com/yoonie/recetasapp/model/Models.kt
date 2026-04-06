package com.yoonie.recetasapp.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")         val id: Int = 0,
    @SerializedName("name")       val name: String = "",
    @SerializedName("email")      val email: String = "",
    @SerializedName("password")   val password: String = "",
    @SerializedName("avatar_url") val avatarUrl: String? = null
)

data class Recipe(
    @SerializedName("id")          val id: Int = 0,
    @SerializedName("user_id")     val userId: Int = 0,
    @SerializedName("category_id") val categoryId: Int = 0,
    @SerializedName("title")       val title: String = "",
    @SerializedName("description") val description: String = "",
    @SerializedName("ingredients") val ingredients: String = "",
    @SerializedName("steps")       val steps: String = "",
    @SerializedName("image_url")   val imageUrl: String? = null,
    @SerializedName("prep_time")   val prepTime: Int = 0,
    @SerializedName("servings")    val servings: Int = 1,
    @SerializedName("created_at")  val createdAt: String = ""
)

data class Category(
    @SerializedName("id")   val id: Int = 0,
    @SerializedName("name") val name: String = ""
)

data class LoginResponse(
    val status: String = "",
    val message: String = "",
    val user: User? = null
)

data class ApiResponse(
    val status: String = "",
    val message: String = ""
)