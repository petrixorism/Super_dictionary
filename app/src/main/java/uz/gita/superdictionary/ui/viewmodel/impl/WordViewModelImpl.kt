package uz.gita.superdictionary.ui.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.superdictionary.data.db.WordEntity
import uz.gita.superdictionary.domain.repository.WordsRepository
import uz.gita.superdictionary.ui.viewmodel.WordViewModel
import javax.inject.Inject


@HiltViewModel
class WordViewModelImpl @Inject constructor(private val repository: WordsRepository) : ViewModel(),
    WordViewModel {

    override val updateLiveData = MutableLiveData<Boolean>()

    override fun updateWord(wordEntity: WordEntity) {

        if (wordEntity.isSaved == 1) {
            wordEntity.isSaved = 0
        } else wordEntity.isSaved = 1

        updateLiveData.postValue(wordEntity.isSaved==1)
        repository.updateWord(wordEntity)

    }
}