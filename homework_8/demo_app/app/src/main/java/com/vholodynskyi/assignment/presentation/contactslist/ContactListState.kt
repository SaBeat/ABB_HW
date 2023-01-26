package com.vholodynskyi.assignment.presentation.contactslist

import com.vholodynskyi.assignment.data.api.contacts.ApiContact

data class ContactListState(
    val isLoading: Boolean = false,
    val contactList: List<ApiContact> = emptyList(),
    val error: String = ""
)
