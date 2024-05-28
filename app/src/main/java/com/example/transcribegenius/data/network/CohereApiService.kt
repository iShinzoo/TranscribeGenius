package com.example.transcribegenius.data.network

import com.example.transcribegenius.data.model.CohereResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface CohereApiService {
    @POST("generate")
    suspend fun generateContent(
        @Body requestBody: RequestBody
    ): CohereResponse
}