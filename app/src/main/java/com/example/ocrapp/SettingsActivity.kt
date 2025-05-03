package com.example.ocrapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class SettingsActivity : AppCompatActivity() {

    private lateinit var preferences: SharedPreferences
    private var isDarkMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Odczyt SharedPreferences
        preferences = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        isDarkMode = preferences.getBoolean("dark_mode", false)
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )

        setContentView(R.layout.activity_settings)

        val btnTheme = findViewById<Button>(R.id.button_theme)
        val btnLanguage = findViewById<Button>(R.id.button_language)
        val btnLogout = findViewById<Button>(R.id.button_logout)

        btnTheme.setOnClickListener {
            isDarkMode = !isDarkMode
            AppCompatDelegate.setDefaultNightMode(
                if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
            preferences.edit().putBoolean("dark_mode", isDarkMode).apply()
        }

        btnLanguage.setOnClickListener {
            startActivity(Intent(this, LanguageActivity::class.java))
        }

        btnLogout.setOnClickListener {
            Toast.makeText(this, "Wylogowano (demo)", Toast.LENGTH_SHORT).show()
        }
    }
}
