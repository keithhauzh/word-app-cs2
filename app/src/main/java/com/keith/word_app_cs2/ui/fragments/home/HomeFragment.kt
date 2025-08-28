package com.keith.word_app_cs2.ui.fragments.home

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.keith.word_app_cs2.ui.adapter.WordsAdapter
import com.keith.word_app_cs2.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: WordsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(
            layoutInflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        lifecycleScope.launch {
            viewModel.words.collect {
                adapter.setWords(it)
            }
        }
        binding.fabAdd.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAddWordFragment()
            findNavController().navigate(action)
        }
        setFragmentResultListener("manage_word") {_, _ ->
            viewModel.getWords()
        }
    }

    fun setupAdapter() {
        adapter = WordsAdapter(emptyList())
        binding.rvWords.adapter = adapter
        binding.rvWords.layoutManager = LinearLayoutManager(requireContext())
//        adapter.setListener(object: WordsAdapter.Listener{
//            override fun onClick(word: Word){
//                val action = HomeFragmentDirections.actionHomeToEditBook(book.id!!)
//                findNavController().navigate(action)
//            }
//        })
    }
}