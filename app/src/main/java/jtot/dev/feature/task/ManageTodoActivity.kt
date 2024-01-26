package jtot.dev.feature.task

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.widget.PopupMenu
import com.google.android.material.datepicker.MaterialDatePicker
import jtot.dev.R
import jtot.dev.base.BaseActivity
import jtot.dev.databinding.ActivityManageTodoBinding
import jtot.dev.model.Todo
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ManageTodoActivity : BaseActivity<ActivityManageTodoBinding>(R.layout.activity_manage_todo) {
    private lateinit var datePicker: MaterialDatePicker<Long>
    private val timePickerDialog: TimePickerDialog by lazy {
        TimePickerDialog(
            this,
            onConfirm = { startTime, endTime, breakTime ->
                binding.tietTimeText.setText("$startTime ~ $endTime break $breakTime minute")
            },
        )
    }
    private val viewModel: ManageTodoViewModel by viewModels()
    private val taskAdapter: TaskAdapter by lazy {
        TaskAdapter(
            findTodo = {
                //
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // DatePicker 초기화
        datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select date").build()

        // DatePicker에서 날짜 선택 시 이벤트 처리
        datePicker.addOnPositiveButtonClickListener { selection ->
            // 선택한 날짜를 TextField에 표시
            binding.tietDateText.setText(
                Instant.ofEpochMilli(
                    selection,
                ).atZone(ZoneId.systemDefault()).toLocalDate()
                    .format(DateTimeFormatter.ofPattern("MM/dd/yyyy")),
            )
        }

        binding.tietDateText.setOnClickListener {
            binding.tilDatePicker.performClick()
        }

        binding.tietTimeText.setOnClickListener {
            binding.tilTimePicker.performClick()
        }

        // 아이콘에 클릭 리스너 설정
        binding.tilDatePicker.setOnClickListener {
            datePicker.show(supportFragmentManager, datePicker.toString())
        }

        binding.tilTimePicker.setOnClickListener {
            timePickerDialog.show()
        }

        // Option Menu 아이콘에 클릭 리스너 설정
        binding.ivOptionMenu.setOnClickListener { view ->
            // PopupMenu로 생성하는데 기본적인 Toolbar 내의 PopupMenu로 구현한 것이 아니어서 리플렉션을 활용해서 아이콘을 강제 표시함
            PopupMenu(this, view).apply {
                menuInflater.inflate(R.menu.menu_manage_todo_option_menu, menu)
                setForceShowIcon(true)

                show()
            }
        }

//        taskAdapter =
//            TaskAdapter(
//                object : TaskAdapter.OnAddTodoClickListener {
//                    override fun onAddTodoClick(title: String) {
//                        manageTodoViewModel.addTodo(Todo(title))
//                    }
//                },
//            )

        binding.rvTodo.adapter = taskAdapter

        // Observe the LiveData
        viewModel.todoList.observe(this) { todoList ->
            taskAdapter.updateList(todoList)
        }

        taskAdapter.setOnSearchQueryChangedListener(
            object : TaskAdapter.OnSearchQueryChanged {
                override fun onSearchQueryChanged(query: String) {
                    viewModel.search(query)
                }
            },
        )

        viewModel.searchResults.observe(this) { results ->
            taskAdapter.updateSearchResults(results)
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            binding.etTitle.requestFocus()
        }
    }

    override fun onStop() {
        super.onStop()

        val saveTodo = Todo(
            title = binding.etTitle.text.toString(),
            date = binding.tietDateText.text.toString(),
            startTime = timePickerDialog.getStartTime(),
            endTime = timePickerDialog.getEndTime(),
            breakTime = timePickerDialog.getBreakTime(),
            contents = taskAdapter.getContentsList()
        )

        viewModel.setTempTodo(saveTodo)
        Log.e("savedTodo Check", viewModel.getTempTodo().toString())
    }
}
