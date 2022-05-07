package uz.gita.superdictionary.ui.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.superdictionary.R
import uz.gita.superdictionary.data.db.WordEntity
import uz.gita.superdictionary.databinding.FragmentWordBinding
import uz.gita.superdictionary.ui.viewmodel.WordViewModel
import uz.gita.superdictionary.ui.viewmodel.impl.WordViewModelImpl
import uz.gita.superdictionary.util.makeVisibleOrGone
import uz.gita.superdictionary.util.showSnackBar

@AndroidEntryPoint
class WordFragment : Fragment(R.layout.fragment_word) {

    private val binding by viewBinding(FragmentWordBinding::bind)
    private val args by navArgs<WordFragmentArgs>()
    private val viewModel: WordViewModel by viewModels<WordViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        viewModel.updateLiveData.observe(viewLifecycleOwner, updateObserver)

        binding.apply {

            wordTv.text = args.word
            wordMeaning.text = args.meaning

            if (args.example != "NA") {
                makeVisibleOrGone(this.exampleContainer, true)
                this.wordExamples.text = args.example
            }
            if (args.synonym != "NA") {
                makeVisibleOrGone(this.synonymContainer, true)
                this.wordSynonym.text = args.synonym
            }
            if (!args.antonym.contains("NA")) {
                makeVisibleOrGone(this.antonymContainer, true)
                this.wordAntonym.text = args.antonym
            }
            backBtn.setOnClickListener {
                requireActivity().onBackPressed()
            }
            binding.wordSave.isChecked = args.isSaved == "1"

            wordSave.setOnClickListener {
                viewModel.updateWord(
                    WordEntity(
                        args.id.toInt(),
                        args.word,
                        args.meaning,
                        args.example,
                        args.synonym,
                        args.antonym,
                        args.isSaved.toInt()
                    )
                )
            }
        }

    }

    private val updateObserver = Observer<Boolean> {
        if (it) {
            showSnackBar("${args.word} has been added to saved list")
        } else {
            showSnackBar("${args.word} has been removed from saved list")
        }
    }

}