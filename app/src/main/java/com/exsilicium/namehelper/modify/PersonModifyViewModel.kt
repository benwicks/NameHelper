package com.exsilicium.namehelper.modify

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.exsilicium.namehelper.data.Person
import com.exsilicium.namehelper.data.Person.Companion.DEFAULT_PERSON_ID
import com.exsilicium.namehelper.data.PersonDao
import com.exsilicium.namehelper.databinding.PersonModifyFragmentBinding
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.firstOrNull
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class PersonModifyViewModel @Inject constructor(
    private val personDao: PersonDao
) : ViewModel() {
    private var personId: Int = DEFAULT_PERSON_ID
    private var person: Person? = null

    fun init(personId: Int) {
        this.personId = personId
    }

    suspend fun save(coroutineScope: CoroutineScope, binding: PersonModifyFragmentBinding) {
        val firstName = binding.etFirstName.text.toString()
        val lastName = binding.etLastName.text.toString()
        if (!isInputValid(firstName, lastName)) {
            coroutineScope.cancel("Invalid Input", InvalidInputException())
            return
        }

        person = if (person == null) {
            Person(firstName, lastName, personId)
        } else {
            person?.copy(
                firstName = firstName,
                lastName = lastName,
                lastModifiedTime = Instant.now()
            )
        }
        person?.let { it ->
            if (personDao.insertPerson(it) == -1L) {
                personDao.updatePerson(it)
            }
        }
    }

    fun lookupPerson() = liveData {
        if (personId == 0) {
            emit(null)
        } else {
            val personFlow = personDao.getPerson(personId)
            person = personFlow.firstOrNull()
            emit(person)
        }
    }

    companion object {
        @VisibleForTesting
        fun isInputValid(firstName: String, lastName: String): Boolean {
            return firstName.isNotBlank() || lastName.isNotBlank()
        }
    }
}
