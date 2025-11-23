package com.example.openvision.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import android.speech.tts.TextToSpeech

@Composable
fun TranslationScreen(
    textToSpeech: TextToSpeech,
    isTTSInitialized: Boolean
) {
    var inputText by remember { mutableStateOf("") }
    var translatedText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Tradutor HandTalk-like",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            label = { Text("Digite o texto para traduzir") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                translatedText = "Traduzido: $inputText"
                if (isTTSInitialized) {
                    textToSpeech.speak(translatedText, TextToSpeech.QUEUE_FLUSH, null, null)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Traduzir e Falar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (translatedText.isNotEmpty()) {
            Text(
                text = translatedText,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}