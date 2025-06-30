package com.example.associate.training.compose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class ComposeAViewModel : ViewModel() {
    private val _infoFlow = MutableSharedFlow<String>(replay = 0)
    val infoFlow: Flow<String> = _infoFlow

    suspend fun sendEvent() {
        _infoFlow.emit("I'm ComposeAViewModel")
    }

    fun sendEventOutside() {
        viewModelScope.launch {
            sendEvent()
        }
    }
}