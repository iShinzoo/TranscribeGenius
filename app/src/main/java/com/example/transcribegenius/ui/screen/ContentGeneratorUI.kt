package com.example.transcribegenius.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.transcribegenius.ContentGeneratorViewModel


@Composable
fun ContentGeneratorUI(viewModel: ContentGeneratorViewModel) {
    var youtubeUrl by remember { mutableStateOf(TextFieldValue("")) }
    var selectedContentType by remember { mutableStateOf("LinkedIn Post") }
    val contentTypes = listOf("LinkedIn Post", "Twitter Tweet", "Blog Post")
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(value = youtubeUrl,
            onValueChange = { youtubeUrl = it },
            label = { Text("YouTube Video URL") })

        Spacer(modifier = Modifier.height(16.dp))

        Box {
            Text(text = selectedContentType,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true }
                    .background(Color.LightGray)
                    .padding(16.dp))
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                contentTypes.forEach { contentType ->
                    DropdownMenuItem(onClick = {
                        selectedContentType = contentType
                        expanded = false
                    }) {
                        Text(text = contentType)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            viewModel.generateContent(youtubeUrl.text, selectedContentType)
        }) {
            Text("Generate Content", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        val generatedContent by viewModel.generatedContent.collectAsState()
        Text(text = generatedContent)
    }
}
