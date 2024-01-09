package jtot.dev.feature.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import jtot.dev.databinding.ItemContentFolderBinding
import jtot.dev.model.Folder

class TodosAdapter() : RecyclerView.Adapter<TodosAdapter.TodosViewHolder>() {
    private var todosList = mutableListOf<Folder>()

    fun setTodosList(list: List<Folder>) {
        todosList = list.toMutableList()
        notifyDataSetChanged()
    }

    inner class TodosViewHolder(
        private val binding: ItemContentFolderBinding,
    ) : ViewHolder(binding.root) {
        fun bind(folder: Folder) {
            binding.folder = folder
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TodosViewHolder {
        return TodosViewHolder(
            ItemContentFolderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    override fun getItemCount(): Int = todosList.size

    override fun onBindViewHolder(
        holder: TodosViewHolder,
        position: Int,
    ) {
        holder.bind(todosList[position])
    }
}
