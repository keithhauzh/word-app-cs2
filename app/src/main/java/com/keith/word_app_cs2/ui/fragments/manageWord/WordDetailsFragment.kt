package com.keith.word_app_cs2.ui.fragments.manageWord

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.keith.word_app_cs2.databinding.FragmentWordDetailsBinding
import kotlinx.coroutines.launch

class WordDetailsFragment : Fragment() {
    private val viewModel: WordDetailsViewModel by viewModels()
    private lateinit var binding: FragmentWordDetailsBinding
    private val args: WordDetailsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWordDetailsBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadWord(args.wordId)

        lifecycleScope.launch {
            viewModel.finish.collect {
                setFragmentResult("manage_word", Bundle())
                findNavController().popBackStack()
            }
        }

        lifecycleScope.launch {
            viewModel.word.collect {
                binding.run {
                    tvTitle.text = it?.title.toString()
                    tvMeaning.text = it?.meaning.toString()
                    tvSyn.text = it?.synonyms.toString()
                    tvDetails.text = it?.details.toString()
                }
            }
        }


        binding.run {
            mbUpdate.setOnClickListener {
                findNavController().navigate("editWordFragment")
            }
        }
    }

}