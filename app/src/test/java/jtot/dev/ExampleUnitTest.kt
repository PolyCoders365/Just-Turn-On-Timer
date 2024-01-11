package jtot.dev

import jtot.dev.model.Schedule
import jtot.dev.model.Todo
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun scheduleTest() {
        val schedule = Schedule().createDummy()
        val todoList = getTodoList(schedule)
        val todo = todoList.first()
        val minute = getTimeLength(startTime = todo.startTime, endTime = todo.endTime)
        assertEquals(60, minute)
    }

    @Test
    fun timeFilter() {
        val startTime = "10:00"
        val endTime = "11:30"

        assertEquals(90, getTimeLength(startTime, endTime))
    }

    fun getTimeLength(
        startTime: String,
        endTime: String,
    ): Int {
        val startHour = startTime.split(":")[0].toInt()
        val startMinute = startTime.split(":")[1].toInt()

        val endHour = endTime.split(":")[0].toInt()
        val endMinute = endTime.split(":")[1].toInt()

        val startTotalMinute = startHour * 60 + startMinute
        val endTotalMinute = endHour * 60 + endMinute

        return endTotalMinute - startTotalMinute
    }

    private fun getTodoList(schedule: Schedule): MutableList<Todo> {
        return mutableListOf<Todo>().apply {
            schedule.todos.forEach { content ->
                if (content is Todo) {
                    add(content)
                }
            }
        }
    }
}
