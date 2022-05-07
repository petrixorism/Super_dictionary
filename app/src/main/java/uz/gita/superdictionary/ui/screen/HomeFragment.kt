package uz.gita.superdictionary.ui.screen

import android.database.Cursor
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.superdictionary.R
import uz.gita.superdictionary.databinding.FragmentHomeBinding
import uz.gita.superdictionary.ui.adapter.CursorWordsAdapter
import uz.gita.superdictionary.ui.viewmodel.HomeViewModel
import uz.gita.superdictionary.ui.viewmodel.SplashViewModel
import uz.gita.superdictionary.ui.viewmodel.impl.HomeViewModelImpl
import uz.gita.superdictionary.ui.viewmodel.impl.SplashViewModelImpl
import uz.gita.superdictionary.util.showSnackBar
import uz.gita.superdictionary.util.showToast

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels<HomeViewModelImpl>()
    private val wordsAdapter by lazy { CursorWordsAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.wordsRv.apply {

            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = wordsAdapter

        }

        viewModel.cursorWordsLiveData.observe(viewLifecycleOwner, cursorWordsObserver)
        viewModel.failLiveData.observe(viewLifecycleOwner, failObserver)
        viewModel.updateWordLiveData.observe(viewLifecycleOwner, updateWordObserver)

        viewModel.getAllWords()

        wordsAdapter.setToggleClick {
            viewModel.updateWord(it)
        }

        wordsAdapter.setItemClick {
            showSnackBar(it.definition)
        }

    }


    private val cursorWordsObserver = Observer<Cursor>{
        wordsAdapter.submitCursor(it)
    }

    private val failObserver = Observer<String>{
        showToast(it)
    }
    private val updateWordObserver = Observer<Unit>{
        viewModel.getAllWords()
    }

}