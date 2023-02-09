package com.vholodynskyi.assignment.presentation.contactslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vholodynskyi.assignment.common.Resource
import com.vholodynskyi.assignment.data.db.contacts.DbContact
import com.vholodynskyi.assignment.domain.usecase.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ContactsListViewModel(
    private val getAllContactListUseCase: GetContactListUseCase,
    private val insertAllContactListUseCase: InsertAllContactListUseCase,
    private val deleteAllContactListUseCase: DeleteAllContactListUseCase,
    private val getAllContactListFromDBUseCase: GetAllContactListFromDBUseCase,
    private val deleteContactByIdUseCase: DeleteContactByIdUseCase,
    private val deleteContactUseCase: DeleteContactUseCase
) : ViewModel() {

    private val _contactList = MutableStateFlow(ContactListState())
    val contactList :StateFlow<ContactListState> = _contactList.asStateFlow()

    private val _dbCOntactList = MutableStateFlow(ContactListState())
    val dbCOntactList :StateFlow<ContactListState> = _dbCOntactList.asStateFlow()


    fun handleEvent(contactListEvent: ContactListEvent){
        when(contactListEvent){
            is ContactListEvent.GetAllContactListFromDB -> {
                getAllContactListFromDB()
            }
            is ContactListEvent.DeleteContactById -> {
                deleteContactById(contactListEvent.id)
            }
            is ContactListEvent.DeleteContact -> {
                deleteContact(contactListEvent.dbContact)
            }
            is ContactListEvent.GetAllContactList -> {
                getAllContactList()
            }
            is ContactListEvent.DeleteAllContactListFromDatabase -> {
                deleteAllContactListFromDatabase()
            }
            is ContactListEvent.InsertAllContactListToDatabase -> {
                insertAllContactListToDatabase(contactListEvent.contactList)
            }
        }
    }

    private fun getAllContactList() {
        viewModelScope.launch {
            getAllContactListUseCase.invoke().collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        _contactList.update { state ->
                            state.copy(contactList = resultState.data!!)
                        }
                    }
                    is Resource.Error -> {
                        _contactList.update { state ->
                            state.copy(error = resultState.message!!)
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    private fun insertAllContactListToDatabase(product: MutableList<DbContact>) {
        viewModelScope.launch {
            insertAllContactListUseCase.invoke(product).collect { resultState ->
                when (resultState) {
                    is Resource.Error -> {
                        _contactList.update { state ->
                            state.copy(error = resultState.message!!)
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    private fun getAllContactListFromDB():MutableList<DbContact>{
       viewModelScope.launch {
            getAllContactListFromDBUseCase.invoke().collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        _contactList.update { state ->
                            state.copy(dbContactList = resultState.data!!)
                        }
                    }
                    is Resource.Error -> {
                        _contactList.update { state ->
                            state.copy(error = resultState.message!!)
                        }
                    }
                    else -> {}
                }
            }
        }
        return _contactList.value.dbContactList
    }

    private fun deleteAllContactListFromDatabase(){
        viewModelScope.launch {
            deleteAllContactListUseCase.invoke().collect { resultState ->
                when(resultState){
                    is Resource.Error -> {
                        _contactList.update { state ->
                            state.copy(error = resultState.message!!)
                        }
                    }
                }
            }
        }
    }

    private fun deleteContactById(id:Int){
        viewModelScope.launch {
            deleteContactByIdUseCase.invoke(id).collect { resultState ->
                when(resultState){
                    is Resource.Error -> {
                        _contactList.update { state ->
                            state.copy(error = resultState.message!!)
                        }
                    }
                }
            }
        }
    }

    private fun deleteContact(dbContact: DbContact){
        viewModelScope.launch {
            deleteContactUseCase.invoke(dbContact).collect { resultState ->
                when(resultState){
                    is Resource.Error -> {
                        _contactList.update { state ->
                            state.copy(error = resultState.message!!)
                        }
                    }
                }
            }
        }
    }


}
