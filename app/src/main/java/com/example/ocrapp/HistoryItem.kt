package com.example.ocrapp

import java.io.Serializable

data class HistoryItem(
    val studentName: String,
    val date: String,
    val subject: String,
    val result: String
) : Serializable
