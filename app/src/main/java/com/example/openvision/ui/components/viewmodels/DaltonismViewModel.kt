package com.example.openvision.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.openvision.data.datastore.DaltonismPreference
import com.example.openvision.utils.DaltonismUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DaltonismViewModel(private val daltonismPreference: DaltonismPreference) : ViewModel() {

    private val _currentDaltonismType = MutableStateFlow(DaltonismUtil.DaltonismType.NONE)
    val currentDaltonismType: StateFlow<DaltonismUtil.DaltonismType> = _currentDaltonismType.asStateFlow()

    init {
        viewModelScope.launch {
            daltonismPreference.getDaltonismType().collect { type ->
                _currentDaltonismType.value = type
            }
        }
    }

    fun saveDaltonismType(type: DaltonismUtil.DaltonismType) {
        viewModelScope.launch {
            daltonismPreference.saveDaltonismType(type)
            _currentDaltonismType.value = type
        }
    }
}