package com.keith.word_app_cs2.ui.fragments.home

import androidx.lifecycle.ViewModel
import com.keith.word_app_cs2.data.model.Word
import com.keith.word_app_cs2.data.repo.WordsRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.collections.filter

class HomeViewModel(
    private val repo: WordsRepo = WordsRepo.getInstance()
): ViewModel(){
    private val _words = MutableStateFlow<List<Word>>(emptyList())
    val words = _words.asStateFlow()
    fun getWords(){
        _words.value = repo.getAllWords().filter {!it.completed}
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
        _words.value = repo.getAllWords().filter {!it.completed}.filter { it.title.contains(search) }

    }
}
