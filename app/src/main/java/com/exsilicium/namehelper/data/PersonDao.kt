package com.exsilicium.namehelper.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPerson(person: Person)

    @Query("SELECT * FROM person WHERE id = :id")
    fun getPerson(id: Int): Flow<Person>

    @Query("SELECT * FROM person")
    fun getAllPeople(): Flow<List<Person>>

    @Delete
    suspend fun deletePerson(person: Person)
}
