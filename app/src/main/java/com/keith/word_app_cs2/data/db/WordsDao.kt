package com.keith.word_app_cs2.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import com.keith.word_app_cs2.data.model.Word

@Dao
interface WordsDao {
    @Query("SELECT * FROM Word")
    fun getAllWords(): Flow<List<Word>>

    @Query("SELECT * FROM word WHERE id = :id")
    fun getWordById(id: Int): Word?

    @Insert
    fun addWord(product: Word)

    @Update
    fun update(product: Word)

    @Query("UPDATE Word SET completed = NOT completed WHERE id=:id")
    fun isCompleted(id: Int)

    @Query("DELETE FROM word WHERE id = :id")
    fun delete(id: Int)
}
