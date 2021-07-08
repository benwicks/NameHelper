package com.exsilicium.namehelper.data

import androidx.room.TypeConverter
import java.time.Instant

class Converters {
    @TypeConverter
    fun fromString(value: String?): Instant? {
        return value?.let(Instant::parse)
    }

    @TypeConverter
    fun fromInstant(value: Instant?): String? {
        return value?.toString()
    }
}
