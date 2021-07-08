package com.exsilicium.namehelper.list

import androidx.lifecycle.ViewModel
import com.exsilicium.namehelper.data.PersonDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class PersonListViewModel @Inject constructor(
    private val personDao: PersonDao
) : ViewModel() {
    val adapter = PersonRecyclerViewAdapter()

    suspend fun getAllPeople() {
        personDao.getAllPeople().collect(adapter::submitList)
    }
}
