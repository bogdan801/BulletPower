package com.bogdan801.bulletpower.data.util

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun getCurrentDateTime(): LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

fun getCurrentTimeStamp(): String {
    val currentDateTime = getCurrentDateTime()
    return "${currentDateTime.hour.toString().padStart(2, '0')}-" +
           "${currentDateTime.minute.toString().padStart(2, '0')}-" +
           "${currentDateTime.second.toString().padStart(2, '0')}_" +
           "${currentDateTime.dayOfMonth.toString().padStart(2, '0')}-" +
           "${currentDateTime.monthNumber.toString().padStart(2, '0')}-" +
            currentDateTime.year.toString().padStart(4, '0')
}