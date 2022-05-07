package uz.gita.superdictionary.domain.repository

import androidx.lifecycle.LiveData
import uz.gita.superdictionary.data.db.HistoryEntity

interface HistoryRepository {

    fun getHistoryList(): LiveData<List<HistoryEntity>>

    fun deleteAllHistory()

    fun deleteWordFromHistory(historyEntity: HistoryEntity)

    fun addToHistory(historyEntity: HistoryEntity)

}