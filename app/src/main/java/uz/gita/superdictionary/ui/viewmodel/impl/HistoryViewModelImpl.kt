package uz.gita.superdictionary.ui.viewmodel.impl

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.superdictionary.data.db.HistoryEntity
import uz.gita.superdictionary.data.db.WordEntity
import uz.gita.superdictionary.domain.repository.HistoryRepository
import uz.gita.superdictionary.domain.repository.WordsRepository
import uz.gita.superdictionary.ui.viewmodel.HistoryViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModelImpl @Inject constructor(private val repository: HistoryRepository) :
    ViewModel(), HistoryViewModel {


    override val historyListLiveData = repository.getHistoryList()

    override val deleteAllLiveData = MutableLiveData<Unit>()



    override fun deleteAll() {
        repository.deleteAllHistory()
        deleteAllLiveData.value = Unit
    }

    override fun deleteHistory(historyEntity: HistoryEntity) {

        repository.deleteWordFromHistory(historyEntity)

    }


}