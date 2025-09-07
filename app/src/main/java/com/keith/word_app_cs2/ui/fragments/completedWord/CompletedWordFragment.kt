package com.keith.word_app_cs2.ui.fragments.completedWord

import android.os.Bundle
import android.provider.UserDictionary
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.keith.word_app_cs2.databinding.FragmentCompletedWordBinding
import com.keith.word_app_cs2.ui.adapter.WordsAdapter
import com.keith.word_app_cs2.ui.fragments.home.HomeFragmentDirections
import kotlinx.coroutines.launch

class CompletedWordFragment : Fragment() {
    private lateinit var binding: FragmentCompletedWordBinding
    private val viewModel: CompletedWordViewModel by viewModels()
    private lateinit var adapter: WordsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCompletedWordBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view:View, savedInstanceState: Bundle?) {
        adapter = WordsAdapter(emptyList()){
            val action = CompletedWordFragmentDirections.actionCompletedWordFragmentToWordDetailsFragment(
                wordId = it.id!!,
            )
            findNavController().navigate(action)
        }
        super.onViewCreated(view, savedInstanceState)
        setFragmentResultListener("manage_word") {_, _ ->
            viewModel.getWords()
        }
        lifecycleScope.launch {
            viewModel.words.collect {
                adapter.setWords(it)
            }
        }
    }
}