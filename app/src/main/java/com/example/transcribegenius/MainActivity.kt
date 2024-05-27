package com.example.transcribegenius

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.transcribegenius.ui.screen.ContentGeneratorUI
import com.example.transcribegenius.ui.theme.TranscribeGeniusTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<ContentGeneratorViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TranscribeGeniusTheme {

                Surface(modifier = Modifier.fillMaxSize()) {

                    ContentGeneratorUI(viewModel)
                }
            }
        }
    }
}
