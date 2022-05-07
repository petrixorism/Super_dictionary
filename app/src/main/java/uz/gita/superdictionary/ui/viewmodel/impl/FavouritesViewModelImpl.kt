package uz.gita.superdictionary.ui.viewmodel.impl

import android.database.Cursor
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.superdictionary.data.db.WordEntity
import uz.gita.superdictionary.domain.repository.WordsRepository
import uz.gita.superdictionary.ui.viewmodel.FavouritesViewModel
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModelImpl @Inject constructor(private val repository: WordsRepository) :
    ViewModel(), FavouritesViewModel {

    override val cursorWordsLiveData = MutableLiveData<Cursor>()
    override val failLiveData = MutableLiveData<String>()
    override val updateWordLiveData = MutableLiveData<Boolean>()

    override fun getSavedWords() {
        try {
            val data = repository.getSavedWordsList()
            cursorWordsLiveData.postValue(data)
        } catch (e: Throwable) {
            failLiveData.postValue(e.message)
        }
    }

    override fun updateWord(wordEntity: WordEntity) {
        try {
            repository.updateWord(wordEntity)
            updateWordLiveData.postValue(wordEntity.isSaved == 1)
        } catch (e: Throwable) {
            failLiveData.postValue(e.message)

        }


    }

}