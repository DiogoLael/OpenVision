package com.example.openvision.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.openvision.data.datastore.DaltonismPreference
import com.example.openvision.ui.components.ColorFilter
import com.example.openvision.ui.factories.createDaltonismViewModelFactory
import com.example.openvision.ui.viewmodels.DaltonismViewModel
import com.example.openvision.utils.DaltonismUtil
import kotlinx.coroutines.launch
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.clickable
import androidx.compose.foundation.background

@Composable
fun DaltonismScreen() {
    val context = LocalContext.current
    val daltonismPreference = remember { DaltonismPreference(context) }
    val viewModel: DaltonismViewModel = viewModel(
        factory = createDaltonismViewModelFactory(daltonismPreference)
    )

    val currentType by viewModel.currentDaltonismType.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    ColorFilter(daltonismType = currentType, modifier = Modifier.fillMaxSize()) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Configurações de Acessibilidade",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectableGroup(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    DaltonismUtil.DaltonismType.values().forEach { type ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    coroutineScope.launch {
                                        viewModel.saveDaltonismType(type)
                                    }
                                }
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = currentType == type,
                                onClick = {
                                    coroutineScope.launch {
                                        viewModel.saveDaltonismType(type)
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = DaltonismUtil.getTypeDescription(type),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                ColorPreview(currentType)
            }
        }
    }
}

@Composable
fun ColorPreview(daltonismType: DaltonismUtil.DaltonismType) {
    val colors = DaltonismUtil.getAccessibleColors(daltonismType)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Preview de Cores",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            colors.forEach { color ->
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(color)
                )
            }
        }

        Text(
            text = "Filtro: ${DaltonismUtil.getTypeDescription(daltonismType)}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 12.dp)
        )
    }
}