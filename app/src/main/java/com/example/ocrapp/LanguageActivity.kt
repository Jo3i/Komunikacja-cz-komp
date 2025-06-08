package com.example.ocrapp

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit

class LanguageActivity : AppCompatActivity() {

    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language)

        preferences = getSharedPreferences("AppPrefs", MODE_PRIVATE)
    }

    fun onPolishClick(view: View) {
        preferences.edit() { putString("language", "pl") }
        Toast.makeText(this, "Wybrano język polski", Toast.LENGTH_SHORT).show()
        // Możesz tu dodać np. powrót do Settings lub restart activity, jeśli chcesz
    }

    fun onEnglishClick(view: View) {
        preferences.edit() { putString("language", "en") }
        Toast.makeText(this, "English selected", Toast.LENGTH_SHORT).show()
        // To samo co wyżej - ew. restart lub powrót
    }
}
