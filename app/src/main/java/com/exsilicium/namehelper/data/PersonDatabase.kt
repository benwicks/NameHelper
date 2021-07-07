package com.exsilicium.namehelper.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
        entities = [
            Person::class
        ],
        version = 1
)
abstract class PersonDatabase : RoomDatabase() {
    abstract fun getPersonDao(): PersonDao

    companion object {
        @Volatile
        private var INSTANCE: PersonDatabase? = null

        fun getDatabase(context: Context): PersonDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, PersonDatabase::class.java, "person_database").build()
                INSTANCE!!
            }
        }
    }
}
