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
import uz.gita.superdictionary.ui.viewmodel.HomeViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModelImpl @Inject constructor(
    private val repository: WordsRepository,
    private val historyRepository: HistoryRepository
) : ViewModel(),
    HomeViewModel {


    override val cursorWordsLiveData = MutableLiveData<Cursor>()

    override val updateWordLiveData = MutableLiveData<Unit>()

    override fun addToHistory(wordEntity: WordEntity) {
        val data = HistoryEntity(0, wordEntity.word)
        historyRepository.addToHistory(data)
    }

    override val failLiveData = MutableLiveData<String>()


    override fun getAllWords() {

        cursorWordsLiveData.value = repository.getWordsCursor()

    }

    override fun searchWord(query: String) {
        if (query.trim().isNotEmpty())
            cursorWordsLiveData.value = repository.getCursorBySearch("%${query.trim()}%")

    }

    override fun updateWord(wordEntity: WordEntity) {
        repository.updateWord(wordEntity)
        updateWordLiveData.postValue(Unit)
    }

}