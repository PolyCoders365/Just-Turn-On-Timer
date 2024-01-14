package jtot.dev.feature.main

import androidx.recyclerview.widget.RecyclerView
import jtot.dev.databinding.ItemContentBinding

class TextViewHolder(
    private val binding: ItemContentBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(content: String) {
        binding.content = content
    }
}
