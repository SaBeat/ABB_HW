package com.vholodynskyi.assignment.presentation.contactslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vholodynskyi.assignment.common.Resource
import com.vholodynskyi.assignment.data.db.contacts.DbContact
import com.vholodynskyi.assignment.domain.usecase.DeleteAllContactListUseCase
import com.vholodynskyi.assignment.domain.usecase.GetContactListUseCase
import com.vholodynskyi.assignment.domain.usecase.InsertAllContactListUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ContactsListViewModel(
    private val getAllContactListUseCase: GetContactListUseCase,
    private val insertAllContactListUseCase: InsertAllContactListUseCase,
    private val deleteAllContactListUseCase: DeleteAllContactListUseCase
) : ViewModel() {

    private val _contactList = MutableStateFlow(ContactListState())
    val contactList :StateFlow<ContactListState> = _contactList.asStateFlow()

    fun handleEvent(contactListEvent: ContactListEvent){
        when(contactListEvent){
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


}
