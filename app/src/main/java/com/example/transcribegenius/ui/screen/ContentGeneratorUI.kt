package com.example.transcribegenius.ui.screen

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.transcribegenius.ContentGeneratorViewModel
import com.example.transcribegenius.R
import com.example.transcribegenius.ui.theme.Blue
import com.example.transcribegenius.ui.theme.LightBlue


@Composable
fun ContentGeneratorUI(viewModel: ContentGeneratorViewModel) {

    var youtubeUrl by remember { mutableStateOf(TextFieldValue("")) }
    var selectedContentType by remember { mutableStateOf("Select a Content Type") }
    val contentTypes = listOf("LinkedIn Post", "Twitter Tweet", "Blog Content","Academic Notes")
    var expanded by remember { mutableStateOf(false) }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Transcribe Genius",
            color = Color.Black,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 30.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = youtubeUrl,
            onValueChange = { youtubeUrl = it },
            placeholder = {
                Text(text = "Enter a Youtube Video Url")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Link, contentDescription = null)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
                .background(Color.White),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Blue,
                cursorColor = Blue
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
        ) {
            Text(text = selectedContentType,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true }
                    .background(colorResource(id = R.color.light_blue))
                    .padding(16.dp),
                color = Color.White)
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

        Spacer(modifier = Modifier.height(8.dp))

        ElevatedButton(
            onClick = {
                viewModel.generateContent(youtubeUrl.text, selectedContentType)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
                .clip(RoundedCornerShape(12.dp)),
            colors = ButtonDefaults.elevatedButtonColors(colorResource(id = R.color.blue))
        ) {
            Text(
                text = "Generate Content",
                modifier = Modifier,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        val generatedContent by viewModel.generatedContent.collectAsState()
        val context = LocalContext.current
        if (generatedContent.isNotEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
                    .height(300.dp),
                shape = RoundedCornerShape(12.dp),
                backgroundColor = LightBlue,
                elevation = 4.dp
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Blue)
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(onClick = {
                            val clipboard =
                                context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText("Generated Content", generatedContent)
                            clipboard.setPrimaryClip(clip)
                            Toast.makeText(
                                context,
                                "Content copied to clipboard",
                                Toast.LENGTH_SHORT
                            ).show()
                        }) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = "Copy",
                                tint = Color.White
                            )
                        }
                        IconButton(onClick = {
                            val intent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, generatedContent)
                                type = "text/plain"
                            }
                            context.startActivity(Intent.createChooser(intent, "Share Content"))
                        }) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Share",
                                tint = Color.White
                            )
                        }
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        val scrollState = rememberScrollState()
                        Text(
                            text = generatedContent,
                            modifier = Modifier
                                .verticalScroll(scrollState)
                                .padding(8.dp),
                            color = Color.White
                        )
                    }
                }
            }
        }
    }

}
