package com.keith.word_app_cs2.ui.fragments.manageWord

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keith.word_app_cs2.data.model.Word
import com.keith.word_app_cs2.data.repo.WordsRepo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class AddWordViewModel(
    private val repo: WordsRepo = WordsRepo.getInstance()
) : ViewModel() {
    private val _finish = MutableSharedFlow<Unit>()
    val finish = _finish.asSharedFlow()

    fun addWord(title: String, meaning: String, synonyms: String, details: String) {
        val word = Word(
            title = title,
            meaning = meaning,
            synonyms = synonyms,
            details = details,
            createdAt = System.currentTimeMillis()
        )
        repo.add(word)
        viewModelScope.launch {
            _finish.emit(Unit)
        }
    }
}