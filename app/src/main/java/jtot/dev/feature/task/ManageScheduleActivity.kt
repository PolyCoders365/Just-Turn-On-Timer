package jtot.dev.feature.task

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.PopupMenu
import com.google.android.material.datepicker.MaterialDatePicker
import jtot.dev.R
import jtot.dev.base.BaseActivity
import jtot.dev.databinding.ActivityManageScheduleBinding
import jtot.dev.model.Todo
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ManageScheduleActivity : BaseActivity<ActivityManageScheduleBinding>(R.layout.activity_manage_schedule) {
    private lateinit var datePicker: MaterialDatePicker<Long>
    private lateinit var taskAdapter: TaskAdapter
    private val manageScheduleViewModel: ManageTodoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // DatePicker 초기화
        datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select date").build()

        // DatePicker에서 날짜 선택 시 이벤트 처리
        datePicker.addOnPositiveButtonClickListener { selection ->
            // 선택한 날짜를 TextField에 표시
            binding.tvTitle.text =
                Instant.ofEpochMilli(
                    selection,
                ).atZone(ZoneId.systemDefault()).toLocalDate()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        }

        binding.ivCalendar.setOnClickListener {
            datePicker.show(supportFragmentManager, datePicker.toString())
        }

        // Option Menu 아이콘에 클릭 리스너 설정
        binding.ivOptionMenu.setOnClickListener { view ->
            // PopupMenu로 생성하는데 기본적인 Toolbar 내의 PopupMenu로 구현한 것이 아니어서 리플렉션을 활용해서 아이콘을 강제 표시함
            PopupMenu(this, view).apply {
                menuInflater.inflate(R.menu.menu_manage_schedule_option_menu, menu)
                try {
                    val fields = menu.javaClass.declaredFields
                    for (field in fields) {
                        if ("mOptionalIconsVisible" == field.name) {
                            field.isAccessible = true
                            field.setBoolean(menu, true)
                            break
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                show()
            }
        }

        taskAdapter =
            TaskAdapter(
                object : TaskAdapter.OnAddTodoClickListener {
                    override fun onAddTodoClick(title: String) {
                        manageScheduleViewModel.addTodo(Todo(title))
                    }
                },
            )

        binding.rvSchedule.adapter = taskAdapter

        // Observe the LiveData
        manageScheduleViewModel.todoList.observe(this) { todoList ->
            taskAdapter.updateList(todoList)
        }

        taskAdapter.setOnSearchQueryChangedListener(
            object : TaskAdapter.OnSearchQueryChanged {
                override fun onSearchQueryChanged(query: String) {
                    manageScheduleViewModel.search(query)
                }
            },
        )

        manageScheduleViewModel.searchResults.observe(this) { results ->
            taskAdapter.updateSearchResults(results)
        }
    }
}
