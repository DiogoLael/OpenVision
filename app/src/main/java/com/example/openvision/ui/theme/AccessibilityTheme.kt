package com.example.openvision.ui.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.openvision.data.datastore.DaltonismPreference
import com.example.openvision.ui.viewmodels.DaltonismViewModel
import com.example.openvision.utils.DaltonismUtil
import androidx.compose.ui.graphics.ColorFilter as ComposeColorFilter

@Composable
fun AccessibilityTheme(content: @Composable () -> Unit) {
    val context = LocalContext.current
    val daltonismPreference = remember { DaltonismPreference(context) }

    // ViewModel factory — cria o ViewModel com a dependência correta
    val viewModel: DaltonismViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return DaltonismViewModel(daltonismPreference) as T
            }
        }
    )

    // Observa o tipo de daltonismo salvo
    val daltonismType by viewModel.currentDaltonismType.collectAsState()

    // Se você criou createColorFilter em DaltonismUtil, usa aqui (pode ser null)
    val colorFilter: ComposeColorFilter? = DaltonismUtil.createColorFilter(daltonismType)

    // Wrapper que permite aplicar efeitos gráficos globais se necessário.
    // Mantemos a compositingStrategy para compatibilidade e futura aplicação do ColorFilter.
    Box(
        modifier = Modifier.graphicsLayer {
            compositingStrategy = CompositingStrategy.Offscreen
            // Se quiser aplicar um RenderEffect global (API >= 31), pode adicionar aqui.
            // Não aplicamos diretamente para evitar problemas de compatibilidade.
        }
    ) {
        // Conteúdo do app renderizado normalmente (fácil de estender depois)
        content()
    }
}
