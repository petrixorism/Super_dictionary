package uz.gita.superdictionary.ui.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.superdictionary.R
import uz.gita.superdictionary.data.db.WordEntity
import uz.gita.superdictionary.databinding.FragmentAddWordBinding
import uz.gita.superdictionary.ui.viewmodel.AddWordViewModel
import uz.gita.superdictionary.ui.viewmodel.impl.AddWordViewModelImpl
import uz.gita.superdictionary.util.showSnackBar
import uz.gita.superdictionary.util.showToast
import java.util.*

@AndroidEntryPoint
class AddWordFragment : Fragment(R.layout.fragment_add_word) {


    private val binding by viewBinding(FragmentAddWordBinding::bind)
    private val viewModel: AddWordViewModel by viewModels<AddWordViewModelImpl>()
    private lateinit var word: WordEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.addWordLiveData.observe(this) {
            showSnackBar("Word has been added")
        }
        viewModel.failLiveData.observe(this) {
            showToast(it)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.apply {
            addWordBtn.setOnClickListener {
                word = WordEntity(
                    0,
                    this.wordEt.text.toString().uppercase(),
                    this.meaningEt.text.toString(),
                    this.exampleEt.text.toString(),
                    this.synonymEt.text.toString(),
                    this.antonymEt.text.toString(),
                    0
                )
                viewModel.addWord(word)
            }
            backBtn.setOnClickListener {
                requireActivity().onBackPressed()
            }

        }

    }

}