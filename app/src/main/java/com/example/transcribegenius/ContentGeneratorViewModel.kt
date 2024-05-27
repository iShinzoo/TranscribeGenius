package com.example.transcribegenius

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.transcribegenius.data.OpenAiRequest
import com.example.transcribegenius.network.RetrofitInstance
import com.example.transcribegenius.util.YOUR_OPENAI_API_KEY
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ContentGeneratorViewModel : ViewModel() {
    private val _generatedContent = MutableStateFlow("")
    val generatedContent: StateFlow<String> = _generatedContent

    fun generateContent(youtubeUrl: String, contentType: String) {
        viewModelScope.launch {
            try {
                Log.d("ContentGenerator", "Starting content generation")
                val videoId = extractVideoId(youtubeUrl)
                Log.d("ContentGenerator", "Extracted video ID: $videoId")

                val videoDetails = RetrofitInstance.youtubeApi.getVideoDetails(
                    part = "snippet",
                    videoId = videoId,
                    apiKey = "AIzaSyACERF2IDtOT33rkOa63LVn4o-ypZE8jQ0"
                )
                Log.d("ContentGenerator", "Fetched video details: $videoDetails")

                val videoTitle = videoDetails.items.firstOrNull()?.snippet?.title.orEmpty()
                val videoDescription = videoDetails.items.firstOrNull()?.snippet?.description.orEmpty()
                Log.d("ContentGenerator", "Video Title: $videoTitle")
                Log.d("ContentGenerator", "Video Description: $videoDescription")

                val prompt = "Generate a $contentType for a video titled '$videoTitle' with the description '$videoDescription'."
                val openAiRequest = OpenAiRequest(
                    model = "text-davinci-003",
                    prompt = prompt,
                    max_tokens = 150
                )
                Log.d("ContentGenerator", "OpenAI Request: $openAiRequest")

                val openAiResponse = RetrofitInstance.openAiApi.generateContent(YOUR_OPENAI_API_KEY, openAiRequest)
                Log.d("ContentGenerator", "OpenAI Response: $openAiResponse")

                val generatedText = openAiResponse.choices.firstOrNull()?.text.orEmpty()
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
