package jtot.dev.feature.task

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import jtot.dev.databinding.ItemContentTodoBinding
import jtot.dev.databinding.ItemSearchBinding
import jtot.dev.databinding.ItemSearchResultBinding

class TaskAdapter : RecyclerView.Adapter<ViewHolder>() {
    private var task = mutableListOf<Any>()
    private var selectedItemPosition = -1 // Add this line

    companion object {
        private const val TYPE_SEARCH = 0
        private const val TYPE_SEARCH_RESULT = 1
        private const val TYPE_CONTENT_TODO = 2
    }

    inner class ItemSearchView(val binding: ItemSearchBinding) : ViewHolder(binding.root)

    inner class ItemSearchResultView(val binding: ItemSearchResultBinding) : ViewHolder(binding.root)

    inner class ItemContentTodoView(val binding: ItemContentTodoBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        return when (viewType) {
            TYPE_SEARCH -> {
                val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ItemSearchView(binding)
            }
            TYPE_SEARCH_RESULT -> {
                val binding = ItemSearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ItemSearchResultView(binding)
            }
            TYPE_CONTENT_TODO -> {
                val binding = ItemContentTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ItemContentTodoView(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int = task.size + 1

    override fun getItemViewType(position: Int): Int {
        // Update this method to return the correct view type based on your conditions
        // For example, if task[position] is a certain class, return TYPE_CONTENT_TODO
        return when {
            position == 0 -> TYPE_SEARCH
            position == selectedItemPosition -> TYPE_CONTENT_TODO
            else -> TYPE_SEARCH_RESULT
        }
    }

    fun selectItem(position: Int) {
        selectedItemPosition = position
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is ItemSearchView -> {
                // Bind data for search view
            }
            is ItemSearchResultView -> {
                // Bind data for search result view
            }
            is ItemContentTodoView -> {
                // Bind data for content todo view
            }
        }
    }
}
