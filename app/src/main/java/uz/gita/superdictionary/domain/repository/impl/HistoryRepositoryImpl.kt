package uz.gita.superdictionary.domain.repository.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import uz.gita.superdictionary.data.db.HistoryDao
import uz.gita.superdictionary.data.db.HistoryEntity
import uz.gita.superdictionary.domain.repository.HistoryRepository
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(private val dao: HistoryDao) : HistoryRepository {

    override fun getHistoryList(): LiveData<List<HistoryEntity>> = dao.getWordsHistory()


    override fun deleteAllHistory() {
        val historyList = dao.getWordsHistoryList()
        historyList.forEach {
            dao.deleteWord(it)
        }
    }

    override fun deleteWordFromHistory(historyEntity: HistoryEntity) {
        dao.deleteWord(historyEntity)
    }

    override fun addToHistory(historyEntity: HistoryEntity) {
        dao.insertWord(historyEntity)
    }


}