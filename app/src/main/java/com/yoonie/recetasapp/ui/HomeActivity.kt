package com.yoonie.recetasapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.yoonie.recetasapp.R
import com.yoonie.recetasapp.viewmodel.RecipeViewModel

class HomeActivity : AppCompatActivity() {

    private val viewModel: RecipeViewModel by viewModels()
    private lateinit var adapter: RecipeAdapter

    private lateinit var recyclerRecipes: RecyclerView
    private lateinit var etSearch: TextInputEditText
    private lateinit var fabAddRecipe: FloatingActionButton
    private lateinit var progressBar: ProgressBar
    private lateinit var tvUserName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        recyclerRecipes = findViewById(R.id.recyclerRecipes)
        etSearch        = findViewById(R.id.etSearch)
        fabAddRecipe    = findViewById(R.id.fabAddRecipe)
        progressBar     = findViewById(R.id.progressBar)
        tvUserName      = findViewById(R.id.tvUserName)

        val prefs    = getSharedPreferences("recetas_prefs", MODE_PRIVATE)
        val userId   = prefs.getInt("user_id", 0)
        val userName = prefs.getString("user_name", "") ?: ""
        tvUserName.text = "Hola, $userName"

        adapter = RecipeAdapter(
            recipes       = emptyList(),
            onItemClick   = { recipe ->
                Toast.makeText(this, recipe.title, Toast.LENGTH_SHORT).show()
            },
            onDeleteClick = { recipe ->
                AlertDialog.Builder(this)
                    .setTitle("Eliminar receta")
                    .setMessage("¿Seguro que quieres eliminar '${recipe.title}'?")
                    .setPositiveButton("Eliminar") { _, _ ->
                        viewModel.deleteRecipe(recipe.id)
                    }
                    .setNegativeButton("Cancelar", null)
                    .show()
            }
        )

        recyclerRecipes.layoutManager = LinearLayoutManager(this)
        recyclerRecipes.adapter = adapter

        viewModel.getRecipes()

        viewModel.recipes.observe(this) { recipes ->
            adapter.updateRecipes(recipes)
        }

        etSearch.setOnEditorActionListener { _, _, _ ->
            val search = etSearch.text.toString().trim()
            viewModel.getRecipes(search)
            true
        }

        fabAddRecipe.setOnClickListener {
            startActivity(Intent(this, AddRecipeActivity::class.java))
        }

        viewModel.apiResult.observe(this) { response ->
            if (response.status == "success") {
                viewModel.getRecipes()
            }
        }

        viewModel.loading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(this) { errorMsg ->
            Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getRecipes()
    }
}