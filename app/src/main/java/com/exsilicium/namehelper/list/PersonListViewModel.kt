package com.exsilicium.namehelper.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.exsilicium.namehelper.data.PersonDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PersonListViewModel @Inject constructor(
        private val personDao: PersonDao
) : ViewModel() {
    fun getAllPeople() = personDao.getAllPeople().asLiveData()
}
