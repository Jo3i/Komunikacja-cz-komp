package com.example.ocrapp

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

// Model zapytania
data class PuterRequest(
    val model: String = "gpt-3.5-turbo", // darmowy model na Puter
    val messages: List<Map<String, String>>
)

// Model odpowiedzi
data class PuterResponse(
    val choices: List<Choice>
) {
    data class Choice(val message: Message) {
        data class Message(val role: String, val content: String)
    }
}

// Definicja endpointu
interface PuterApiService {
    @Headers("Content-Type: application/json")
    @POST("chat/completions") // endpoint Puter AI
    fun getCompletion(@Body request: PuterRequest): Call<PuterResponse>
}
