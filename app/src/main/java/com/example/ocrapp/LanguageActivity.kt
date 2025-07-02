package com.example.ocrapp

import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import java.util.Locale

class LanguageActivity : AppCompatActivity() {

    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language)

        preferences = getSharedPreferences("AppPrefs", MODE_PRIVATE)
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration(resources.configuration)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale)
        } else {
            @Suppress("DEPRECATION")
            config.locale = locale
        }

        @Suppress("DEPRECATION")
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    fun onPolishClick(view: View) {
        preferences.edit {
            putString("language", "pl")
        }
        setLocale("pl")
        Toast.makeText(this, "Wybrano język polski", Toast.LENGTH_SHORT).show()
        setResult(RESULT_OK)
        finish()
    }

    fun onEnglishClick(view: View) {
        preferences.edit {
            putString("language", "en")
        }
        setLocale("en")
        Toast.makeText(this, "English selected", Toast.LENGTH_SHORT).show()
        setResult(RESULT_OK)
        finish()
    }
    fun onBackToMenuClick(view: View) {
        finish() // zamyka ustawienia i wraca do poprzedniego ekranu
    }
}
