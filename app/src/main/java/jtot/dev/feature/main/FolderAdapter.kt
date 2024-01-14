package jtot.dev.feature.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import jtot.dev.databinding.ItemContentBinding
import jtot.dev.databinding.ItemContentTodoBinding
import jtot.dev.databinding.ItemFolderBinding
import jtot.dev.model.Folder
import jtot.dev.model.Todo

class FolderAdapter() : RecyclerView.Adapter<ViewHolder>() {
    private var folderList = mutableListOf<Any>()

    fun setFolderList(list: List<Any>) {
        folderList = list.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        return when (viewType) {
            1 -> {
                FolderViewHolder(
                    ItemFolderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    ),
                )
            }
            2 -> {
                TodoViewHolder(
                    ItemContentTodoBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    ),
                )
            }
            else -> {
                TextViewHolder(
                    ItemContentBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    ),
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (folderList[position] is Folder) {
            1
        } else if (folderList[position] is Todo) {
            2
        } else {
            3
        }
    }

    override fun getItemCount(): Int = folderList.size

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is TodoViewHolder -> {
                holder.bind(folderList[position] as Todo)
            }
            is TextViewHolder -> {
                holder.bind(folderList[position] as String)
            }
            is FolderViewHolder -> {
                holder.bind(folderList[position] as Folder)
            }
        }
    }
}
