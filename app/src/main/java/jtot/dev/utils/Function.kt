package jtot.dev.utils

import jtot.dev.model.Schedule
import jtot.dev.model.Todo
import java.util.Calendar
import java.util.Locale
import java.util.Random

fun getTodoList(schedule: Schedule): MutableList<Todo> {
    return mutableListOf<Todo>().apply {
        schedule.todos.forEach { content ->
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

fun randomNum(): Int  {
    val random = Random()
    return random.nextInt(10000)
}
