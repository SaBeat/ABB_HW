package com.vholodynskyi.assignment.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vholodynskyi.assignment.api.contacts.ApiContact
import com.vholodynskyi.assignment.di.GlobalFactory
import com.vholodynskyi.assignment.repo.ContactRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailsViewModel: ViewModel() {

    private val _contactList = MutableLiveData<List<ApiContact>>()
    var contactList :LiveData<List<ApiContact>> = _contactList
    private val repository: ContactRepository
    private val contactsService = GlobalFactory.service

    init {
        val dao = GlobalFactory.db.userDao()
        repository= ContactRepository(contactsService,dao)
        getAllContactList()
    }

    fun getAllContactList() = viewModelScope.launch {
        repository.getContactList().collect {appContactsList ->
            _contactList.value = appContactsList
        }
    }

}
