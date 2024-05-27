package com.example.transcribegenius


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
                val videoId = extractVideoId(youtubeUrl)
                val videoDetails = RetrofitInstance.youtubeApi.getVideoDetails(
                    part = "snippet",
                    videoId = videoId,
                    apiKey = "AIzaSyACERF2IDtOT33rkOa63LVn4o-ypZE8jQ0"
                )
                val videoTitle = videoDetails.items.firstOrNull()?.snippet?.title.orEmpty()
                val videoDescription =
                    videoDetails.items.firstOrNull()?.snippet?.description.orEmpty()

                val prompt =
                    "Generate a $contentType for a video titled '$videoTitle' with the description '$videoDescription'."
                val openAiRequest = OpenAiRequest(
                    model = "text-davinci-003", prompt = prompt, max_tokens = 150
                )
                val openAiResponse =
                    RetrofitInstance.openAiApi.generateContent(YOUR_OPENAI_API_KEY, openAiRequest)
                val generatedText = openAiResponse.choices.firstOrNull()?.text.orEmpty()
                _generatedContent.value = generatedText
            } catch (e: Exception) {
                _generatedContent.value = "Error generating content: ${e.message}"
            }
        }
    }

    private fun extractVideoId(youtubeUrl: String): String {
        val videoIdRegex =
            Regex("(?:https?://)?(?:www\\.)?(?:youtube\\.com/watch\\?v=|youtu.be/)([^&?]+)")
        val matchResult = videoIdRegex.find(youtubeUrl)
        return matchResult?.groupValues?.get(1) ?: ""
    }

}