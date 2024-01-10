package jtot.dev.utils

/**
 * 더미 문자열 반환
 */
fun createStringDummy() = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor"

/**
 * startTime과 endTime 사이의 시간이 몇 분인지 반환
 */
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
