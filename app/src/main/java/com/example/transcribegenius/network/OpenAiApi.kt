package com.example.transcribegenius.network

import com.example.transcribegenius.data.OpenAiRequest
import com.example.transcribegenius.data.OpenAiResponse
import com.example.transcribegenius.util.Authorization
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface OpenAiApi {
    @Headers("Content-Type: application/json")
    @POST("v1/engines/davinci/completions")
    suspend fun generateContent(
        @Header("Authorization") apiKey: String,
        @Body request: OpenAiRequest
    ): OpenAiResponse
}