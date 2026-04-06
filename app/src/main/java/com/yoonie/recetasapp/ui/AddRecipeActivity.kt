package com.yoonie.recetasapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.yoonie.recetasapp.R
import com.yoonie.recetasapp.viewmodel.RecipeViewModel

class AddRecipeActivity : AppCompatActivity() {

    private val viewModel: RecipeViewModel by viewModels()

    private lateinit var etTitle: TextInputEditText
    private lateinit var etDescription: TextInputEditText
    private lateinit var etIngredients: TextInputEditText
    private lateinit var etSteps: TextInputEditText
    private lateinit var etPrepTime: TextInputEditText
    private lateinit var etServings: TextInputEditText
    private lateinit var btnSaveRecipe: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe)

        etTitle       = findViewById(R.id.etTitle)
        etDescription = findViewById(R.id.etDescription)
        etIngredients = findViewById(R.id.etIngredients)
        etSteps       = findViewById(R.id.etSteps)
        etPrepTime    = findViewById(R.id.etPrepTime)
        etServings    = findViewById(R.id.etServings)
        btnSaveRecipe = findViewById(R.id.btnSaveRecipe)
        progressBar   = findViewById(R.id.progressBar)

        val prefs  = getSharedPreferences("recetas_prefs", MODE_PRIVATE)
        val userId = prefs.getInt("user_id", 0)

        btnSaveRecipe.setOnClickListener {
            val title       = etTitle.text.toString().trim()
            val description = etDescription.text.toString().trim()
            val ingredients = etIngredients.text.toString().trim()
            val steps       = etSteps.text.toString().trim()
            val prepTime    = etPrepTime.text.toString().toIntOrNull() ?: 0
            val servings    = etServings.text.toString().toIntOrNull() ?: 1

            if (title.isEmpty()) {
                Toast.makeText(this, "El título es obligatorio", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (ingredients.isEmpty()) {
                Toast.makeText(this, "Agrega al menos un ingrediente", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (steps.isEmpty()) {
                Toast.makeText(this, "Agrega al menos un paso", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.createRecipe(
                userId      = userId,
                categoryId  = 1,
                title       = title,
                description = description,
                ingredients = ingredients,
                steps       = steps,
                prepTime    = prepTime,
                servings    = servings
            )
        }

        viewModel.apiResult.observe(this) { response ->
            if (response.status == "success") {
                Toast.makeText(this, "¡Receta guardada!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.loading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            btnSaveRecipe.isEnabled = !isLoading
        }

        viewModel.error.observe(this) { errorMsg ->
            Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show()
        }
    }
}