package uz.gita.superdictionary.ui.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.superdictionary.R
import uz.gita.superdictionary.databinding.FragmentFavouritesBinding
import uz.gita.superdictionary.databinding.FragmentWordBinding
import uz.gita.superdictionary.ui.adapter.CursorWordsAdapter
import uz.gita.superdictionary.ui.viewmodel.FavouritesViewModel
import uz.gita.superdictionary.ui.viewmodel.WordViewModel
import uz.gita.superdictionary.ui.viewmodel.impl.FavouritesViewModelImpl
import uz.gita.superdictionary.ui.viewmodel.impl.WordViewModelImpl
import uz.gita.superdictionary.util.showSnackBar
import uz.gita.superdictionary.util.showToast

@AndroidEntryPoint
class FavouritesFragment : Fragment(R.layout.fragment_favourites) {

    private val binding by viewBinding(FragmentFavouritesBinding::bind)
    private val viewModel: FavouritesViewModel by viewModels<FavouritesViewModelImpl>()
    private val adapter by lazy { CursorWordsAdapter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.cursorWordsLiveData.observe(this) {
            adapter.submitCursor(it)
        }

        viewModel.failLiveData.observe(this) {
            showToast(it)
        }

        viewModel.updateWordLiveData.observe(this, updateObserver)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.getSavedWords()
        binding.backBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.savedWordsRv.adapter = adapter

        adapter.setToggleClick {
            viewModel.updateWord(it)
        }

        adapter.setItemClick {
            findNavController().navigate(
                FavouritesFragmentDirections.actionFavouritesFragmentToWordFragment(
                    it.id.toLong(),
                    it.word,
                    it.definition,
                    it.example,
                    it.synonym,
                    it.antonym,
                    it.isSaved.toString()
                )
            )
        }

    }

    private val updateObserver = Observer<Boolean> {
        viewModel.getSavedWords()
    }


}