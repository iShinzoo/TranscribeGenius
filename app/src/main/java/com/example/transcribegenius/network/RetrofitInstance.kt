package com.example.transcribegenius.network

import com.example.transcribegenius.ApiKeyInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val apiKeyInterceptor = ApiKeyInterceptor(
        apiKey = "ede88c6a1bmsh03ffbec69f6e420p13570cjsnccd815f465ba",
        apiHost = "youtube-transcriptor.p.rapidapi.com"
    )

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(apiKeyInterceptor)
        .build()

    fun transcriptApiResponse(): TranscriptApi {
        val BASE_URL = "https://youtube-transcriptor.p.rapidapi.com/"
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(TranscriptApi::class.java)
    }

    fun OpenAiApiResponse(): OpenAiApi {
        val BASE_URL = "https://api.openai.com/"
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(OpenAiApi::class.java)
    }
}
