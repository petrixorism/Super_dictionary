package uz.gita.superdictionary.ui.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.superdictionary.R
import uz.gita.superdictionary.databinding.FragmentFavouritesBinding
import uz.gita.superdictionary.databinding.FragmentHistoryBinding
import uz.gita.superdictionary.ui.adapter.CursorWordsAdapter
import uz.gita.superdictionary.ui.adapter.HistoryAdapter
import uz.gita.superdictionary.ui.viewmodel.FavouritesViewModel
import uz.gita.superdictionary.ui.viewmodel.HistoryViewModel
import uz.gita.superdictionary.ui.viewmodel.impl.FavouritesViewModelImpl
import uz.gita.superdictionary.ui.viewmodel.impl.HistoryViewModelImpl
import uz.gita.superdictionary.util.showToast

@AndroidEntryPoint
class HistoryFragment : Fragment(R.layout.fragment_history) {

    private val binding by viewBinding(FragmentHistoryBinding::bind)
    private val viewModel: HistoryViewModel by viewModels<HistoryViewModelImpl>()
    private val historyAdapter by lazy { HistoryAdapter() }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.historyListLiveData.observe(viewLifecycleOwner) {
            historyAdapter.submitList(it)
        }
        binding.historyRv.apply {
            adapter = historyAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
        historyAdapter.setRemoveClick {
            viewModel.deleteHistory(it)
        }

        binding.deleteAll.setOnClickListener {
            viewModel.deleteAll()
        }

    }

}