package com.keith.word_app_cs2.ui.fragments.manageWord

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SortDialogViewModel: ViewModel() {
    private val _error = MutableSharedFlow<String>()
    val error = _error.asSharedFlow()

    fun showValidation(orderId: Int, byId: Int) {
        try {
            require(orderId != -1) {"Please select sort order"}
            require(byId != -1) {"Please select sort by"}
        } catch (e: Exception) {
        viewModelScope.launch { _error.emit(e.message.toString())}
        }
    }
}