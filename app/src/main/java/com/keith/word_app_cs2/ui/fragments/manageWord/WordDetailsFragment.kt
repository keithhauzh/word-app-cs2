package com.keith.word_app_cs2.ui.fragments.manageWord

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.keith.word_app_cs2.R
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

        finishObserver()
        setupLifeCycleScope()
    }

    private fun finishObserver() {
        lifecycleScope.launch {
            viewModel.finish.collect {
                setFragmentResult("manage_word", Bundle())
                findNavController().popBackStack(R.id.homeFragment, false)
            }
        }
    }

    private fun setupLifeCycleScope() {
        lifecycleScope.launch {
            viewModel.word.collect {
                binding.tvTitle.text = it?.title.toString()
                binding.tvMeaning.text = it?.meaning.toString()
                binding.tvSyn.text = it?.synonyms.toString()
                binding.tvDetails.text = it?.details.toString()
                binding.mbDone.isEnabled = if(it?.completed == false) true else false
                setupUpdateListener()
                setupDeleteListener()
                setupDoneListener()
            }
        }
    }

    private fun setupUpdateListener() {
        binding.mbUpdate.setOnClickListener {
            val action = WordDetailsFragmentDirections.actionWordDetailsFragmentToEditWordFragment(wordId = args.wordId)
            findNavController().navigate(action)
        }
    }

    private fun setupDeleteListener() {
        binding.mbDelete.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Are you sure?")
                .setMessage("You want to delete this word? Action can not be undone.")
                .setNegativeButton("Cancel") {dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton("Delete") {dialog, _ ->
                    viewModel.deleteWord(args.wordId)
                    dialog.dismiss()
                }
                .show()
        }
    }

    private fun setupDoneListener() {
        binding. mbDone.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Are you sure?")
                .setMessage("Do you want to move this word to completed list.")
                .setNegativeButton("Yes") {dialog, _ ->
                    viewModel.isDone(args.wordId)
                    dialog.dismiss()
                }
                .setPositiveButton("No") {dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }
}