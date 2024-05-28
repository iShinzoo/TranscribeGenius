package com.example.transcribegenius.data.network

import com.example.transcribegenius.util.CO_API_KEY
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val youtubeApi: YouTubeApiService by lazy {
        Retrofit.Builder().baseUrl("https://www.googleapis.com/youtube/v3/")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(YouTubeApiService::class.java)
    }

    private const val COHERE_BASE_URL = "https://api.cohere.com/v1/"

    // Adding Cohere API service
    val cohereApi: CohereApiService by lazy {
        val httpClient = OkHttpClient.Builder().apply {
            addInterceptor { chain ->
                val request =
                    chain.request().newBuilder()
                        .addHeader("Authorization", "bearer $CO_API_KEY")
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Accept", "application/json")
                        .build()
                chain.proceed(request)
            }
        }.build()

        Retrofit.Builder().baseUrl(COHERE_BASE_URL).client(httpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(CohereApiService::class.java)
    }
}
