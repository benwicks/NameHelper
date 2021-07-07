package com.exsilicium.namehelper.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.exsilicium.namehelper.data.PersonDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PersonDetailViewModel @Inject constructor(
        private val personDao: PersonDao
) : ViewModel() {
    fun lookupPerson(personId: Int) = personDao.getPerson(personId).asLiveData()
}
