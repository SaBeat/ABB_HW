package com.example.randomuser.presentation.contactlist

import com.example.randomuser.data.api.contacts.ApiContact

data class ContactListState(
    val isLoading: Boolean = false,
    val contactList: List<ApiContact> = emptyList(),
    val error: String = ""
)