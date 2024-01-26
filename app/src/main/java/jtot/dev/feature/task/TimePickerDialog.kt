package jtot.dev.feature.task

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import jtot.dev.databinding.DialogTimePickerBinding

class TimePickerDialog(
    context: Context,
    private val onConfirm: (String, String, String) -> Unit,
) : AlertDialog(context) {
    private lateinit var binding: DialogTimePickerBinding
    private var startTime = ""
    private var endTime = ""
    private var breakTime = ""

    fun getStartTime() = startTime
    fun getEndTime() = endTime
    fun getBreakTime() = breakTime

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogTimePickerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnConfirm.setOnClickListener {
            startTime = "${binding.tietStartTimeHourText.text}:${binding.tietStartTimeMinuteText.text}"
            endTime = "${binding.tietEndTimeHourText.text}:${binding.tietEndTimeMinuteText.text}"
            breakTime = binding.etBreakTime.text.toString()

            onConfirm(startTime, endTime, breakTime)
            dismiss()
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }
}
