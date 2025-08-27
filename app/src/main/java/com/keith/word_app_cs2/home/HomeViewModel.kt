package com.keith.word_app_cs2.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(
//    private val repo: WordsRepo = WordsRepo.getInstace()
): ViewModel(){
//    private val _words = MutableStateFlow<List<Book>>(emptyList())
//    val books = _words.asStateFlow()
    init {
        getWords()
    }
    fun getWords(){
//        _words.value = repo.getWords
    }
}
