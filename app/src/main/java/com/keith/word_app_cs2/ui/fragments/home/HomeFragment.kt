package com.keith.word_app_cs2.ui.fragments.home

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.keith.word_app_cs2.R
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
        super.onCreate(savedInstanceState)
        binding = FragmentHomeBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment = requireActivity().supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.navView.setupWithNavController(navController)

        setupAdapter()

        lifecycleScope.launch {
            viewModel.words.collect {
                adapter.setWords(it)
                binding.llEmpty.visibility = if(it.isEmpty()) View.VISIBLE else View.GONE
            }
        }
        binding.fabAdd.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAddWordFragment()
            navController.navigate(action)
        }

        setFragmentResultListener("manage_word") { _, _ ->
            viewModel.getWords()
        }
        setFragmentResultListener("sort_options") {_, bundle ->
            val sortOder = bundle.getString("sort_order", "ascending")
            val sortBy = bundle.getString("sort_by", "title")
            viewModel.sortWords(sortOder, sortBy)
        }
        binding.ivSort.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToSortDialogFragment()
            findNavController().navigate(action)
        }
        binding.etSearch.addTextChangedListener {
            viewModel.search(it.toString().trim())
        }
    }
    fun setupAdapter() {
        adapter = WordsAdapter(emptyList()) {
            val action = HomeFragmentDirections.actionHomeFragmentToWordDetailsFragment(
                wordId = it.id!!,
            )
            findNavController().navigate(action)
        }
        binding.rvWords.layoutManager = LinearLayoutManager(requireContext())
        binding.rvWords.adapter = adapter
    }
}