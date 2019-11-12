package com.kirdmt.sbertranslator.app.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kirdmt.sbertranslator.app.models.SavedWord
import io.reactivex.Flowable

@Dao
interface SavedWordDao {

    @Query("SELECT * from saved_words_table ORDER BY id DESC")
    fun getSavedWords(): Flowable<List<SavedWord>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: SavedWord)

    @Query("DELETE FROM saved_words_table")
    suspend fun deleteAll()

    @Query("SELECT COUNT(id) FROM saved_words_table")
    suspend fun getCount(): Int

    @Query("SELECT * from saved_words_table WHERE native_word LIKE '%' || :search || '%' OR translated_word LIKE '%' || :search || '%' ORDER BY id DESC")
    suspend fun searchSavedWords(search: String): List<SavedWord>

    @Query("SELECT COUNT(id) FROM saved_words_table WHERE native_word = :search")
    suspend fun getCountMatches(search: String): Int
}