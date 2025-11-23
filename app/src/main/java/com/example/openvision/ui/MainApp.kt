package com.example.openvision.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import android.content.Intent
import android.provider.Settings
import android.speech.tts.TextToSpeech
import com.example.openvision.service.FloatingTranslationService
import com.example.openvision.utils.AccessibilityUtils

@Composable
fun MainApp(
    textToSpeech: TextToSpeech,
    isTTSInitialized: Boolean
) {
    var currentScreen by remember { mutableStateOf("home") }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("OpenVision - Acessibilidade") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors()
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Início") },
                    label = { Text("Início") },
                    selected = currentScreen == "home",
                    onClick = { currentScreen = "home" }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Visibility, contentDescription = "Visão") },
                    label = { Text("Visão") },
                    selected = currentScreen == "vision",
                    onClick = { currentScreen = "vision" }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Translate, contentDescription = "Tradução") },
                    label = { Text("Tradução") },
                    selected = currentScreen == "translation",
                    onClick = { currentScreen = "translation" }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Accessibility, contentDescription = "Acessibilidade") },
                    label = { Text("Acessibilidade") },
                    selected = currentScreen == "accessibility",
                    onClick = { currentScreen = "accessibility" }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val intent = Intent(context, FloatingTranslationService::class.java)
                    context.startService(intent)
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Tradução Flutuante")
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (currentScreen) {
                "home" -> HomeScreen(textToSpeech, isTTSInitialized)
                "vision" -> VisionScreen()
                "translation" -> TranslationScreen(textToSpeech, isTTSInitialized)
                "accessibility" -> AccessibilityScreen()
                else -> HomeScreen(textToSpeech, isTTSInitialized)
            }
        }
    }
}

@Composable
fun HomeScreen(textToSpeech: TextToSpeech, isTTSInitialized: Boolean) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Cartão de Boas-vindas
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    "Bem-vindo ao OpenVision",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    "Seu assistente completo de acessibilidade digital",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        // Ações Rápidas
        Text("Ações Rápidas", style = MaterialTheme.typography.titleMedium)

        // Grid de Ações
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            QuickActionCard(
                title = "Filtro Daltonismo",
                icon = Icons.Default.Visibility,
                onClick = { /* Navegar para tela de visão */ }
            )
            QuickActionCard(
                title = "Tradutor",
                icon = Icons.Default.Translate,
                onClick = {
                    val intent = Intent(context, FloatingTranslationService::class.java)
                    context.startService(intent)
                }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            QuickActionCard(
                title = "Leitor de Tela",
                icon = Icons.Default.AudioFile,
                onClick = {
                    val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                    context.startActivity(intent)
                }
            )
            QuickActionCard(
                title = "Configurações",
                icon = Icons.Default.Settings,
                onClick = { /* Navegar para configurações */ }
            )
        }

        // Estatísticas de Uso
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Estatísticas", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text("• Filtros aplicados: 12 vezes")
                Text("• Textos traduzidos: 8 vezes")
                Text("• Leituras de tela: 25 vezes")
            }
        }

        // Teste de Acessibilidade
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Teste seus recursos", style = MaterialTheme.typography.titleMedium)

                Button(
                    onClick = {
                        if (isTTSInitialized) {
                            textToSpeech.speak(
                                "OpenVision - Teste de leitura de tela funcionando perfeitamente",
                                TextToSpeech.QUEUE_FLUSH,
                                null,
                                "test"
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Testar Leitor de Voz")
                }

                Button(
                    onClick = {
                        AccessibilityUtils.toggleColorFilter(context)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Testar Filtro de Cores")
                }
            }
        }
    }
}

@Composable
fun QuickActionCard(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .weight(1f)
            .aspectRatio(1f),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(icon, contentDescription = title, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(title, style = MaterialTheme.typography.labelMedium, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        }
    }
}

@Composable
fun VisionScreen() {
    DaltonismScreen() // Sua tela existente de daltonismo
}

@Composable
fun AccessibilityScreen() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Configurações de Acessibilidade", style = MaterialTheme.typography.headlineMedium)

        // Controle de TalkBack
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Leitor de Tela (TalkBack)", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Ative o leitor de tela do sistema para navegação por voz")
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = {
                        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                        context.startActivity(intent)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Configurar TalkBack")
                }
            }
        }

        // Configurações de Display
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Configurações de Display", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))

                // Tamanho da Fonte
                Text("Tamanho da Fonte", style = MaterialTheme.typography.bodyMedium)
                Slider(
                    value = 0.5f,
                    onValueChange = { /* Implementar */ },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Contraste
                Text("Contraste", style = MaterialTheme.typography.bodyMedium)
                Slider(
                    value = 0.5f,
                    onValueChange = { /* Implementar */ },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // Atalhos Rápidos
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Atalhos Rápidos", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))

                val shortcuts = listOf(
                    "Volume + + Volume - : Ativar/Desativar",
                    "Toque Triplo : Ampliar tela",
                    "Toque Duplo com 2 dedos : Pausar/Retomar TalkBack"
                )

                shortcuts.forEach { shortcut ->
                    Text("• $shortcut", modifier = Modifier.padding(vertical = 4.dp))
                }
            }
        }
    }
}