package com.yoonie.recetasapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.yoonie.recetasapp.ui.HomeActivity
import com.yoonie.recetasapp.ui.RegisterActivity
import com.yoonie.recetasapp.viewmodel.RecipeViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: RecipeViewModel by viewModels()

    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnLogin: Button
    private lateinit var tvRegister: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etEmail     = findViewById(R.id.etEmail)
        etPassword  = findViewById(R.id.etPassword)
        btnLogin    = findViewById(R.id.btnLogin)
        tvRegister  = findViewById(R.id.tvRegister)
        progressBar = findViewById(R.id.progressBar)

        btnLogin.setOnClickListener {
            val email    = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.login(email, password)
        }

        tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        viewModel.loginResult.observe(this) { response ->
            if (response.status == "success") {
                val prefs = getSharedPreferences("recetas_prefs", MODE_PRIVATE)
                prefs.edit().apply {
                    putInt("user_id",    response.user?.id ?: 0)
                    putString("user_name",  response.user?.name ?: "")
                    putString("user_email", response.user?.email ?: "")
                    apply()
                }
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        }

        viewModel.loading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            btnLogin.isEnabled = !isLoading
        }

        viewModel.error.observe(this) { errorMsg ->
            Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show()
        }
    }
}