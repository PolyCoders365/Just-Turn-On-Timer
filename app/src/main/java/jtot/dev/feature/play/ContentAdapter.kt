package jtot.dev.feature.play

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import jtot.dev.databinding.ItemContentBinding
import jtot.dev.databinding.ItemContentTodoBinding
import jtot.dev.feature.main.TextViewHolder
import jtot.dev.feature.main.TodoViewHolder
import jtot.dev.model.Todo

/**
 *
 * viewtype에 따라 표시되는 View 변경
 *
 * 1 ->  TodoViewHolder
 *
 * else -> TextViewHolder
 *
 */
class ContentAdapter() : RecyclerView.Adapter<ViewHolder>() {
    private var contentList = mutableListOf<Any>()

    fun setContentList(list: List<Any>) {
        contentList = list.toMutableList()
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (contentList[position] is Todo) {
            1
        } else {
            2
        }
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is TodoViewHolder -> {
                holder.bind(contentList[position] as Todo)
            }
            is TextViewHolder -> {
                holder.bind(contentList[position] as String)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        return when (viewType) {
            1 -> {
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

    override fun getItemCount(): Int = contentList.size
}
