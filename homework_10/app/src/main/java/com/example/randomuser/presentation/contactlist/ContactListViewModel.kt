package com.example.randomuser.presentation.contactlist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomuser.common.Resource
import com.example.randomuser.data.db.contacts.DbContact
import com.example.randomuser.domain.usecase.DeleteAllContactListUseCase
import com.example.randomuser.domain.usecase.GetContactListUseCase
import com.example.randomuser.domain.usecase.InsertAllContactListUseCase
import kotlinx.coroutines.flow.*

class ContactsListViewModel(
    private val getAllContactListUseCase: GetContactListUseCase,
    private val insertAllContactListUseCase: InsertAllContactListUseCase,
    private val deleteAllContactListUseCase: DeleteAllContactListUseCase
) : ViewModel() {

    private val _contactList = mutableStateOf(ContactListState())
    val contactList: State<ContactListState> = _contactList

    init {
        getAllContactList()
        deleteAllContactListFromDatabase()
    }

    private fun getAllContactList() {
        getAllContactListUseCase().onEach { resultState ->
            when (resultState) {
                is Resource.Success -> {
                    _contactList.value =
                        ContactListState(contactList = resultState.data ?: emptyList())

                }
                is Resource.Error -> {
                    _contactList.value = ContactListState(error = resultState.message ?: "Error")
                }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    private fun insertAllContactListToDatabase(product: MutableList<DbContact>) {

        insertAllContactListUseCase(product).onEach { resultState ->
            when (resultState) {
                is Resource.Error -> {
                    _contactList.value = ContactListState(error = resultState.message ?: "Error")
                }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    private fun deleteAllContactListFromDatabase() {
        deleteAllContactListUseCase().onEach { resultState ->
            when (resultState) {
                is Resource.Error -> {
                    _contactList.value = ContactListState(error = resultState.message ?: "Error")
                }
            }
        }.launchIn(viewModelScope)
    }


}
