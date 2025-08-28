package com.keith.word_app_cs2.manageWord

import androidx.lifecycle.ViewModel
import com.keith.word_app_cs2.data.repo.WordsRepo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow

abstract class BaseManageWordViewModel(
    protected val repo: WordsRepo = WordsRepo.getInstance()
) : ViewModel() {
    val title = MutableStateFlow("")
    val meaning = MutableStateFlow("")
    val synonyms = MutableStateFlow("")
    val details = MutableStateFlow("")
    val completed = MutableStateFlow(false)
    val createdAt = MutableStateFlow(0L)
    protected val _finish = MutableSharedFlow<Unit>()
    val finish = _finish.asSharedFlow()

    abstract fun submit()
}