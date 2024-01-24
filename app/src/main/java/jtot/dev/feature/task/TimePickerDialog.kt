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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogTimePickerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnConfirm.setOnClickListener {
            val startTime = "${binding.tietStartTimeHourText.text}:${binding.tietStartTimeMinuteText.text}"
            val endTime = "${binding.tietEndTimeHourText.text}:${binding.tietEndTimeMinuteText.text}"
            val breakTime = binding.etBreakTime.text.toString()

            onConfirm(startTime, endTime, breakTime)
            dismiss()
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }
}
