package uz.gita.superdictionary.ui.screen

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.View.VISIBLE
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import uz.gita.superdictionary.R
import uz.gita.superdictionary.databinding.FragmentHistoryBinding
import uz.gita.superdictionary.ui.adapter.HistoryAdapter
import uz.gita.superdictionary.ui.viewmodel.HistoryViewModel
import uz.gita.superdictionary.ui.viewmodel.impl.HistoryViewModelImpl
import uz.gita.superdictionary.util.makeVisibleOrGone
import uz.gita.superdictionary.util.showToast

@AndroidEntryPoint
class HistoryFragment : Fragment(R.layout.fragment_history) {

    private val binding by viewBinding(FragmentHistoryBinding::bind)
    private val viewModel: HistoryViewModel by viewModels<HistoryViewModelImpl>()
    private val historyAdapter by lazy { HistoryAdapter() }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.historyListLiveData.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                makeVisibleOrGone(binding.infoTv, true)
            } else {
                makeVisibleOrGone(binding.infoTv, false)
            }
            historyAdapter.submitList(it)
        }
        lifecycleScope.launchWhenCreated {
            viewModel.oneWordListLiveData.collect {
                findNavController().navigate(
                    HistoryFragmentDirections.actionHistoryFragmentToWordFragment(
                        it.id,
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
        lifecycleScope.launchWhenCreated {
            viewModel.failLiveData.collect {
                showToast(it)
            }
        }



        binding.historyRv.apply {
            adapter = historyAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
        binding.backBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.deleteAll.setOnClickListener {
            viewModel.deleteAll()
        }
        binding.gridBtn.setOnClickListener {
            val drawer = requireActivity().findViewById<DrawerLayout>(R.id.drawer_layout)
            drawer.openDrawer(Gravity.RIGHT)
        }

        historyAdapter.setRemoveClick {
            viewModel.deleteHistory(it)
        }
        historyAdapter.setItemClick {
            viewModel.getOneWord(it.word)
        }


    }

}