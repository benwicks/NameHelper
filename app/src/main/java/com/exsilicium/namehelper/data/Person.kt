package com.exsilicium.namehelper.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

// todo does it really make sense for the user to type first/last name separately? What about people who have more than 2 parts to their name?
@Entity
data class Person(
    @ColumnInfo(name = "first_name") val firstName: String?,
    @ColumnInfo(name = "last_name") val lastName: String?,
    @PrimaryKey(autoGenerate = true) val id: Int = DEFAULT_PERSON_ID,
    @ColumnInfo(name = "time_created") val createdTime: Instant = Instant.now(),
    @ColumnInfo(name = "time_last_modified") val lastModifiedTime: Instant = Instant.now(),
) {
    fun getName() = "$firstName $lastName"

    companion object {
        const val DEFAULT_PERSON_ID: Int = 0
    }
}

