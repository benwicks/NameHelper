package com.exsilicium.namehelper.data

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPerson(person: Person): Long

    @Update
    suspend fun updatePerson(person: Person)

    @Query("SELECT * FROM person WHERE id = :id")
    fun getPerson(id: Int): Flow<Person>

    @Query("SELECT * FROM person")
    fun getAllPeople(): PagingSource<Int, Person>

    @Query("SELECT COUNT(id) FROM person")
    fun getPeopleCount(): Flow<Int>

    @Delete
    suspend fun deletePerson(person: Person)
}
