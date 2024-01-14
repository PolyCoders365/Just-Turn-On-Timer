package jtot.dev.feature.main

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import jtot.dev.R
import jtot.dev.databinding.ItemFolderBinding
import jtot.dev.feature.play.decoration.ContentDecoration
import jtot.dev.feature.play.decoration.ToggleAnimation
import jtot.dev.model.Folder
import jtot.dev.utils.dpToPixel

class FolderViewHolder(
    private val binding: ItemFolderBinding,
) : RecyclerView.ViewHolder(binding.root) {
    private var isExpand = false

    fun bind(folder: Folder) {
        binding.folder = folder

        View.OnClickListener {
            ToggleAnimation.toggleArrow(view = binding.btnMore, isExpanded = isExpand)
            if (isExpand) {
                ToggleAnimation.expand(binding.layoutExpand)
                binding.layoutFolder.background =
                    ContextCompat.getDrawable(
                        binding.root.context,
                        R.drawable.background_todo_top_expand,
                    )
            } else {
                ToggleAnimation.collapse(binding.layoutExpand)
                binding.layoutFolder.background =
                    ContextCompat.getDrawable(
                        binding.root.context,
                        R.drawable.background_todo_top,
                    )
            }
            isExpand = !isExpand
            binding.rvNestContent.run {
                adapter =
                    FolderAdapter().apply {
                        setFolderList(folder.docs)
                    }
                if (this.itemDecorationCount < 1) {
                    addItemDecoration(
                        ContentDecoration(
                            binding.root.context.dpToPixel(16f).toInt(),
                        ),
                    )
                }
            }
        }.apply {
            binding.btnMore.setOnClickListener(this)
            binding.layoutFolder.setOnClickListener(this)
        }
    }
}
