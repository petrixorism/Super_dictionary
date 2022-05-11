package uz.gita.superdictionary.ui.screen

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.Gravity
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import me.zhanghai.android.fastscroll.FastScrollerBuilder
import uz.gita.superdictionary.R
import uz.gita.superdictionary.databinding.FragmentHomeBinding
import uz.gita.superdictionary.ui.adapter.CursorWordsAdapter
import uz.gita.superdictionary.ui.viewmodel.HomeViewModel
import uz.gita.superdictionary.ui.viewmodel.impl.HomeViewModelImpl
import uz.gita.superdictionary.util.showToast
import java.util.*

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels<HomeViewModelImpl>()
    private val wordsAdapter by lazy { CursorWordsAdapter() }
    private val REQUEST_CODE = 100


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.cursorWordsLiveData.observe(this, cursorWordsObserver)
        viewModel.failLiveData.observe(this, failObserver)
        viewModel.updateWordLiveData.observe(this, updateWordObserver)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        showToast("OnViewCreated")

        FastScrollerBuilder(binding.wordsRv)
            .build()

        binding.wordsRv.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = wordsAdapter
        }


        binding.wordEt.setOnQueryTextListener(listener)
        viewModel.getAllWords()
        wordsAdapter.setToggleClick {
            viewModel.updateWord(it)
        }
        wordsAdapter.setItemClick {
            viewModel.addToHistory(it)
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToWordFragment(
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
        binding.voiceToTextBtn.setOnClickListener {
            speechToText()
        }
        binding.gridBtn.setOnClickListener {
            val drawer = requireActivity().findViewById<DrawerLayout>(R.id.drawer_layout)
            drawer.openDrawer(Gravity.RIGHT)
        }

    }

    private val listener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(p0: String?): Boolean {
            viewModel.searchWord(p0.toString())
            return true
        }

        override fun onQueryTextChange(p0: String?): Boolean {
            return false
        }
    }

    private val cursorWordsObserver = Observer<Cursor> {
        wordsAdapter.submitCursor(it)
    }

    private val failObserver = Observer<String> {
        showToast(it)
    }
    private val updateWordObserver = Observer<Unit> {
        viewModel.getAllWords()
    }

    private fun speechToText() {

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-UK");
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something")

        try {
            startActivityForResult(intent, REQUEST_CODE);
        } catch (e: Throwable) {
            showToast("Sorry! your device does not support speech input")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    binding.wordEt.setQuery(result!![0], true)
                    viewModel.searchWord(result!![0])

                }
            }

        }

    }
}