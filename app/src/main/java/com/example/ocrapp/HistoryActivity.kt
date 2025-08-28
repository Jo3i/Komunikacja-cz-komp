package com.example.ocrapp

import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ocrapp.databinding.ActivityHistoryBinding
import java.util.Locale

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var adapter: HistoryAdapter
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        preferences = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        val lang = preferences.getString("language", "en") ?: "en"
        setLocale(lang)

        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gradePrefix = getString(R.string.grade_prefix)//zmiana nazwy oceny przy zmianie jezyka
        val subjectName = getString(R.string.subjectName)//zmiana nazwy przedmiotu przy zmianie jezyka

        // Przykładowe dane
        val historyItems = listOf(
            HistoryItem("Jan Kowalski", "2025-06-08","$subjectName: j.Polski", "$gradePrefix: 4"),
            HistoryItem("Anna Nowak", "2025-06-07", "$subjectName: j.Polski","$gradePrefix: 5"),
            HistoryItem("Krzysztof Wiśniewski", "2025-06-06","$subjectName: j.Polski", "$gradePrefix: 3")
        )

        adapter = HistoryAdapter(historyItems)
        binding.historyRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.historyRecyclerView.adapter = adapter
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
    fun onBackToMenuClick(view: View) {
        finish() // lub możesz przejść jawnie do MainMenuActivity, jeśli chcesz
    }
}
