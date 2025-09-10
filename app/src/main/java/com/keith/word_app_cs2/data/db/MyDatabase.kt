package com.keith.word_app_cs2.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.keith.word_app_cs2.data.model.Word

@Database(
    entities = [Word::class],
    version = 1)
abstract class MyDatabase: RoomDatabase() {
    abstract fun getWordsDao(): WordsDao
    companion object {
        const val NAME = "my_database"
    }
}
