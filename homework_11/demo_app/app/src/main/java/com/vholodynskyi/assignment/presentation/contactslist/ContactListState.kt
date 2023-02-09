package com.vholodynskyi.assignment.presentation.contactslist

import com.vholodynskyi.assignment.data.api.contacts.ApiContact
import com.vholodynskyi.assignment.data.db.contacts.DbContact

data class ContactListState(
    val isLoading: Boolean = false,
    val contactList: List<ApiContact> = emptyList(),
    val dbContactList : MutableList<DbContact> = arrayListOf(),
    val error: String = ""
)
