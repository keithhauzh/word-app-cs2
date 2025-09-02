package com.keith.word_app_cs2.ui.fragments.manageWord

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keith.word_app_cs2.data.model.Word
import com.keith.word_app_cs2.data.repo.WordsRepo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WordDetailsViewModel(
    private val repo: WordsRepo = WordsRepo.getInstance(),
) : ViewModel() {
    private val _word = MutableStateFlow<Word?>(null)
    val word: StateFlow<Word?> = _word
    private val _finish = MutableSharedFlow<Unit>()
    val finish: SharedFlow<Unit> = _finish
     fun loadWord(id:Int){
         repo.getWordById(id)?.let{
             _word.value = it
         }
    }
    fun deleteWord(id: Int) {
        repo.deleteWord(id)
        viewModelScope.launch {
            _finish.emit(Unit)
        }
    }

    fun isDone(id: Int) {
        repo.isCompleted(id)
        _word.value = repo.getWordById(id)
//        viewModelScope.launch {
//            _finish.emit(Unit)
//        }
    }
}