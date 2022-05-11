package uz.gita.superdictionary.ui.screen

import android.app.Activity.CLIPBOARD_SERVICE
import android.app.Activity.RESULT_OK
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.view.Gravity
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
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
import uz.gita.superdictionary.util.showToast
import java.util.*

@AndroidEntryPoint
class WordFragment : Fragment(R.layout.fragment_word) {

    private val binding by viewBinding(FragmentWordBinding::bind)
    private val args by navArgs<WordFragmentArgs>()
    private val viewModel: WordViewModel by viewModels<WordViewModelImpl>()
    private val REQUEST_CODE = 100
    private var textToSpeech: TextToSpeech? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        viewModel.updateLiveData.observe(viewLifecycleOwner, updateObserver)

        binding.gridBtn.setOnClickListener {
            val drawer = requireActivity().findViewById<DrawerLayout>(R.id.drawer_layout)
            drawer.openDrawer(Gravity.RIGHT)
        }

        binding.apply {

            wordTv.text = args.word
            wordMeaning.text = args.meaning
            gridBtn.setOnClickListener {
                val drawer = requireActivity().findViewById<DrawerLayout>(R.id.drawer_layout)
                drawer.openDrawer(Gravity.RIGHT)
            }

            if (args.example != "NA" && args.example.isNotEmpty()) {
                makeVisibleOrGone(this.exampleContainer, true)
                this.wordExamples.text = args.example.replace(",", ", ").replace("  ", " ")
            }
            if (args.synonym != "NA" && args.synonym.isNotEmpty()) {
                makeVisibleOrGone(this.synonymContainer, true)
                this.wordSynonym.text = args.synonym.replace(",", ", ").replace("  ", " ")
            }
            if (!args.antonym.contains("NA") && args.antonym.isNotEmpty()) {
                makeVisibleOrGone(this.antonymContainer, true)
                this.wordAntonym.text = args.antonym.replace(",", ", ").replace("  ", " ")
            }
            backBtn.setOnClickListener {
                requireActivity().onBackPressed()
            }
            binding.wordSave.isChecked = args.isSaved == "1"



            wordSave.setOnClickListener {
                viewModel.updateWord(
                    WordEntity(
                        args.id,
                        args.word,
                        args.meaning,
                        args.example,
                        args.synonym,
                        args.antonym,
                        args.isSaved.toInt()
                    )
                )
            }
            wordCopy.setOnClickListener {
                val clipboard =
                    requireContext().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                val clip =
                    ClipData.newPlainText("WORD", args.word[0] + args.word.substring(1).lowercase())
                clipboard.setPrimaryClip(clip)

                showToast("${args.word} copied")
            }
            copyMeaningBtn.setOnClickListener {
                val clipboard =
                    requireContext().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                val clip =
                    ClipData.newPlainText("WORD", args.meaning)
                clipboard.setPrimaryClip(clip)
                showToast("Meanings have been copied")

            }
            copyExamplesBtn.setOnClickListener {
                val clipboard =
                    requireContext().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                val clip =
                    ClipData.newPlainText("WORD", args.example)
                clipboard.setPrimaryClip(clip)
                showToast("Examples have been copied")

            }
            copySynonymBtn.setOnClickListener {
                val clipboard =
                    requireContext().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                val clip =
                    ClipData.newPlainText("WORD", args.synonym)
                clipboard.setPrimaryClip(clip)
                showToast("Synonyms have been copied")

            }
            copyAntonymBtn.setOnClickListener {
                val clipboard =
                    requireContext().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                val clip =
                    ClipData.newPlainText("WORD", args.antonym)
                clipboard.setPrimaryClip(clip)
                showToast("Antonyms have been copied")

            }
        }
        textToSpeech = TextToSpeech(requireContext()) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech!!.language = Locale.UK

            }
        }
        textToSpeech!!.language = Locale.UK
        binding.wordVoice.setOnClickListener {
            val word = args.word
            textToSpeech!!.speak(
                word[0] + word.substring(1, word.length),
                TextToSpeech.QUEUE_FLUSH,
                null,
                ""
            )
        }
    }

    private val updateObserver = Observer<Boolean> {
        if (it) {
            showSnackBar("${args.word} has been added to saved list")
        } else {
            showSnackBar("${args.word} has been removed from saved list")
        }
    }


    override fun onDestroy() {
        if (textToSpeech != null) {
            textToSpeech!!.stop()
            textToSpeech!!.shutdown()
        }
        super.onDestroy()


    }
}