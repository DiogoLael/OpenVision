package com.example.openvision.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.openvision.utils.DaltonismUtil

@Composable
fun ColorFilter(
    daltonismType: DaltonismUtil.DaltonismType,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    // At the moment, this is a wrapper composable that can be extended later
    // to apply RenderEffect or other color transforms. Kept minimal to avoid
    // API/version issues and to remain fully compilable.
    Box(modifier = modifier) {
        content()
    }
}
