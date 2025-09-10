package com.keith.word_app_cs2.ui.fragments.manageWord

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.keith.word_app_cs2.data.model.Word
import com.keith.word_app_cs2.data.repo.WordsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import com.keith.word_app_cs2.MyApp

class AddWordViewModel(
    private val repo: WordsRepo = WordsRepo.getInstance()
//    private val repo: WordsRepo
) : ViewModel() {
    private val _finish = MutableSharedFlow<Unit>()
    val finish = _finish.asSharedFlow()
    private val _error = MutableSharedFlow<String>()
    val error = _error.asSharedFlow()

    fun addWord(title: String, meaning: String, synonyms: String, details: String) {
        try {
            val word = Word(
                title = title,
                meaning = meaning,
                synonyms = synonyms,
                details = details,
                createdAt = System.currentTimeMillis()
            )
            require(title.isNotBlank()) {"Title cannot be blank"}
            require(meaning.isNotBlank()) {"Meaning cannot be blank"}
            require(synonyms.isNotBlank()) {"Synonyms cannot be blank"}
            require(details.isNotBlank()) {"Details cannot be blank"}
            viewModelScope.launch(Dispatchers.IO) {
                repo.add(word)
                _finish.emit(Unit)
            }
        } catch (e: Exception) {
            viewModelScope.launch {
                _error.emit(e.message.toString())
            }
        }
    }

    companion object {
//        val Factory: ViewModelProvider.Factory = viewModelFactory {
//            initializer {
//                val myRepository = (this[APPLICATION_KEY] as MyApp).repo
//                AddWordViewModel(myRepository)
//            }
//        }
    }
}