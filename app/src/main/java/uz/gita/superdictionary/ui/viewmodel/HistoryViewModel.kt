package uz.gita.superdictionary.ui.viewmodel

import android.database.Cursor
import androidx.lifecycle.LiveData
import uz.gita.superdictionary.data.db.HistoryEntity
import uz.gita.superdictionary.data.db.WordEntity

interface HistoryViewModel {

    val historyListLiveData: LiveData<List<HistoryEntity>>

    val deleteAllLiveData: LiveData<Unit>

    fun deleteAll()

    fun deleteHistory(historyEntity: HistoryEntity)

}