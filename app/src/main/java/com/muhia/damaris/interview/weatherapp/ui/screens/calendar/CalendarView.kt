package com.muhia.damaris.interview.weatherapp.ui.screens.calendar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun CalendarView(
    month: Date,
    date:  List<Pair<Date, Boolean>>,
    displayNext: Boolean,
    displayPrev: Boolean,
    onClickNext: () -> Unit,
    onClickPrev: () -> Unit,
    onClick: (Date) -> Unit,
    startFromSunday: Boolean,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Box(modifier = Modifier.fillMaxWidth()) {
            if (displayPrev)
                CustomIconButton(
                    onClick = onClickPrev,
                    modifier = Modifier.align(Alignment.CenterStart),
                    icon = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "navigate to previous month"
                )
            if (displayNext)
                CustomIconButton(
                    onClick = onClickNext,
                    modifier = Modifier.align(Alignment.CenterEnd),
                    icon = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "navigate to next month"
                )
            Text(
                text = month.formatToMonthString(),
                style = typography.headlineMedium,
                color = colorScheme.onPrimaryContainer,
                modifier = Modifier.align(Alignment.Center),
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        if (date.isNotEmpty()) {
            CalendarGrid(
                date = date,
                onClick = onClick,
                startFromSunday = startFromSunday,
                modifier = Modifier
                    .wrapContentHeight()
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}


@Composable
fun CustomIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector,
    contentDescription: String? = null
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription
        )
    }
}


fun Date.formatToMonthString(): String = SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(this)