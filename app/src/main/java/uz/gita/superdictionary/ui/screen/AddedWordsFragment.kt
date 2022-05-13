package uz.gita.superdictionary.ui.screen

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import uz.gita.superdictionary.R
import uz.gita.superdictionary.databinding.FragmentAddWordBinding
import uz.gita.superdictionary.databinding.FragmentAddedWordsBinding
import uz.gita.superdictionary.databinding.FragmentFavouritesBinding
import uz.gita.superdictionary.ui.adapter.AddedWordsAdapter
import uz.gita.superdictionary.ui.adapter.CursorWordsAdapter
import uz.gita.superdictionary.ui.viewmodel.AddWordViewModel
import uz.gita.superdictionary.ui.viewmodel.AddedWordsViewModel
import uz.gita.superdictionary.ui.viewmodel.FavouritesViewModel
import uz.gita.superdictionary.ui.viewmodel.impl.AddWordViewModelImpl
import uz.gita.superdictionary.ui.viewmodel.impl.AddedWordsViewModelImpl
import uz.gita.superdictionary.ui.viewmodel.impl.FavouritesViewModelImpl
import uz.gita.superdictionary.util.showToast

@AndroidEntryPoint
class AddedWordsFragment : Fragment(R.layout.fragment_added_words) {


    private val binding by viewBinding(FragmentAddedWordsBinding::bind)
    private val viewModel: AddedWordsViewModel by viewModels<AddedWordsViewModelImpl>()
    private val adapter by lazy { AddedWordsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        lifecycleScope.launchWhenCreated {
            viewModel.editFlow.collect {
                findNavController().navigate(
                    AddedWordsFragmentDirections.actionAddedWordsFragmentToEditWordFragment(
                        it.id,
                        it.word,
                        it.definition,
                        it.example,
                        it.synonym,
                        it.antonym,
                        it.isSaved
                    )
                )

            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.goToFlow.collect {
                findNavController().navigate(
                    AddedWordsFragmentDirections.actionAddedWordsFragmentToWordFragment(
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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.gridBtn.setOnClickListener {
            val drawer = requireActivity().findViewById<DrawerLayout>(R.id.drawer_layout)
            drawer.openDrawer(Gravity.RIGHT)
        }

        binding.backBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.addedWordsRv.adapter = adapter

        adapter.setItemClick {
            viewModel.goToWord(it)
        }

        adapter.setEditClick {
            viewModel.editWord(it)
        }

        adapter.setRemoveClick {
            viewModel.deleteWord(it)
        }

        binding.addWordBtn.setOnClickListener {
            findNavController().navigate(AddedWordsFragmentDirections.actionAddedWordsFragmentToAddWordFragment())
        }
        viewModel.getAllWordsLiveData.observe(requireActivity()) {
            if (it.isEmpty()) {
                binding.infoTv.visibility = View.VISIBLE
            }
            adapter.submitList(it)
        }
    }

}