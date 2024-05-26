package com.example.transcribegenius.network

import com.example.transcribegenius.data.TranscriptResponse
import com.example.transcribegenius.util.X_RapidAPI_Host
import com.example.transcribegenius.util.X_RapidAPI_Key
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface TranscriptApi {
    @GET("youtube-transcript")
    suspend fun getTranscript(
        @Query("video_id") videoId: String,
        @Header("X-RapidAPI-Key") apiKey: String,
        @Header("X-RapidAPI-Host") apiHost: String
    ): TranscriptResponse
}