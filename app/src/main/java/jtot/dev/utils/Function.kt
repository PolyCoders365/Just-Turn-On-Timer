package jtot.dev.utils

import jtot.dev.model.Schedule
import jtot.dev.model.Todo
import java.util.Calendar
import java.util.Locale

fun getTodoList(schedule: Schedule): MutableList<Todo> {
    return mutableListOf<Todo>().apply {
        schedule.contents.forEach { content ->
            if (content is Todo) {
                add(content)
            }
        }
    }
}

fun getToday(): String {
    val cal = Calendar.getInstance(Locale.KOREA)
    return cal.time.convertToFormat()
}
