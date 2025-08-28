package com.example.ocrapp

import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import java.util.Locale
import androidx.core.content.edit

class MainMenuActivity : AppCompatActivity() {

    private lateinit var preferences: SharedPreferences

    // pobranie do startowania LanguageActivity i odbierania rezultatu
    private val languageActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            // Jeśli język został zmieniony to restart aktywności aby załadować nowy język
            recreate()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        preferences = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        val lang = preferences.getString("language", "en") ?: "en"
        setLocale(lang)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
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

    fun onButtonScanClick(view: View) {
        val intent = Intent(this, ScanActivity::class.java)
        startActivity(intent)
    }

    fun onButtonHistoryClick(view: View) {
        val intent = Intent(this, HistoryActivity::class.java)
        startActivity(intent)
    }

    fun onButtonSettingsClick(view: View) {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }
    fun onButtonLogoutClick(view: View) {
        // wyczyść dane sesji, SharedPreferences itp.
        val preferences = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        preferences.edit() { clear() }  // czyszczenie danych użytkownika

        // Wracamy do ekranu logowania
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

}
