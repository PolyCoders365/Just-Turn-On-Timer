package jtot.dev.feature.main

import android.util.Log
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
    private val createFolder: (Folder) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    var isExpand = false
    val layoutFolder = binding.layoutFolder
    val btnMore = binding.btnMore
    val btnFolder = binding.btnFolder
    val layoutExpand = binding.layoutExpand
    val rvNestContent = binding.rvNestContent
    val etTitle = binding.etTitle
    lateinit var bindFolder: Folder

    fun bind(folder: Folder) {
        binding.folder = folder
        bindFolder = folder

        binding.btnMore.setOnClickListener {
            ToggleAnimation.toggleArrow(view = binding.btnMore, isExpanded = isExpand)
            if (isExpand) {
                ToggleAnimation.expand(layoutExpand)
                layoutFolder.background =
                    ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.background_todo_top_expand,
                    )
            } else {
                ToggleAnimation.collapse(layoutExpand)
                layoutFolder.background =
                    ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.background_todo_top,
                    )
            }
            isExpand = !(isExpand)
            rvNestContent.run {
                adapter =
                    FolderAdapter(
                        createFolder = { value ->
                            Log.e("CF 2", folder.toString())
                            createFolder(value)
                        },
                    ).apply {
                        setFolderList(folder.docs)
                    }

                if (this.itemDecorationCount < 1) {
                    addItemDecoration(
                        ContentDecoration(
                            itemView.context.dpToPixel(16f).toInt(),
                        ),
                    )
                }
            }
        }
    }
}
