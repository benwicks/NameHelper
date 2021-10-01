package com.exsilicium.namehelper.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant


@Entity
data class Person(
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "notes") val notes: String?,
    @PrimaryKey(autoGenerate = true) val id: Int = DEFAULT_PERSON_ID,
    @ColumnInfo(name = "time_created") val createdTime: Instant = Instant.now(),
    @ColumnInfo(name = "time_last_modified") val lastModifiedTime: Instant = Instant.now(),
) {
    companion object {
        const val DEFAULT_PERSON_ID: Int = 0
    }
}

