package com.example.transcribegenius.network

import com.example.transcribegenius.util.YOUR_OPENAI_API_KEY
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val youtubeApi: YouTubeApiService by lazy {
        Retrofit.Builder().baseUrl("https://www.googleapis.com/youtube/v3/")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(YouTubeApiService::class.java)
    }

    val openAiApi: OpenAiApiService by lazy {
        val httpClient = OkHttpClient.Builder().apply {
            addInterceptor { chain ->
                val request =
                    chain.request().newBuilder().addHeader("Content-Type", "application/json")
                        .addHeader("Authorization", "Bearer $YOUR_OPENAI_API_KEY").build()
                chain.proceed(request)
            }
        }.build()

        Retrofit.Builder().baseUrl("https://api.openai.com/v1/").client(httpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(OpenAiApiService::class.java)
    }
}
