package com.muhia.damaris.interview.weatherapp.utils

import java.util.Calendar
import java.util.Date

fun Date.formatToWeekDay(): Int {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return calendar.get(Calendar.DAY_OF_WEEK) // This returns a value from 1 (Sunday) to 7 (Saturday)
}
fun getDatesForMonth(calendar: Calendar): List<Pair<Date, Boolean>> {
    val tempCalendar = calendar.clone() as Calendar
    tempCalendar.set(Calendar.DAY_OF_MONTH, 1) // Start at the first day of the month

    val currentMonth = tempCalendar.get(Calendar.MONTH)
    val dates = mutableListOf<Pair<Date, Boolean>>()

    // Collect all dates in the current month
    while (tempCalendar.get(Calendar.MONTH) == currentMonth) {
        dates.add(Pair(tempCalendar.time, false)) // Mark all days as 'false' for the signal
        tempCalendar.add(Calendar.DAY_OF_MONTH, 1)
    }
    return dates
}
