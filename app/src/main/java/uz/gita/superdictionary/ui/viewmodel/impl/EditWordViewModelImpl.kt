package uz.gita.superdictionary.ui.viewmodel.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.superdictionary.data.db.AddedWordEntity
import uz.gita.superdictionary.data.db.AddedWordsDao
import uz.gita.superdictionary.data.db.WordDao
import uz.gita.superdictionary.data.db.WordEntity
import uz.gita.superdictionary.ui.viewmodel.EditWordViewModel
import javax.inject.Inject

@HiltViewModel
class EditWordViewModelImpl @Inject constructor(
    private val wordDao: WordDao,
    private val addedDao: AddedWordsDao,

    ) : ViewModel(), EditWordViewModel {
    override val editWordLiveData = MutableLiveData<Unit>()
    override val failLiveData = MutableLiveData<String>()

    override fun editWord(wordEntity: WordEntity) {

        if (wordEntity.word.isEmpty() && wordEntity.definition.isEmpty()) {
            failLiveData.value = "Word or it's meaning is empty"
        } else {

            addedDao.update(
                AddedWordEntity(
                    wordEntity.id,
                    wordEntity.word
                )
            )
            wordDao.updateWord(wordEntity)
            editWordLiveData.postValue(Unit)

        }


    }
}