package com.example.transcribegenius.data

data class OpenAiResponse(
    val choices: List<Choice>
)

data class Choice(
    val text: String
)
