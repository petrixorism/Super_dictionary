package uz.gita.superdictionary.ui.viewmodel.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.superdictionary.ui.viewmodel.SplashViewModel

class SplashViewModelImpl : ViewModel(), SplashViewModel {
    override val splashLiveData = MutableLiveData<Unit>()

    init {
        viewModelScope.launch {
            delay(1500L)
            splashLiveData.value = Unit
        }

    }

}