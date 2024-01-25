package jtot.dev.feature.task

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import jtot.dev.databinding.ItemContentBinding
import jtot.dev.databinding.ItemContentTodoBinding
import jtot.dev.databinding.ItemSearchBinding
import jtot.dev.databinding.ItemSearchResultBinding
import jtot.dev.model.Todo

class TaskAdapter(private val onAddTodoClickListener: OnAddTodoClickListener) : RecyclerView.Adapter<ViewHolder>() {
    private var task: List<Any> = listOf()
    private var searchResults: List<Any> = listOf()
    var currentSearchText: String = ""

    companion object {
        private const val TYPE_SEARCH = 0
        private const val TYPE_SEARCH_RESULT = 1
        private const val TYPE_CONTENT_TODO = 2
        private const val TYPE_CONTENT_STRING = 3
    }

    inner class ItemContentStringViewHolder(val binding: ItemContentBinding) :
        ViewHolder(binding.root) {
        fun bind(content: String) {
            binding.etContent.post {
                binding.etContent.setText(content)
                binding.etContent.isFocusable = false
            }
        }
    }

    inner class ItemSearchViewHolder(val binding: ItemSearchBinding) : ViewHolder(binding.root) {
        init {
            binding.etTitle.addTextChangedListener(
                object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        val query = s.toString()
                        currentSearchText = query
                        onSearchQueryChanged?.onSearchQueryChanged(query)
                    }

                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int,
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int,
                    ) {
                    }
                },
            )
        }
    }

    inner class ItemSearchResultViewHolder(val binding: ItemSearchResultBinding) :
        ViewHolder(binding.root) {
        init {
            binding.btnAddTodo.setOnClickListener {
                onAddTodoClickListener.onAddTodoClick(currentSearchText)
            }
        }

        fun bind(todo: Todo?) {
            if (todo != null) {
                binding.todo = todo
                binding.btnAddTodo.visibility = View.GONE
                binding.tvTitle.visibility = View.VISIBLE
                binding.tvReference.visibility = View.VISIBLE
            } else {
                binding.btnAddTodo.visibility = View.VISIBLE
                binding.tvTitle.visibility = View.GONE
                binding.tvReference.visibility = View.GONE
            }
        }
    }

    inner class ItemContentTodoViewHolder(val binding: ItemContentTodoBinding) :
        ViewHolder(binding.root) {
        fun bind(todo: Todo) {
            binding.todo = todo
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        return when (viewType) {
            TYPE_SEARCH -> {
                val binding =
                    ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ItemSearchViewHolder(binding)
            }

            TYPE_SEARCH_RESULT -> {
                val binding =
                    ItemSearchResultBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    )
                ItemSearchResultViewHolder(binding)
            }

            TYPE_CONTENT_TODO -> {
                val binding =
                    ItemContentTodoBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    )
                ItemContentTodoViewHolder(binding)
            }

            TYPE_CONTENT_STRING -> {
                val binding =
                    ItemContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ItemContentStringViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int = task.size + 1 + if (searchResults.isEmpty()) 1 else searchResults.size

    override fun getItemViewType(position: Int): Int {
        return when {
            position < task.size -> {
                when (task[position]) {
                    is Todo -> TYPE_CONTENT_TODO
                    is String -> TYPE_CONTENT_STRING
                    else -> throw IllegalArgumentException("Invalid type")
                }
            }

            position == task.size -> TYPE_SEARCH
            position == task.size + 1 && searchResults.isEmpty() -> TYPE_SEARCH_RESULT
            else -> TYPE_SEARCH_RESULT
        }
    }

    fun updateList(newList: List<Any>) {
        task = newList
        notifyDataSetChanged()
    }

    fun updateSearchResults(newResults: List<Any>) {
        searchResults = newResults
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is ItemContentTodoViewHolder -> {
                // Ensure the object is of type Todo before binding
                if (task[position] is Todo) {
                    holder.bind(task[position] as Todo)
                }
            }

            is ItemSearchViewHolder -> {
                // Bind data for search view
                holder.itemView.post {
                    holder.binding.etTitle.requestFocus()
                }
            }

            is ItemSearchResultViewHolder -> {
                val searchPosition = position - task.size - 1
                if (searchPosition < searchResults.size && searchResults[searchPosition] is Todo) {
                    holder.bind(searchResults[searchPosition] as Todo)
                } else if (searchResults.isEmpty()) {
                    holder.bind(null)
                }
            }

            is ItemContentStringViewHolder -> {
                if (task[position] is String) {
                    holder.bind(task[position] as String)
                }
            }
        }
    }

    interface OnSearchQueryChanged {
        fun onSearchQueryChanged(query: String)
    }

    private var onSearchQueryChanged: OnSearchQueryChanged? = null

    fun setOnSearchQueryChangedListener(listener: OnSearchQueryChanged) {
        onSearchQueryChanged = listener
    }

    interface OnAddTodoClickListener {
        fun onAddTodoClick(title: String)
    }
}
