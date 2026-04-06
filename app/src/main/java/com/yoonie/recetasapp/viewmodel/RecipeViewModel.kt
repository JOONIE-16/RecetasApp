package com.yoonie.recetasapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yoonie.recetasapp.model.*
import com.yoonie.recetasapp.repository.RecipeRepository
import kotlinx.coroutines.launch

class RecipeViewModel : ViewModel() {

    private val repository = RecipeRepository()

    private val _recipes = MutableLiveData<List<Recipe>>()
    val recipes: LiveData<List<Recipe>> = _recipes

    private val _loginResult = MutableLiveData<LoginResponse>()
    val loginResult: LiveData<LoginResponse> = _loginResult

    private val _apiResult = MutableLiveData<ApiResponse>()
    val apiResult: LiveData<ApiResponse> = _apiResult

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    // ---- AUTH ----
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = repository.getUserByEmail(email)
                if (response.isSuccessful) {
                    val users = response.body()
                    if (users.isNullOrEmpty()) {
                        _error.value = "Usuario no encontrado"
                    } else {
                        val user = users[0]
                        if (user.password == password) {
                            _loginResult.value = LoginResponse(
                                status  = "success",
                                message = "Login exitoso",
                                user    = user
                            )
                        } else {
                            _error.value = "Contraseña incorrecta"
                        }
                    }
                } else {
                    _error.value = "Error al iniciar sesión"
                }
            } catch (e: Exception) {
                _error.value = "Error de conexión: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = repository.createUser(name, email, password)
                if (response.isSuccessful) {
                    _apiResult.value = ApiResponse(
                        status  = "success",
                        message = "Usuario registrado correctamente"
                    )
                } else {
                    _error.value = "Error al registrarse"
                }
            } catch (e: Exception) {
                _error.value = "Error de conexión: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    // ---- RECETAS ----
    fun getRecipes(search: String = "") {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = repository.getRecipes(search)
                if (response.isSuccessful) {
                    _recipes.value = response.body() ?: emptyList()
                } else {
                    _error.value = "Error al cargar recetas"
                }
            } catch (e: Exception) {
                _error.value = "Error de conexión: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun createRecipe(
        userId: Int, categoryId: Int, title: String,
        description: String, ingredients: String,
        steps: String, prepTime: Int, servings: Int
    ) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = repository.createRecipe(
                    userId, categoryId, title, description,
                    ingredients, steps, prepTime, servings
                )
                if (response.isSuccessful) {
                    _apiResult.value = ApiResponse(
                        status  = "success",
                        message = "Receta creada correctamente"
                    )
                } else {
                    _error.value = "Error al crear receta"
                }
            } catch (e: Exception) {
                _error.value = "Error de conexión: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun deleteRecipe(recipeId: Int) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = repository.deleteRecipe(recipeId)
                if (response.isSuccessful) {
                    _apiResult.value = ApiResponse(
                        status  = "success",
                        message = "Receta eliminada"
                    )
                    getRecipes()
                } else {
                    _error.value = "Error al eliminar receta"
                }
            } catch (e: Exception) {
                _error.value = "Error de conexión: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
}