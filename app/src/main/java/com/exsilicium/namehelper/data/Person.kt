package com.exsilicium.namehelper.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Person(
        @ColumnInfo(name = "first_name") val firstName: String?,
        @ColumnInfo(name = "last_name") val lastName: String?,
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
) {
    fun getName() = "$firstName $lastName"
}

