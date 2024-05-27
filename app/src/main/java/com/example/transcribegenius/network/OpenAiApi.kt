package com.example.transcribegenius.network

import com.example.transcribegenius.data.OpenAiRequest
import com.example.transcribegenius.data.OpenAiResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface OpenAiApiService {
    @Headers("Content-Type: application/json")
    @POST("completions")
    suspend fun generateContent(
        @Header("Authorization") apiKey: String,
        @Body request: OpenAiRequest
    ): OpenAiResponse
}