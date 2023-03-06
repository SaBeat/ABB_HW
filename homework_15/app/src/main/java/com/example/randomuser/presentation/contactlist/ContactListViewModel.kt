package com.example.randomuser.presentation.contactlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomuser.common.Resource
import com.example.randomuser.data.db.contacts.DbContact
import com.example.randomuser.domain.repository.ContactRepository
import com.example.randomuser.domain.usecase.GetContactListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ContactsListViewModel(
    private val getContactListUseCase: GetContactListUseCase,
    private val repository: ContactRepository
) : ViewModel() {

    private val _contacts = MutableStateFlow<List<DbContact>>(emptyList())
    val contacts: StateFlow<List<DbContact>> = _contacts

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        getDbContactList()
    }

    private fun getDbContactList() {
        viewModelScope.launch {
            getContactListUseCase.invoke().collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        _contacts.value = resultState.data ?: emptyList()
                    }
                    is Resource.Loading -> {
                        Log.i("DEBUG",resultState.data.toString())
                    }
                    is Resource.Error -> {
                        Log.i("DEBUG",resultState.message ?: "Unexpected error")
                    }
                }
            }
        }
    }

    fun refreshDbContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _isLoading.value = true
                repository.refreshDbContacts()
            } catch (ex: Exception) {

            }
            finally {
                _isLoading.value = false
            }
        }
    }

}
