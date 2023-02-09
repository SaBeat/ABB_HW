package com.vholodynskyi.assignment.presentation.contactslist

import com.vholodynskyi.assignment.data.db.contacts.DbContact

sealed class ContactListEvent {
    object GetAllContactList : ContactListEvent()
    object DeleteAllContactListFromDatabase : ContactListEvent()
    data class InsertAllContactListToDatabase(val contactList:MutableList<DbContact>) : ContactListEvent()
    object GetAllContactListFromDB : ContactListEvent()
    data class DeleteContactById(val id:Int) : ContactListEvent()
    data class DeleteContact(val dbContact: DbContact) : ContactListEvent()
}