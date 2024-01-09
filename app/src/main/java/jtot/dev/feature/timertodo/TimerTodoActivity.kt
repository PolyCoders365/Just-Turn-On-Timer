package jtot.dev.feature.timertodo

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import jtot.dev.R
import jtot.dev.base.BaseActivity
import jtot.dev.databinding.ActivityTimerTodoBinding
import jtot.dev.model.Schedule
import jtot.dev.model.Todo
import jtot.dev.utils.getFiltMinute
import java.util.Timer
import kotlin.concurrent.timer

class TimerTodoActivity : BaseActivity<ActivityTimerTodoBinding>(
    R.layout.activity_timer_todo,
) {
    private val viewModel: TimerViewModel by viewModels()
    private var timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.setSchedule(
            intent.getSerializableExtra("schedule") as Schedule,
        )
        val todo = getTodoList(viewModel.getSchedule()).first()
        var minute = getFiltMinute(startTime = todo.startTime, endTime = todo.endTime)
        var period = minute * 60
        binding.timer.setTime(minute)
        binding.iconButton.setOnClickListener {
            if (!binding.iconButton.isSelected) {
                binding.iconButton.isSelected = !binding.iconButton.isSelected
                timer =
                    timer(period = 1000) {
                        period--
                        Log.e("period", "min: ${period / 60}, sec: ${period - (period / 60) * 60}")
                        binding.timer.setTime(period / 60)
                    }
            } else {
                binding.iconButton.isSelected = !binding.iconButton.isSelected
                timer.cancel()
            }
        }
    }

    private fun getTodoList(schedule: Schedule): MutableList<Todo> {
        val todoList = mutableListOf<Todo>()
        schedule.todos.forEach { content ->
            if (content is Todo) {
                todoList.add(content)
            }
        }
        return todoList
    }
}
