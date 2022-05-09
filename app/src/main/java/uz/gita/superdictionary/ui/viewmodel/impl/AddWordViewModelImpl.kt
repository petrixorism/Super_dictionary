package uz.gita.superdictionary.ui.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.superdictionary.data.db.AddedWordEntity
import uz.gita.superdictionary.data.db.AddedWordsDao
import uz.gita.superdictionary.data.db.WordDao
import uz.gita.superdictionary.data.db.WordEntity
import uz.gita.superdictionary.domain.repository.WordsRepository
import uz.gita.superdictionary.ui.viewmodel.AddWordViewModel
import javax.inject.Inject

@HiltViewModel
class AddWordViewModelImpl @Inject constructor(
    private val dao: AddedWordsDao,
    private val repository: WordsRepository,
    private val wordDao: WordDao
) : ViewModel(),
    AddWordViewModel {


    override val addWordLiveData = MutableLiveData<Unit>()
    override val failLiveData = MutableLiveData<String>()

    override fun addWord(wordEntity: WordEntity) {

        if (wordEntity.word.isEmpty()) {
            failLiveData.value = "Word mustn't be empty"
        } else if (wordEntity.definition.isEmpty()) {
            failLiveData.value = "Meaning mustn't be empty"
        } else if (wordDao.getOneWord(wordEntity.word) != null) {
            failLiveData.value = "This word is exist"
        } else {
            repository.addWord(wordEntity)
            dao.insert(AddedWordEntity(0, wordEntity.word))
            addWordLiveData.postValue(Unit)

        }


    }

}