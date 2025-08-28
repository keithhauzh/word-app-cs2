package com.keith.word_app_cs2.ui.fragments.manageWord

import androidx.lifecycle.viewModelScope
import com.keith.word_app_cs2.data.model.Word
import kotlinx.coroutines.launch

class AddWordViewModel : BaseManageWordViewModel() {
    override fun submit() {
        val word = Word(
            title = title.value,
            meaning = meaning.value,
            synonyms = synonyms.value,
            details = details.value,
            completed = completed.value,
            createdAt = createdAt.value
        )
        repo.add(word)
        viewModelScope.launch{
            _finish.emit(Unit)
        }

    }
}