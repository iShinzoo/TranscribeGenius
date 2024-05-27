package com.example.transcribegenius.network

import com.example.transcribegenius.data.VideoDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface YouTubeApiService {
    @GET("videos")
    suspend fun getVideoDetails(
        @Query("part") part: String,
        @Query("id") videoId: String,
        @Query("key") apiKey: String
    ): VideoDetailsResponse
}