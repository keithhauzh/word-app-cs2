package com.keith.word_app_cs2.ui.fragments.manageWord

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keith.word_app_cs2.data.model.Word
import com.keith.word_app_cs2.data.repo.WordsRepo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EditWordViewModel(
    private val repo: WordsRepo = WordsRepo.getInstance()
): ViewModel() {
    private val _finish = MutableSharedFlow<Unit>()
    val finish = _finish.asSharedFlow()
    private val _error = MutableSharedFlow<String>()
    val error = _error.asSharedFlow()
    private var _word = MutableStateFlow(Word(
        title = "",
        meaning = "",
        synonyms = "",
        details = "",
        completed = false,
        createdAt = System.currentTimeMillis()
    ))
    val word = _word.asStateFlow()
    fun getWord(id: Int) {
        repo.getWordById(id)?.let {
            _word.value = it
        }
    }
    fun updateWord(title: String, meaning: String, synonyms: String, details: String) {
        try {
            require(title.isNotBlank()) {"Title cannot be blank"}
            require(meaning.isNotBlank()) {"Meaning cannot be blank"}
            require(synonyms.isNotBlank()) {"Synonyms cannot be blank"}
            require(details.isNotBlank()) {"Details cannot be blank"}
            _word.value.id?.let {
                repo.updateWord(
                    id = it,
                    word = _word.value.copy(title = title, meaning = meaning, synonyms = synonyms, details = details
                    )
                )
            }
            viewModelScope.launch { _finish.emit(Unit) }
        } catch (e: Exception) {
            viewModelScope.launch { _error.emit(e.message.toString()) }
        }
    }
}