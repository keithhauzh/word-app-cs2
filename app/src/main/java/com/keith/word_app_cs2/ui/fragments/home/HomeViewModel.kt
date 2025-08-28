package com.keith.word_app_cs2.ui.fragments.home

import androidx.lifecycle.ViewModel
import com.keith.word_app_cs2.data.model.Word
import com.keith.word_app_cs2.data.repo.WordsRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(
    private val repo: WordsRepo = WordsRepo.getInstance()
): ViewModel(){
    private val _words = MutableStateFlow<List<Word>>(emptyList())
    val words = _words.asStateFlow()
    init {
        getWords()
    }
    fun getWords(){
        _words.value = repo.getAllWords()
    }
}
