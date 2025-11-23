package com.example.openvision.utils

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.provider.Settings
import android.view.accessibility.AccessibilityManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorMatrix
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object AccessibilityUtils {

    private val _currentFilter = MutableStateFlow(DaltonismType.NONE)
    val currentFilter: StateFlow<DaltonismType> = _currentFilter

    fun toggleColorFilter(context: Context) {
        val current = _currentFilter.value
        val next = when (current) {
            DaltonismType.NONE -> DaltonismType.PROTANOPIA
            DaltonismType.PROTANOPIA -> DaltontonismType.DEUTERANOPIA
            DaltonismType.DEUTERANOPIA -> DaltonismType.TRITANOPIA
            DaltonismType.TRITANOPIA -> DaltonismType.MONOCHROME
            DaltonismType.MONOCHROME -> DaltonismType.NONE
        }
        _currentFilter.value = next
    }

    fun isAccessibilityServiceEnabled(context: Context): Boolean {
        val accessibilityManager = context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
        return accessibilityManager.isEnabled
    }

    fun isTalkBackEnabled(context: Context): Boolean {
        return Settings.Secure.getInt(
            context.contentResolver,
            Settings.Secure.ACCESSIBILITY_ENABLED
        ) == 1
    }

    fun applyHighContrast(colors: List<Color>): List<Color> {
        return colors.map { color ->
            val contrastFactor = 1.5f
            Color(
                red = (color.red * contrastFactor).coerceIn(0f, 1f),
                green = (color.green * contrastFactor).coerceIn(0f, 1f),
                blue = (color.blue * contrastFactor).coerceIn(0f, 1f),
                alpha = color.alpha
            )
        }
    }

    fun simulateColorBlindness(type: DaltonismType, color: Color): Color {
        return when (type) {
            DaltonismType.PROTANOPIA -> {
                // Simulação de protanopia
                Color(
                    red = color.red * 0.567f + color.green * 0.433f,
                    green = color.red * 0.558f + color.green * 0.442f,
                    blue = color.green * 0.242f + color.blue * 0.758f,
                    alpha = color.alpha
                )
            }
            DaltonismType.DEUTERANOPIA -> {
                // Simulação de deuteranopia
                Color(
                    red = color.red * 0.625f + color.green * 0.375f,
                    green = color.red * 0.7f + color.green * 0.3f,
                    blue = color.green * 0.3f + color.blue * 0.7f,
                    alpha = color.alpha
                )
            }
            DaltonismType.TRITANOPIA -> {
                // Simulação de tritanopia
                Color(
                    red = color.red * 0.95f + color.green * 0.05f,
                    green = color.green * 0.433f + color.blue * 0.567f,
                    blue = color.green * 0.475f + color.blue * 0.525f,
                    alpha = color.alpha
                )
            }
            DaltonismType.MONOCHROME -> {
                val gray = (color.red * 0.299f + color.green * 0.587f + color.blue * 0.114f)
                Color(gray, gray, gray, color.alpha)
            }
            else -> color
        }
    }
}