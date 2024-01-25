package jtot.dev.feature.main

import androidx.recyclerview.widget.RecyclerView
import jtot.dev.databinding.ItemContentScheduleBinding
import jtot.dev.model.Schedule

class ScheduleViewHolder(
    private val binding: ItemContentScheduleBinding,
) : RecyclerView.ViewHolder(binding.root) {
    val layoutBlock = binding.layoutBlock

    fun bind(schedule: Schedule) {
        binding.schedule = schedule
    }
}
