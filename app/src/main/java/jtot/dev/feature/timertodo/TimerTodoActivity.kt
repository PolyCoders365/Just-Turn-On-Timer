package jtot.dev.feature.timertodo

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import jtot.dev.R
import jtot.dev.base.BaseActivity
import jtot.dev.databinding.ActivityTimerTodoBinding
import jtot.dev.model.Schedule
import jtot.dev.utils.ONE_SECOND
import jtot.dev.utils.getTimeLength
import jtot.dev.utils.getTodoList
import jtot.dev.utils.intentSerializable
import java.util.Timer
import kotlin.concurrent.timer

class TimerTodoActivity : BaseActivity<ActivityTimerTodoBinding>(
    R.layout.activity_timer_todo,
) {
    private val viewModel: TimerViewModel by viewModels()
    private var timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.intentSerializable("schedule", Schedule::class.java)?.let {
            viewModel.setSchedule(
                it,
            )
        }
        val todo = getTodoList(viewModel.getSchedule()).first()
        val timeMinute = getTimeLength(startTime = todo.startTime, endTime = todo.endTime)
        var totalSecond = timeMinute * 60

        binding.timer.setTime(timeMinute)
        binding.title = todo.title
        BottomSheetBehavior.from(binding.bottomsheet)
        binding.btnPlay.setOnClickListener {
            if (!binding.btnPlay.isSelected) {
                binding.btnPlay.isSelected = !binding.btnPlay.isSelected
                timer =
                    timer(period = ONE_SECOND) {
                        totalSecond--
                        Log.e("current remain time", "min: ${totalSecond / 60}, sec: ${totalSecond - (totalSecond / 60) * 60}")
                        binding.timer.setTime(totalSecond / 60)
                    }
            } else {
                binding.btnPlay.isSelected = !binding.btnPlay.isSelected
                timer.cancel()
            }
        }
    }
}
