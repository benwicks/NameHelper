package com.exsilicium.namehelper.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.exsilicium.namehelper.data.Person
import com.exsilicium.namehelper.data.PersonDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class PersonDetailViewModel @Inject constructor(
    private val personDao: PersonDao
) : ViewModel() {
    private var personId by Delegates.notNull<Int>()
    private var person: Person? = null

    fun init(personId: Int) {
        this.personId = personId
    }

    fun lookupPerson(): Flow<Person?> {
        val personFlow = personDao.getPerson(personId)
        GlobalScope.launch {
            this@PersonDetailViewModel.person = personFlow.firstOrNull()
        }
        return personFlow
    }

    suspend fun deletePerson() {
        person?.let {
            personDao.deletePerson(it)
        }
    }
}
