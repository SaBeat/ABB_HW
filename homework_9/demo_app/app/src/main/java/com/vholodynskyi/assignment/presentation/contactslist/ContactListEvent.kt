package com.vholodynskyi.assignment.presentation.contactslist

import com.vholodynskyi.assignment.data.db.contacts.DbContact

sealed class ContactListEvent {
    object GetAllContactList : ContactListEvent()
    object DeleteAllContactListFromDatabase : ContactListEvent()
    data class InsertAllContactListToDatabase(val contactList:MutableList<DbContact>) : ContactListEvent()
}