package uz.gita.superdictionary.domain.repository

import android.database.Cursor
import uz.gita.superdictionary.data.db.WordEntity

interface WordsRepository {

    fun getWordsCursor(): Cursor

    fun getSavedWordsList(): Cursor

    fun updateWord(wordEntity: WordEntity)

    fun deleteWords(wordEntity: WordEntity)

    fun insertWord(wordEntity: WordEntity)

    fun getCursorBySearch(query: String): Cursor

}