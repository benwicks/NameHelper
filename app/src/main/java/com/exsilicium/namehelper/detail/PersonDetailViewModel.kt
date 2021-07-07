package com.exsilicium.namehelper.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.exsilicium.namehelper.data.Person
import com.exsilicium.namehelper.data.PersonDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonDetailViewModel @Inject constructor(
    private val personDao: PersonDao
) : ViewModel() {
    private var person: Person? = null

    fun lookupPerson(personId: Int): LiveData<Person?> {
        val personFlow = personDao.getPerson(personId)
        GlobalScope.launch {
            this@PersonDetailViewModel.person = personFlow.firstOrNull()
        }
        return personFlow.asLiveData()
    }

    suspend fun deletePerson() {
        person?.let {
            personDao.deletePerson(it)
        }
    }
}
