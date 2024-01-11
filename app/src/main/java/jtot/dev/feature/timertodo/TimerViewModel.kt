package jtot.dev.feature.timertodo

import jtot.dev.base.BaseViewModel
import jtot.dev.model.Schedule

class TimerViewModel : BaseViewModel() {
    private lateinit var schedule: Schedule

    fun setSchedule(schedule: Schedule) {
        this.schedule = schedule
    }

    fun getSchedule() = schedule
}
