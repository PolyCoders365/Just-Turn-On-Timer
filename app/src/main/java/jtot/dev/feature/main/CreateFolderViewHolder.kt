package jtot.dev.feature.main

import androidx.recyclerview.widget.RecyclerView
import jtot.dev.databinding.ItemCreateFolderBinding

class CreateFolderViewHolder(
    private val binding: ItemCreateFolderBinding,
) : RecyclerView.ViewHolder(binding.root) {
    val doneButton = binding.btnDone
    val titleEditText = binding.etTitle
}
