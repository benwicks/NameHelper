package com.exsilicium.namehelper.modify

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.exsilicium.namehelper.data.Person
import com.exsilicium.namehelper.data.PersonDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

@HiltViewModel
class PersonModifyViewModel @Inject constructor(
        private val personDao: PersonDao
) : ViewModel() {
    suspend fun save(firstName: String, lastName: String) {
        personDao.insertPerson(Person(firstName = firstName, lastName = lastName))
    }

    fun lookupPerson(personId: Int) = liveData {
        if (personId == -1) {
            emit(null)
        } else {
            emit(personDao.getPerson(personId).firstOrNull())
        }
    }
}
