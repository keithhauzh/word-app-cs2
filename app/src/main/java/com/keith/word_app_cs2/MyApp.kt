package com.keith.word_app_cs2

import android.app.Application
import androidx.room.Room
import com.google.android.material.color.DynamicColors
import com.keith.word_app_cs2.data.db.MyDatabase
import com.keith.word_app_cs2.data.repo.WordsRepo

class MyApp: Application() {
    lateinit var repo: WordsRepo
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)

        val db = Room.databaseBuilder(
            this,
            MyDatabase::class.java,
            MyDatabase.NAME
        ).build()
        repo = WordsRepo(db.getWordsDao())
    }
}