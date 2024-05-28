package com.example.transcribegenius

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.transcribegenius.data.network.RetrofitInstance
import com.example.transcribegenius.util.YOUR_YT_API_KEY
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class ContentGeneratorViewModel : ViewModel() {
    private val _generatedContent = MutableStateFlow("")
    val generatedContent: StateFlow<String> = _generatedContent

    fun generateContent(youtubeUrl: String, contentType: String) {
        viewModelScope.launch {
            try {
                Log.d("ContentGenerator", "Starting content generation")
                val videoId = extractVideoId(youtubeUrl)
                Log.d("ContentGenerator", "Extracted video ID: $videoId")

                if (videoId.isEmpty()) {
                    _generatedContent.value = "Invalid YouTube URL"
                    return@launch
                }

                val videoDetails = RetrofitInstance.youtubeApi.getVideoDetails(
                    part = "snippet",
                    videoId = videoId,
                    apiKey = YOUR_YT_API_KEY
                )
                Log.d("ContentGenerator", "Fetched video details: $videoDetails")

                val videoTitle = videoDetails.items.firstOrNull()?.snippet?.title.orEmpty()
                val videoDescription = videoDetails.items.firstOrNull()?.snippet?.description.orEmpty()
                Log.d("ContentGenerator", "Video Title: $videoTitle")
                Log.d("ContentGenerator", "Video Description: $videoDescription")

                if (videoTitle.isEmpty() || videoDescription.isEmpty()) {
                    _generatedContent.value = "Unable to fetch video details"
                    return@launch
                }

                val prompt = "Write a short and engaging $contentType for a YouTube video titled '$videoTitle'. The video description is: '$videoDescription'."
                val jsonBody = JSONObject().apply {
                    put("prompt", prompt)
                }
                val requestBody = jsonBody.toString().toRequestBody("application/json".toMediaTypeOrNull())

                val cohereResponse = RetrofitInstance.cohereApi.generateContent(requestBody)
                Log.d("ContentGenerator", "Cohere Response: $cohereResponse")

                val generatedText = cohereResponse.generations.firstOrNull()?.text.orEmpty()
                Log.d("ContentGenerator", "Generated Text: $generatedText")

                _generatedContent.value = generatedText
            } catch (e: Exception) {
                Log.e("ContentGenerator", "Error generating content: ${e.message}", e)
                _generatedContent.value = "Error generating content: ${e.message}"
            }
        }
    }

    private fun extractVideoId(youtubeUrl: String): String {
        val videoIdRegex = Regex("(?:https?://)?(?:www\\.)?(?:youtube\\.com/watch\\?v=|youtu.be/)([^&?]+)")
        val matchResult = videoIdRegex.find(youtubeUrl)
        return matchResult?.groupValues?.get(1) ?: ""
    }
}
