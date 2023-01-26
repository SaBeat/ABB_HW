package com.vholodynskyi.assignment.domain.repository

import com.vholodynskyi.assignment.data.api.contacts.ApiContact
import com.vholodynskyi.assignment.data.db.contacts.DbContact

interface ContactRepository{
    suspend fun getContactList(): List<ApiContact>
    suspend fun insertContactList(dbContactList:MutableList<DbContact>)
    suspend fun deleteAllContacts()
}