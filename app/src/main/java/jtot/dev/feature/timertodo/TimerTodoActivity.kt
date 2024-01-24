package jtot.dev.feature.timertodo

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import jtot.dev.R
import jtot.dev.base.BaseActivity
import jtot.dev.databinding.ActivityTimerTodoBinding
import jtot.dev.feature.play.ContentAdapter
import jtot.dev.feature.play.decoration.ContentDecoration
import jtot.dev.model.Schedule
import jtot.dev.model.Todo
import jtot.dev.utils.ONE_SECOND
import jtot.dev.utils.dpToPixel
import jtot.dev.utils.getTimeLength
import jtot.dev.utils.getTodoList
import jtot.dev.utils.intentSerializable
import java.util.Timer
import kotlin.concurrent.timer

class TimerTodoActivity : BaseActivity<ActivityTimerTodoBinding>(
    R.layout.activity_timer_todo,
) {
    private val viewModel: TimerViewModel by viewModels()
    private var myTimer = Timer()

    private var timeCount = 0
    private var breakTimeCount = 0

    private val contentAdapter: ContentAdapter by lazy {
        ContentAdapter()
    }
    private var isBreakTime = false
    private var currentTodoIndex = 1
    private var maxTodoIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.intentSerializable("schedule", Schedule::class.java)?.let {
            viewModel.setSchedule(
                it,
            )
            binding.schedule = it
            contentAdapter.setContentList(it.todos)
        }

        maxTodoIndex = getTodoList(viewModel.getSchedule()).size

        val asset = resources.assets.open("jtot-adminsdk.json")
        viewModel.getAccessToken(asset, isSuccess = { accessToken ->
            viewModel.setAccessToken(accessToken)
        })

        val firstTodo = getTodoList(viewModel.getSchedule()).first()
        val firstTime =
            getTimeLength(startTime = firstTodo.startTime, endTime = firstTodo.endTime)

        setTimerInfo(time = firstTime, todo = firstTodo)

        BottomSheetBehavior.from(binding.bottomsheet)
        binding.rvBottomSheet.run {
            adapter = contentAdapter
            addItemDecoration(ContentDecoration(dpToPixel(16f).toInt()))
        }

        binding.btnPlay.setOnClickListener {
            if (!binding.btnPlay.isSelected) {
                binding.btnPlay.isSelected = !binding.btnPlay.isSelected
                // remove soon
                binding.timer.setTime(3)
                val time = Timer()

                if (isBreakTime) {
                    myTimer =
                        timer(period = ONE_SECOND) {
                            val time = binding.timer.getTime()
                            binding.timer.setTime(time - 1)
                            if (time < 0) {
                                if (breakTimeCount > 0) {
                                    breakTimeCount--
                                    binding.timer.setTime(3600)
                                    // remove soon
                                    binding.timer.setTime(3)
                                } else {
                                    Log.e("currentTodoIndex", currentTodoIndex.toString())
                                    Log.e("maxTodoIndex", maxTodoIndex.toString())
                                    if (currentTodoIndex < maxTodoIndex) {
                                        myTimer.cancel()
                                        changeButtonState()
                                        val currentTodo = getTodoList(viewModel.getSchedule())[currentTodoIndex]
                                        val currentTime =
                                            getTimeLength(
                                                startTime = currentTodo.startTime,
                                                endTime = currentTodo.endTime,
                                            )

                                        setTimerInfo(time = currentTime, todo = currentTodo)
                                        Log.e("todo changed", "!!")
                                        currentTodoIndex++
                                        isBreakTime = !isBreakTime
                                        sendNotification(title = "${binding.title} Break Time 완료")
                                    } else {
                                        // 종료
                                        finish()
                                        myTimer.cancel()
                                    }
                                }
                            }
                        }
                } else {
                    myTimer =
                        timer(period = ONE_SECOND) {
                            val time = binding.timer.getTime()
                            binding.timer.setTime(time - 1)
                            if (time < 0) {
                                if (timeCount > 0) {
                                    timeCount--
                                    binding.timer.setTime(3600)
                                    // remove soon
                                    binding.timer.setTime(3)
                                } else {
                                    isBreakTime = !isBreakTime
                                    binding.timer.setCurrentMinArcColor(
                                        ContextCompat.getColor(
                                            this@TimerTodoActivity,
                                            R.color.green,
                                        ),
                                    )
                                    var filtTime = 0
                                    if (binding.todo!!.breakTime.toInt() >= 60) {
                                        timeCount = binding.todo!!.breakTime.toInt() / 60
                                        filtTime = binding.todo!!.breakTime.toInt() % 60
                                        if (filtTime == 0) {
                                            breakTimeCount--
                                            filtTime += 60
                                        }
                                    } else {
                                        filtTime = binding.todo!!.breakTime.toInt()
                                    }

                                    binding.timer.setTime(filtTime * 60)
                                    myTimer.cancel()
                                    changeButtonState()
                                    sendNotification(title = "${binding.title} 완료")
                                    binding.title = "${binding.title} Break Time"
                                }
                            }
                        }
                }
            } else {
                binding.btnPlay.isSelected = !binding.btnPlay.isSelected
                myTimer.cancel()
            }
        }
    }

    private fun setTimerInfo(
        time: Int,
        todo: Todo,
    ) {
        var filtTime = 0
        if (time >= 60) {
            timeCount = time / 60
            filtTime = time % 60
            if (filtTime == 0) {
                timeCount--
                filtTime += 60
            }
        } else {
            filtTime = time
        }
        binding.timer.setTime(filtTime * 60)
        binding.todo = todo
        binding.title = todo.title
        binding.time = "${todo.startTime} ~ ${todo.endTime} (${todo.breakTime}분)"
        binding.timer.setCurrentMinArcColor(
            ContextCompat.getColor(
                this@TimerTodoActivity,
                R.color.red,
            ),
        )
    }

    private fun sendNotification(title: String) {
        viewModel.createFCMToken { deviceToken ->
            viewModel.sendNotifications(
                accessToken = viewModel.getAccessToken(),
                receiveDeviceToken = deviceToken,
                title = title,
                content = binding.time.toString(),
            )
        }
    }

    private fun changeButtonState() {
        runOnUiThread {
            binding.btnPlay.isSelected = !binding.btnPlay.isSelected
        }
    }
}
