package com.keith.word_app_cs2.data.model

data class Word(
    val id: Int? = null,
    val title: String,
    val meaning: String,
    val synonyms: String,
    val details: String,
    val completed: Boolean = false,
    val createdAt: Long
)