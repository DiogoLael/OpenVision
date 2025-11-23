package com.example.openvision.service

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.openvision.R

class FloatingTranslationService : Service() {

    private var floatingView: View? = null
    private var windowManager: WindowManager? = null

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()

        // Criar view flutuante
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

        floatingView = LayoutInflater.from(this).inflate(R.layout.floating_translator, null)

        val layoutParams = WindowManager.LayoutParams().apply {
            type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            format = PixelFormat.TRANSLUCENT
            flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
            gravity = Gravity.TOP or Gravity.START
            x = 0
            y = 100
        }

        windowManager?.addView(floatingView, layoutParams)

        setupFloatingView()
    }

    private fun setupFloatingView() {
        floatingView?.apply {
            val closeButton = findViewById<ImageView>(R.id.closeButton)
            val translateButton = findViewById<Button>(R.id.floatingTranslateBtn)
            val inputText = findViewById<EditText>(R.id.floatingInputText)
            val resultText = findViewById<TextView>(R.id.floatingResultText)

            closeButton.setOnClickListener {
                stopSelf()
            }

            translateButton.setOnClickListener {
                val text = inputText.text.toString()
                if (text.isNotEmpty()) {
                    // Simular tradução - integrar com API real depois
                    resultText.text = "📝 ${text.reversed()}" // Exemplo simples
                }
            }

            // Tornar a view arrastável
            setOnTouchListener(FloatingViewTouchListener(layoutParams, windowManager!!))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        floatingView?.let {
            windowManager?.removeView(it)
        }
    }
}