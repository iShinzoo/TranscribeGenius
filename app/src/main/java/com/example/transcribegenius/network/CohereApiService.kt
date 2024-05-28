package com.example.transcribegenius.network

import com.example.transcribegenius.data.CohereResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface CohereApiService {
    @POST("generate")
    suspend fun generateContent(
        @Body requestBody: RequestBody
    ): CohereResponse
}