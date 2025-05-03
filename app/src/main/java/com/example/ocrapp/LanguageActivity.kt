package com.example.ocrapp

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LanguageActivity : AppCompatActivity() {

    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language)

        preferences = getSharedPreferences("AppPrefs", MODE_PRIVATE)

        val btnPolish = findViewById<Button>(R.id.button_polish)
        val btnEnglish = findViewById<Button>(R.id.button_english)

        btnPolish.setOnClickListener {
            preferences.edit().putString("language", "pl").apply()
            Toast.makeText(this, "Wybrano język polski", Toast.LENGTH_SHORT).show()
        }

        btnEnglish.setOnClickListener {
            preferences.edit().putString("language", "en").apply()
            Toast.makeText(this, "English selected", Toast.LENGTH_SHORT).show()
        }
    }
}
