package com.example.ocrapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit

class SettingsActivity : AppCompatActivity() {

    private lateinit var preferences: SharedPreferences
    private var isDarkMode = false

    // Launcher do uruchamiania LanguageActivity i odbierania wyniku
    private val languageActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            // Po powrocie z LanguageActivity odświeżamy aktywność, by załadować nowy język
            recreate()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        preferences = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        isDarkMode = preferences.getBoolean("dark_mode", false)
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )

        setContentView(R.layout.activity_settings)
    }

    // Metoda wywoływana z android:onClick="onChangeThemeClick"
    fun onChangeThemeClick(view: View) {
        isDarkMode = !isDarkMode
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
        preferences.edit { putBoolean("dark_mode", isDarkMode) }
        Toast.makeText(this, "Motyw zmieniony", Toast.LENGTH_SHORT).show()
    }

    // Metoda wywoływana z android:onClick="onChangeLanguageClick"
    fun onChangeLanguageClick(view: View) {
        val intent = Intent(this, LanguageActivity::class.java)
        languageActivityLauncher.launch(intent)
    }

    // Metoda wywoływana z android:onClick="onLogoutClick"
    fun onLogoutClick(view: View) {
        // Tu wylogowanie - np. czyszczenie SharedPreferences lub tokenów itp.
        preferences.edit { clear() }
        Toast.makeText(this, "Wylogowano", Toast.LENGTH_SHORT).show()

        // Przejście do ekranu logowania
        val intent = Intent(this, LoginActivity::class.java)
        // Czyść stos, żeby nie można było wrócić przyciskiem wstecz
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
    fun onBackToMenuClick(view: View) {
        finish() // zamyka ustawienia i wraca do poprzedniego ekranu
    }
}
