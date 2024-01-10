package jtot.dev.utils

import jtot.dev.model.Schedule
import jtot.dev.model.Todo

fun getTodoList(schedule: Schedule): MutableList<Todo> {
    return mutableListOf<Todo>().apply {
        schedule.todos.forEach { content ->
            if (content is Todo) {
                add(content)
            }
        }
    }
}
