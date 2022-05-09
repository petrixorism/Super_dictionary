package uz.gita.superdictionary.domain.repository.impl

import android.database.Cursor
import uz.gita.superdictionary.data.db.WordDao
import uz.gita.superdictionary.data.db.WordEntity
import uz.gita.superdictionary.domain.repository.WordsRepository
import javax.inject.Inject


class WordsRepositoryImpl @Inject constructor(private val dao: WordDao) : WordsRepository {

    override fun getWordsCursor(): Cursor = dao.getAllWordsCursor()

    override fun getSavedWordsList(): Cursor = dao.getSavedWords()

    override fun updateWord(wordEntity: WordEntity) = dao.updateWord(wordEntity)

    override fun deleteWords(wordEntity: WordEntity) = dao.deleteWord(wordEntity)

    override fun insertWord(wordEntity: WordEntity) = dao.insertWord(wordEntity)

    override fun getCursorBySearch(query: String): Cursor = dao.getWordCursorByQuery(query)

    override fun addWord(wordEntity: WordEntity) = dao.insertWord(wordEntity)


}