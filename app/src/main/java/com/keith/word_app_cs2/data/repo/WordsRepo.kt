package com.keith.word_app_cs2.data.repo

import com.keith.word_app_cs2.data.model.Word

class WordsRepo private constructor(){
    val map = mutableMapOf<Int, Word>()
    var counter = 0
    fun add(word: Word) {
        map[++counter] = word.copy(id = counter)
    }
    fun getWordById(id: Int): Word? {
        return map[id]
    }
    fun getAllWords() = map.values.toList()
    fun deleteWord(id: Int) {
        map.remove(id)
    }
    fun updateWord(id: Int, word: Word) {
        map[id] = word
    }

    fun isCompleted(id: Int) {
        map[id]?.let { word ->
            map[id] = word.copy(completed = true)
        }
    }

    companion object {
        private var instance: WordsRepo? = null
        fun getInstance(): WordsRepo {
            if(instance == null) {
                instance = WordsRepo()
            }
            return instance!!
        }
    }
}