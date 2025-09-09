package com.keith.word_app_cs2.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.keith.word_app_cs2.data.model.Word
import com.keith.word_app_cs2.databinding.ItemLayoutWordBinding

class WordsAdapter(
    private var words: List<Word>,
    private var onClick: (Word) -> Unit
): RecyclerView.Adapter<WordsAdapter.WordViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WordViewHolder {
        val binding = ItemLayoutWordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: WordViewHolder,
        position: Int
    ) {
        val word = words[position]
        holder.bind(word)
    }

    override fun getItemCount() = words.size

    fun setWords(words: List<Word>) {
        this.words = words
        notifyDataSetChanged()
    }
    inner class WordViewHolder(
        private val binding: ItemLayoutWordBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(word: Word) {
            binding.run {
                tvTitle.text = word.title
                tvMeaning.text = word.meaning
                llWord.setOnClickListener {
                    onClick(word)
                }
            }
        }
    }
}