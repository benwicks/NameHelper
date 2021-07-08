package com.exsilicium.namehelper.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exsilicium.namehelper.data.PersonDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonListViewModel @Inject constructor(
    personDao: PersonDao
) : ViewModel() {
    val adapter = PersonRecyclerViewAdapter()
    private val allPeople = personDao.getAllPeople()
    private val isEmpty = allPeople.map { it.isEmpty() }

    init {
        viewModelScope.launch { getAllPeople() }
    }

    private suspend fun getAllPeople() {
        allPeople.collect(adapter::submitList)
    }

    fun isEmpty() = isEmpty
}
