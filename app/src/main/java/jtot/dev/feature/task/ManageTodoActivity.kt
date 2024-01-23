package jtot.dev.feature.task

import android.os.Bundle
import androidx.appcompat.widget.PopupMenu
import com.google.android.material.datepicker.MaterialDatePicker
import jtot.dev.R
import jtot.dev.base.BaseActivity
import jtot.dev.databinding.ActivityManageTodoBinding
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ManageTodoActivity : BaseActivity<ActivityManageTodoBinding>(R.layout.activity_manage_todo) {
    private lateinit var datePicker: MaterialDatePicker<Long>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // EditText에 포커스 주기
        binding.etTitle.requestFocus()

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
            TimePickerDialog(this) { startTime, endTime, breakTime ->
                binding.tietTimeText.setText("$startTime ~ $endTime break $breakTime minute")
            }.show()
        }

        // Option Menu 아이콘에 클릭 리스너 설정
        binding.ivOptionMenu.setOnClickListener { view ->
            // PopupMenu로 생성하는데 기본적인 Toolbar 내의 PopupMenu로 구현한 것이 아니어서 리플렉션을 활용해서 아이콘을 강제 표시함
            PopupMenu(this, view).apply {
                menuInflater.inflate(R.menu.menu_manage_todo_option_menu, menu)
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
    }
}
