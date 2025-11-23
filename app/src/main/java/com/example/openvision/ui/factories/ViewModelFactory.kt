package com.example.openvision.ui.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.openvision.data.datastore.DaltonismPreference
import com.example.openvision.ui.viewmodels.DaltonismViewModel

fun createDaltonismViewModelFactory(daltonismPreference: DaltonismPreference): ViewModelProvider.Factory {
    return object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DaltonismViewModel::class.java)) {
                return DaltonismViewModel(daltonismPreference) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}