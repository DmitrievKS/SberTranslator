package com.kirdmt.sbertranslator.app.room

import com.kirdmt.sbertranslator.app.models.SavedWord
import io.reactivex.Flowable
import java.util.*
import kotlin.collections.ArrayList
import io.reactivex.Observable

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class SavedWordRepository(private val dao: SavedWordDao) {

    fun getSavedWords(): Flowable<List<SavedWord>> {
        return dao.getSavedWords()
    }

    suspend fun getCount(): Int {
        return dao.getCount()
    }

    suspend fun insert(word: SavedWord) {
        dao.insert(word)
    }

    suspend fun searchSavedWords(search: String): List<SavedWord> {
        return dao.searchSavedWords(search)
    }

    suspend fun getCountMatches(search: String): Int {
        return dao.getCountMatches(search)
    }

    suspend fun deleteAll()
    {
        return dao.deleteAll()
    }
}