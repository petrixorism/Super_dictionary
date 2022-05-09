package uz.gita.superdictionary.data.db

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface WordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWord(word: WordEntity)

    @Delete
    fun deleteWord(word: WordEntity)

    @Query("SELECT * FROM words WHERE isSaved=1 ORDER BY en_word")
    fun getSavedWords(): Cursor

    @Query("SELECT * FROM words ORDER BY en_word")
    fun getAllWordsCursor(): Cursor

    @Query("SELECT * FROM words WHERE en_word LIKE :query")
    fun getWordCursorByQuery(query: String): Cursor

    @Update
    fun updateWord(word: WordEntity)

    @Query("SELECT * FROM words WHERE en_word LIKE :word")
    fun getOneWord(word: String): WordEntity?
}