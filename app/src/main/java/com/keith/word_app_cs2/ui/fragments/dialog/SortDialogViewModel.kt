package com.keith.word_app_cs2.ui.fragments.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SortDialogViewModel: ViewModel() {
    private val _finish = MutableSharedFlow< Pair<String, String>>()
    val finish = _finish.asSharedFlow()
    private val _error = MutableSharedFlow<String>()
    val error = _error.asSharedFlow()
    fun onDone(sortOrderId: Int, sortById: Int, ascId: Int, descId: Int, titleId:Int, dateId:Int) {
        try {
            require(sortOrderId != -1) {"Please select sort order"}
            require(sortById != -1) {"Please select sort by"}
            val sortOrder = when(sortOrderId) {
                ascId -> "ascending"
                descId -> "descending"
                else -> "ascending"
            }
            val sortBy = when(sortById) {
                titleId -> "title"
                dateId -> "date"
                else -> "title"
            }
            viewModelScope.launch { _finish.emit(Pair(sortOrder, sortBy))}
        } catch (e: Exception) {
        viewModelScope.launch { _error.emit(e.message.toString())}
        }
    }
}