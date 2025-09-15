package com.keith.word_app_cs2.ui.fragments.completedWord

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.keith.word_app_cs2.data.model.Word
import com.keith.word_app_cs2.data.repo.WordsRepo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import com.keith.word_app_cs2.MyApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CompletedWordViewModel(
    private val repo: WordsRepo
) : ViewModel() {
    private val _words = MutableStateFlow<List<Word>>(emptyList())
    val words = _words.asStateFlow()
    private val _finish = MutableSharedFlow<Unit>()
    val finish: SharedFlow<Unit> = _finish
    fun getWords(){
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllWords().collect { words ->
                _words.value = words.filter { it.completed == true }
            }
        }
    }
    fun sortWords(sortOrder: String, sortBy: String) {
        val sorted = when(sortBy) {
            "title" -> if(sortOrder == "ascending") {_words.value.sortedBy { it.title }
            } else {
                _words.value.sortedByDescending { it.title }
            }
            "date" -> if(sortOrder == "ascending") {_words.value.sortedBy { it.createdAt }
            } else {
                _words.value.sortedByDescending { it.createdAt }
            }
            else -> _words.value
        }
        _words.value = sorted
    }
    fun search(search: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllWords().collect { words ->
                _words.value = words
                    .filter {it.completed == true}
                    .filter { it.title.contains(search) }
            }
        }
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val myRepository = (this[APPLICATION_KEY] as MyApp).repo
                CompletedWordViewModel(myRepository)
            }
        }
    }
}