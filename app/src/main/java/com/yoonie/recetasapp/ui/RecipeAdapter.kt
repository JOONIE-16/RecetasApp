package com.yoonie.recetasapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yoonie.recetasapp.R
import com.yoonie.recetasapp.model.Recipe

class RecipeAdapter(
    private var recipes: List<Recipe>,
    private val onItemClick: (Recipe) -> Unit,
    private val onDeleteClick: (Recipe) -> Unit
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle:    TextView = itemView.findViewById(R.id.tvTitle)
        val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)
        val tvPrepTime: TextView = itemView.findViewById(R.id.tvPrepTime)
        val tvDelete:   TextView = itemView.findViewById(R.id.tvDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recipe, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.tvTitle.text    = recipe.title
        holder.tvCategory.text = "📂 Categoría ${recipe.categoryId}"
        holder.tvPrepTime.text = "⏱ ${recipe.prepTime} min"
        holder.tvDelete.text   = "🗑️ Eliminar"

        holder.itemView.setOnClickListener { onItemClick(recipe) }
        holder.tvDelete.setOnClickListener { onDeleteClick(recipe) }
    }

    override fun getItemCount() = recipes.size

    fun updateRecipes(newRecipes: List<Recipe>) {
        recipes = newRecipes
        notifyDataSetChanged()
    }
}