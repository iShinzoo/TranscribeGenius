package com.example.transcribegenius.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.transcribegenius.ContentViewModel


@Composable
fun ContentGeneratorScreen(viewModel: ContentViewModel) {
    var url by remember { mutableStateOf("") }
    var videoId by remember { mutableStateOf("") }


    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = url,
            onValueChange = {
                url = it
                videoId = it.split("/").last().split("?").first()
            },
            label = { Text("Enter YouTube URL") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Uri),
            enabled = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (videoId.isNotEmpty()) {
                viewModel.fetchTranscript(videoId)
            } else {
                // Handle invalid URL input
            }
        }) {
            Text("Fetch Transcript")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val prompt = "Generate a LinkedIn post based on the following transcript: ${viewModel.transcript}"
            viewModel.generateContent(prompt)
        }) {
            Text("Generate Content")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(viewModel.transcript)
        Spacer(modifier = Modifier.height(16.dp))
        Text(viewModel.generatedContent)
    }
}
