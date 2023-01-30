package com.example.randomuser.presentation.contactlist

import com.example.randomuser.data.db.contacts.DbContact

sealed class ContactListEvent {
    object GetAllContactList : ContactListEvent()
    object DeleteAllContactListFromDatabase : ContactListEvent()
    data class InsertAllContactListToDatabase(val contactList:MutableList<DbContact>) : ContactListEvent()
}