package com.exsilicium.namehelper.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.exsilicium.namehelper.data.Person
import com.exsilicium.namehelper.data.PersonDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonListViewModel @Inject constructor(
    personDao: PersonDao
) : ViewModel() {
    val adapter = PersonRecyclerViewAdapter()

    private val allPeople: Flow<PagingData<Person>> = Pager(
        config = PagingConfig(
            pageSize = 60
        )
    ) { personDao.getAllPeople() }.flow
    private val isEmpty = personDao.getPeopleCount()

    init {
        viewModelScope.launch { getAllPeople() }
    }

    private suspend fun getAllPeople() {
        allPeople.collectLatest(adapter::submitData)
    }

    fun isEmpty() = isEmpty.map { it == 0 }.distinctUntilChanged()
}
