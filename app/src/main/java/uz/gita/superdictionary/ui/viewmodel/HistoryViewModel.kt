package uz.gita.superdictionary.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow
import uz.gita.superdictionary.data.db.HistoryEntity
import uz.gita.superdictionary.data.db.WordEntity

interface HistoryViewModel {

    val historyListLiveData: LiveData<List<HistoryEntity>>

    val oneWordListLiveData: Flow<WordEntity>

    val deleteAllLiveData: LiveData<Unit>

    val failLiveData: Flow<String>

    fun deleteAll()

    fun deleteHistory(historyEntity: HistoryEntity)

    fun getOneWord(wordName: String)

}