package uz.gita.superdictionary.ui.viewmodel.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import uz.gita.superdictionary.data.db.HistoryEntity
import uz.gita.superdictionary.data.db.WordDao
import uz.gita.superdictionary.data.db.WordEntity
import uz.gita.superdictionary.domain.repository.HistoryRepository
import uz.gita.superdictionary.domain.repository.WordsRepository
import uz.gita.superdictionary.ui.viewmodel.HistoryViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModelImpl @Inject constructor(
    private val repository: HistoryRepository,
    private val dao: WordDao
) :
    ViewModel(), HistoryViewModel {

    private val goToChannel = Channel<WordEntity>()
    private val failChannel = Channel<String>()

    override val historyListLiveData = repository.getHistoryList()
    override val oneWordListLiveData = goToChannel.receiveAsFlow()
    override val deleteAllLiveData = MutableLiveData<Unit>()
    override val failLiveData = failChannel.receiveAsFlow()


    override fun deleteAll() {
        repository.deleteAllHistory()
        deleteAllLiveData.value = Unit
    }

    override fun deleteHistory(historyEntity: HistoryEntity) {
        repository.deleteWordFromHistory(historyEntity)
    }

    override fun getOneWord(wordName: String) {
        viewModelScope.launch {
            val word = dao.getOneWord(wordName)
            if (word == null) {
                failChannel.send("This word does not exist")
            } else {
                goToChannel.send(word)
            }
        }

    }


}