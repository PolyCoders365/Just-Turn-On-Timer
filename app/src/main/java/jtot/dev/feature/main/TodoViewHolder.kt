package jtot.dev.feature.main

import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import jtot.dev.R
import jtot.dev.databinding.ItemContentTodoBinding
import jtot.dev.feature.play.ContentAdapter
import jtot.dev.feature.play.decoration.ContentDecoration
import jtot.dev.feature.play.decoration.ToggleAnimation
import jtot.dev.model.Todo
import jtot.dev.utils.dpToPixel

class TodoViewHolder(
    private val binding: ItemContentTodoBinding,
) : RecyclerView.ViewHolder(binding.root) {
    private var isExpand = false
    var btnMore = binding.btnMore
    var layoutBlock = binding.layoutBlock

    fun bind(todo: Todo) {
        binding.todo = todo
        binding.time = "${todo.startTime} ~ ${todo.endTime}, ${todo.breakTime}분"

        View.OnClickListener {
            ToggleAnimation.toggleArrow(view = binding.btnMore, isExpanded = isExpand)
            if (isExpand) {
                ToggleAnimation.expand(binding.layoutExpand)
                binding.layoutBlock.background =
                    ContextCompat.getDrawable(
                        binding.root.context,
                        R.drawable.background_todo_top_expand,
                    )
            } else {
                ToggleAnimation.collapse(binding.layoutExpand)
                binding.layoutBlock.background =
                    ContextCompat.getDrawable(binding.root.context, R.drawable.background_todo_top)
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
