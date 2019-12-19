package com.ibenew.sunflower.data

import androidx.room.TypeConverter
import java.util.*

/**
 * Create by wuyt on 2019/12/17 17:22
 * {@link }
 */
class Converters {
    @TypeConverter
    fun calendarToDatestamp(calendar: Calendar): Long = calendar.timeInMillis

    @TypeConverter
    fun datestampToCalendar(value: Long): Calendar =
        Calendar.getInstance().apply { timeInMillis = value }
}