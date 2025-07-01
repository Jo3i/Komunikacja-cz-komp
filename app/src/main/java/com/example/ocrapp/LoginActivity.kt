package com.example.ocrapp

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import java.util.Locale

class LoginActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var preferences: SharedPreferences

    private val languageActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            // Po powrocie z wyboru języka odświeżamy LoginActivity, aby załadować nowy język
            recreate()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Inicjalizacja SharedPreferences przed super.onCreate
        preferences = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        val lang = preferences.getString("language", "en") ?: "en"
        setLocale(lang)  // ustaw język

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameEditText = findViewById(R.id.username)
        passwordEditText = findViewById(R.id.password)
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

    fun onLoginClick(view: View) {
        val username = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (username.isNotEmpty() && password.isNotEmpty()) {
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Wypełnij wszystkie pola (czymkolwiek, funkcjonalnosc testowa)", Toast.LENGTH_SHORT).show()
        }
    }

    // Funkcje do zmiany języka z przycisków (np. android:onClick)
    fun onPolishClick(view: View) {
        saveLanguageAndApply("pl")
    }

    fun onEnglishClick(view: View) {
        saveLanguageAndApply("en")
    }

    private fun saveLanguageAndApply(languageCode: String) {
        preferences.edit {
            putString("language", languageCode)
        }
        setLocale(languageCode)
        recreate() // odśwież aktywność, by zmiana języka była widoczna
    }

    // Wywołanie zmiany języka z osobnej aktywności (jeśli używasz)
    fun onChangeLanguageClick(view: View) {
        val intent = Intent(this, LanguageActivity::class.java)
        languageActivityLauncher.launch(intent)
    }
}
