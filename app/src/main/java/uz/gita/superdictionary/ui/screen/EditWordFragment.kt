package uz.gita.superdictionary.ui.screen

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.ldralighieri.corbind.view.clicks
import uz.gita.superdictionary.R
import uz.gita.superdictionary.data.db.WordEntity
import uz.gita.superdictionary.databinding.FragmentEditWordBinding
import uz.gita.superdictionary.ui.viewmodel.EditWordViewModel
import uz.gita.superdictionary.ui.viewmodel.impl.EditWordViewModelImpl
import uz.gita.superdictionary.util.showSnackBar
import uz.gita.superdictionary.util.showToast

@AndroidEntryPoint
class EditWordFragment : Fragment(R.layout.fragment_edit_word) {

    private val binding by viewBinding(FragmentEditWordBinding::bind)
    private val args by navArgs<EditWordFragmentArgs>()
    private val viewModel: EditWordViewModel by viewModels<EditWordViewModelImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.editWordLiveData.observe(this) {
            showSnackBar("Word has been edited")
        }
        viewModel.failLiveData.observe(this) {
            showToast(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        binding.wordEt.setText(args.word)
        binding.meaningEt.setText(args.meaning)
        binding.exampleEt.setText(args.example)
        binding.synonymEt.setText(args.synonym)
        binding.antonymEt.setText(args.antonym)


        binding.editWordBtn.setOnClickListener {
            viewModel.editWord(
                WordEntity(
                    args.id,
                    binding.wordEt.text.toString(),
                    binding.meaningEt.text.toString(),
                    binding.exampleEt.text.toString(),
                    binding.synonymEt.text.toString(),
                    binding.antonymEt.text.toString(),
                    args.isSaved
                )
            )
        }

        binding.backBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.gridBtn.setOnClickListener {
            val drawer = requireActivity().findViewById<DrawerLayout>(R.id.drawer_layout)
            drawer.openDrawer(Gravity.RIGHT)
        }
    }

}