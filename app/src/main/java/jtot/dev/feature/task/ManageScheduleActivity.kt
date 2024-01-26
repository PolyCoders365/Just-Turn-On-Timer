package jtot.dev.feature.task

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.PopupMenu
import com.google.android.material.datepicker.MaterialDatePicker
import jtot.dev.R
import jtot.dev.base.BaseActivity
import jtot.dev.databinding.ActivityManageScheduleBinding
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ManageScheduleActivity : BaseActivity<ActivityManageScheduleBinding>(R.layout.activity_manage_schedule) {
    private val datePicker: MaterialDatePicker<Long> by lazy {
        MaterialDatePicker.Builder.datePicker().setTitleText("Select date").build()
    }
    private val taskAdapter: TaskAdapter by lazy {
        TaskAdapter(findTodo = { query ->
            viewModel.search(query)
            // 전체 데이터에서 query를 포함한 데이터를 가져오고, 그 가져온 값을 Search view에다가 적용시킨다.
        })
    }
    private val viewModel: ManageTodoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.rvSchedule.adapter = taskAdapter

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
                setForceShowIcon(true)
                show()
            }
        }

//
//        taskAdapter.setOnSearchQueryChangedListener(
//            object : TaskAdapter.OnSearchQueryChanged {
//                override fun onSearchQueryChanged(query: String) {
//                    manageScheduleViewModel.search(query)
//                }
//            },
//        )
        // Observe the LiveData
        viewModel.todoList.observe(this) { todoList ->
            taskAdapter.updateList(todoList)
        }
        viewModel.searchResults.observe(this) { results ->
            taskAdapter.updateSearchResults(results)
        }
    }
}
