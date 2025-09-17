package com.keith.word_app_cs2.data.repo

import com.keith.word_app_cs2.data.db.WordsDao
import com.keith.word_app_cs2.data.model.Word
import kotlinx.coroutines.flow.Flow

class WordsRepo(
    private val dao: WordsDao
) {
    fun add(word: Word) {
        dao.addWord(word)
    }
    fun getAllWords() : Flow<List<Word>> {
        return dao.getAllWords()
    }

    fun getWordById(id: Int): Word? {
        return dao.getWordById(id)
    }
    fun updateWord(word: Word) {
        dao.update(word)
    }

    fun isCompleted(id: Int) {
        dao.isCompleted(id)
    }

    fun deleteWord(id: Int) {
        dao.delete(id)
    }
}