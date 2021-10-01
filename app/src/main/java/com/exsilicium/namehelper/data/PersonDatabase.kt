package com.exsilicium.namehelper.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    version = 1,
    entities = [
        Person::class
    ],
)
@TypeConverters(Converters::class)
abstract class PersonDatabase : RoomDatabase() {
    abstract fun getPersonDao(): PersonDao

    companion object {
        @Volatile
        private var INSTANCE: PersonDatabase? = null

        fun getDatabase(context: Context): PersonDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    PersonDatabase::class.java,
                    "person_database"
                ).build()
                INSTANCE!!
            }
        }
    }
}
