package com.vholodynskyi.assignment.ui.contactslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vholodynskyi.assignment.api.contacts.ApiContact
import com.vholodynskyi.assignment.db.contacts.DbContact
import com.vholodynskyi.assignment.di.GlobalFactory
import com.vholodynskyi.assignment.repo.ContactRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ContactsListViewModel(
) : ViewModel() {

    private val _contactList = MutableLiveData<List<ApiContact>>()
    var contactList :LiveData<List<ApiContact>> = _contactList

//    private val _contactListFromDB = MutableLiveData<List<DbContact>>()
//    var contactListFromDB :LiveData<List<DbContact>> = _contactListFromDB

    private val _contactFromDB = MutableLiveData<DbContact>()
    var contactFromDB :LiveData<DbContact> = _contactFromDB

    private val repository:ContactRepository
    private val contactsService = GlobalFactory.service

    init {
        val dao = GlobalFactory.db.userDao()
        repository= ContactRepository(contactsService,dao)
        getAllContactList()
        getAllContactListFromDB()
    }

    fun getAllContactList() = viewModelScope.launch {
        repository.getContactList().collect {appContactsList ->
            _contactList.value = appContactsList
        }
    }

    fun getAllContactListFromDB():MutableList<DbContact> {
        return GlobalFactory.db.userDao().getContacts()
    }

    fun getContactById(contactId: Int):DbContact {
       return  GlobalFactory.db.userDao().getContactById(contactId)
    }

    fun insertContactList(dbContactList: MutableList<DbContact>) = viewModelScope.launch {
        repository.insertContactList(dbContactList)
    }

    fun deleteAllContactList() = viewModelScope.launch {
        repository.deleteAllContacts()
    }

    fun insertContact(dbContact: DbContact) = viewModelScope.launch {
        repository.insertContact(dbContact)
    }

    fun deleteContactById(contactId:Int) = viewModelScope.launch {
        repository.deleteContactById(contactId)
    }


}
