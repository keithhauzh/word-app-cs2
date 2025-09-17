package com.keith.word_app_cs2.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Word(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val meaning: String,
    val synonyms: String,
    val details: String,
    val completed: Boolean = false,
    val createdAt: Long
)