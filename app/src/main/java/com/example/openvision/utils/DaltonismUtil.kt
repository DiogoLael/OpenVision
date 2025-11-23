package com.example.openvision.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.ColorFilter as ComposeColorFilter

object DaltonismUtil {

    enum class DaltonismType {
        NONE,
        PROTANOPIA,
        DEUTERANOPIA,
        TRITANOPIA,
        MONOCHROME
    }

    fun getColorMatrixForType(type: DaltonismType): ColorMatrix {
        return when (type) {
            DaltonismType.PROTANOPIA -> ColorMatrix(floatArrayOf(
                0.567f, 0.433f, 0f, 0f, 0f,
                0.558f, 0.442f, 0f, 0f, 0f,
                0f, 0.242f, 0.758f, 0f, 0f,
                0f, 0f, 0f, 1f, 0f
            ))
            DaltonismType.DEUTERANOPIA -> ColorMatrix(floatArrayOf(
                0.625f, 0.375f, 0f, 0f, 0f,
                0.7f, 0.3f, 0f, 0f, 0f,
                0f, 0.3f, 0.7f, 0f, 0f,
                0f, 0f, 0f, 1f, 0f
            ))
            DaltonismType.TRITANOPIA -> ColorMatrix(floatArrayOf(
                0.95f, 0.05f, 0f, 0f, 0f,
                0f, 0.433f, 0.567f, 0f, 0f,
                0f, 0.475f, 0.525f, 0f, 0f,
                0f, 0f, 0f, 1f, 0f
            ))
            DaltonismType.MONOCHROME -> ColorMatrix(floatArrayOf(
                0.299f, 0.587f, 0.114f, 0f, 0f,
                0.299f, 0.587f, 0.114f, 0f, 0f,
                0.299f, 0.587f, 0.114f, 0f, 0f,
                0f, 0f, 0f, 1f, 0f
            ))
            DaltonismType.NONE -> ColorMatrix()
        }
    }

    fun createColorFilter(daltonismType: DaltonismType): ComposeColorFilter? {
        return if (daltonismType == DaltonismType.NONE) null
        else ComposeColorFilter.colorMatrix(getColorMatrixForType(daltonismType))
    }

    fun getTypeDescription(type: DaltonismType): String = when (type) {
        DaltonismType.NONE -> "Filtro Desativado"
        DaltonismType.PROTANOPIA -> "Protanopia (Vermelho)"
        DaltonismType.DEUTERANOPIA -> "Deuteranopia (Verde)"
        DaltonismType.TRITANOPIA -> "Tritanopia (Azul)"
        DaltonismType.MONOCHROME -> "Monocromático"
    }

    fun getAccessibleColors(type: DaltonismType): List<Color> {
        return when (type) {
            DaltonismType.PROTANOPIA -> listOf(Color(0xFF0055A4), Color(0xFF00A48F), Color(0xFFFFC000))
            DaltonismType.DEUTERANOPIA -> listOf(Color(0xFF8A2BE2), Color(0xFF00CED1), Color(0xFFFF69B4))
            DaltonismType.TRITANOPIA -> listOf(Color(0xFFDC143C), Color(0xFF32CD32), Color(0xFFFFD700))
            else -> listOf(Color(0xFF6200EE), Color(0xFF03DAC6), Color(0xFFFF5252))
        }
    }
}