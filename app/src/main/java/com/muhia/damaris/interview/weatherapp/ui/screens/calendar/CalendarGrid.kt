package com.muhia.damaris.interview.weatherapp.ui.screens.calendar

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.muhia.damaris.interview.weatherapp.utils.formatToWeekDay
import okhttp3.internal.toImmutableList
import java.util.*

@Composable
 fun CalendarGrid(
    date: List<Pair<Date, Boolean>>,
    onClick: (Date) -> Unit,
    startFromSunday: Boolean,
    modifier: Modifier = Modifier,
) {
    val weekdayFirstDay = date.first().first.formatToWeekDay()
    val weekdays = getWeekDays(startFromSunday)
    CalendarCustomLayout(modifier = modifier) {
        weekdays.forEach {
            WeekdayCell(weekday = it)
        }
        // Adds Spacers to align the first day of the month to the correct weekday
        repeat(if (!startFromSunday) weekdayFirstDay - 2 else weekdayFirstDay - 1) {
            Spacer(modifier = Modifier)
        }
        date.forEach {
            CalendarCell(date = it.first, signal = it.second, onClick = { onClick(it.first) })
        }
    }
}

fun getWeekDays(startFromSunday: Boolean): List<Int> {
    val lista = (1..7).toList()
    return (if (startFromSunday) lista else lista.drop(1) + lista.take(1)).toImmutableList()
}


