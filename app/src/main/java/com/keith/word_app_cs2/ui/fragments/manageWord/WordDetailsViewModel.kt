package com.keith.word_app_cs2.ui.fragments.manageWord

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.keith.word_app_cs2.data.model.Word
import com.keith.word_app_cs2.data.repo.WordsRepo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import com.keith.word_app_cs2.MyApp
import kotlinx.coroutines.Dispatchers

class WordDetailsViewModel(
    private val repo: WordsRepo
) : ViewModel() {
    private val _word = MutableStateFlow<Word?>(null)
    val word: StateFlow<Word?> = _word
    private val _finish = MutableSharedFlow<Unit>()
    val finish: SharedFlow<Unit> = _finish
     fun loadWord(id:Int){
         viewModelScope.launch(Dispatchers.IO) {
             repo.getWordById(id)?.let{
                 _word.value = it
             }
         }
    }
    fun deleteWord(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteWord(id)
            _finish.emit(Unit)
        }
    }
    fun isDone(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.isCompleted(id)
            _word.value = repo.getWordById(id)
        }
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val myRepository = (this[APPLICATION_KEY] as MyApp).repo
                WordDetailsViewModel(myRepository)
            }
        }
    }
}