package com.example.openvision.accessibility

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.graphics.PixelFormat
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import com.example.openvision.R
import java.util.Locale

class VisionAccessibilityService : AccessibilityService(), TextToSpeech.OnInitListener {

    private lateinit var textToSpeech: TextToSpeech
    private var isTTSInitialized = false
    private var overlayView: FrameLayout? = null
    private var windowManager: WindowManager? = null

    override fun onServiceConnected() {
        super.onServiceConnected()

        // Configurar serviço de acessibilidade
        val info = AccessibilityServiceInfo().apply {
            eventTypes = AccessibilityEvent.TYPES_ALL_MASK
            feedbackType = AccessibilityServiceInfo.FEEDBACK_ALL_MASK
            notificationTimeout = 100
            flags = AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS or
                    AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS
        }

        this.serviceInfo = info

        // Inicializar TTS
        textToSpeech = TextToSpeech(this, this)

        // Criar overlay flutuante
        setupFloatingOverlay()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech.setLanguage(Locale("pt", "BR"))
            isTTSInitialized = result != TextToSpeech.LANG_MISSING_DATA &&
                    result != TextToSpeech.LANG_NOT_SUPPORTED
        }
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        event?.let {
            // Ler conteúdo quando usuário focar em elementos
            if (isTTSInitialized && it.eventType == AccessibilityEvent.TYPE_VIEW_FOCUSED) {
                val text = it.text?.joinToString(" ") ?: ""
                if (text.isNotEmpty()) {
                    textToSpeech.speak(text, TextToSpeech.QUEUE_ADD, null, "accessibility")
                }
            }

            // Detectar mudanças de texto para leitura automática
            if (it.eventType == AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED) {
                val text = it.text?.joinToString(" ") ?: ""
                if (text.isNotEmpty() && text.length > 1) {
                    textToSpeech.speak("Texto alterado: $text", TextToSpeech.QUEUE_ADD, null, "text_change")
                }
            }
        }
    }

    private fun setupFloatingOverlay() {
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

        val layoutParams = WindowManager.LayoutParams().apply {
            type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY
            format = PixelFormat.TRANSLUCENT
            flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            width = WindowManager.LayoutParams.MATCH_PARENT
            height = WindowManager.LayoutParams.MATCH_PARENT
        }

        overlayView = FrameLayout(this).apply {
            setBackgroundColor(0x00000000) // Transparente
        }

        windowManager?.addView(overlayView, layoutParams)
    }

    fun speakText(text: String) {
        if (isTTSInitialized) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "manual_speech")
        }
    }

    override fun onInterrupt() {
        textToSpeech.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        textToSpeech.shutdown()
        overlayView?.let {
            windowManager?.removeView(it)
        }
    }
}