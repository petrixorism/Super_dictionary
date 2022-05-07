package uz.gita.superdictionary.ui.screen

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.android.HandlerDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.superdictionary.R
import uz.gita.superdictionary.ui.viewmodel.SplashViewModel
import uz.gita.superdictionary.ui.viewmodel.impl.SplashViewModelImpl

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val viewModel: SplashViewModel by viewModels<SplashViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.splashLiveData.observe(viewLifecycleOwner){
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
        }

    }

}