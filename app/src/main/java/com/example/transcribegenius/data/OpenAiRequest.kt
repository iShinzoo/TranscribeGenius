package com.example.transcribegenius.data

data class OpenAiRequest(
    val model: String,
    val prompt: String,
    val max_tokens: Int
)