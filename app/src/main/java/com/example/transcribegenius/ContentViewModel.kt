package com.example.transcribegenius


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.transcribegenius.data.OpenAiRequest
import com.example.transcribegenius.network.RetrofitInstance
import com.example.transcribegenius.util.Authorization
import com.example.transcribegenius.util.X_RapidAPI_Host
import com.example.transcribegenius.util.X_RapidAPI_Key
import kotlinx.coroutines.launch

class ContentViewModel : ViewModel() {

    private val apiServiceTranscript = RetrofitInstance.transcriptApiResponse()
    private val apiServiceOpenAi = RetrofitInstance.OpenAiApiResponse()

    var transcript by mutableStateOf("")
    var generatedContent by mutableStateOf("")

    fun fetchTranscript(videoId: String) {
        viewModelScope.launch {
            try {
                val response = apiServiceTranscript.getTranscript(videoId, X_RapidAPI_Key, X_RapidAPI_Host)
                transcript = response.text
            } catch (e: Exception) {
                transcript = "Error fetching transcript: ${e.message}. Please check the log for more details."
            }
        }
    }

    fun generateContent(prompt: String) {
        viewModelScope.launch {
            try {
                val request = OpenAiRequest(prompt = prompt, max_tokens = 150)
                val response = apiServiceOpenAi.generateContent(Authorization, request)
                generatedContent = response.choices.first().text
            } catch (e: Exception) {
                generatedContent = "Error generating content: ${e.message}. Please check the log for more details."
            }
        }
    }
}
