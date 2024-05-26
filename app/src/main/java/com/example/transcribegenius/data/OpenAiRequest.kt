package com.example.transcribegenius.data

data class OpenAiRequest(
    val prompt: String,
    val max_tokens: Int
)