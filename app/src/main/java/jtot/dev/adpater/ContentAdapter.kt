package jtot.dev.adpater

import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import jtot.dev.R
import jtot.dev.databinding.ItemContentBinding
import jtot.dev.databinding.ItemContentTodoBinding
import jtot.dev.decoration.ContentDecoration
import jtot.dev.decoration.ToggleAnimation
import jtot.dev.model.Todo
import jtot.dev.utils.dpToPixel

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

    inner class TextViewHolder(
        private val binding: ItemContentBinding,
    ) : ViewHolder(binding.root) {
        fun bind(content: String) {
            binding.content = content
        }
    }

    inner class TodoViewHolder(
        private val binding: ItemContentTodoBinding,
    ) : ViewHolder(binding.root) {
        private var isExpand = false

        fun bind(todo: Todo) {
            binding.todo = todo

            OnClickListener {
                ToggleAnimation.toggleArrow(view = binding.btnMore, isExpanded = isExpand)
                if (isExpand) {
                    ToggleAnimation.expand(binding.layoutExpand)
                    binding.layoutBlock.background =
                        ContextCompat.getDrawable(
                            binding.root.context,
                            R.drawable.background_todo_expand,
                        )
                } else {
                    ToggleAnimation.collapse(binding.layoutExpand)
                    binding.layoutBlock.background =
                        ContextCompat.getDrawable(binding.root.context, R.drawable.background_todo)
                }

                isExpand = !isExpand
                binding.rvNestContent.run {
                    adapter =
                        ContentAdapter().apply {
                            setContentList(todo.todos)
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
                binding.layoutBlock.setOnClickListener(this)
            }

            if (!todo.star) {
                // star 아이콘 삭제
                (binding.btnStar.parent as ViewGroup).removeView(binding.btnStar)

                // titleTextView constraint를 parent인 layout_block으로 변경
                binding.tvTitle.updateLayoutParams<ConstraintLayout.LayoutParams> {
                    startToStart = R.id.layout_block
                }
            }
        }
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
        if (holder is TodoViewHolder) {
            holder.bind(contentList[position] as Todo)
        }
        if (holder is TextViewHolder) {
            holder.bind(contentList[position] as String)
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
