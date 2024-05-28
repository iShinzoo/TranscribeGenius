package com.example.transcribegenius.data.model

data class VideoDetailsResponse(
    val items: List<VideoItem>
)

data class VideoItem(
    val id: String,
    val snippet: Snippet
)

data class Snippet(
    val title: String,
    val description: String
)