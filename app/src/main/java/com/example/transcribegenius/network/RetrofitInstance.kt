package com.example.transcribegenius.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val client = OkHttpClient()

    fun transcriptApiResponse(): TranscriptApi {
        val BASE_URL = "https://youtube-transcriptor.p.rapidapi.com/"
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(TranscriptApi::class.java)
    }

    fun OpenAiApiResponse(): OpenAiApi {
        val BASE_URL = "https://api.openai.com/"
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(OpenAiApi::class.java)
    }

}
