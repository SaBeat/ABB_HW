package com.example.randomuser.domain.repository

import com.example.randomuser.data.api.contacts.ApiContact
import com.example.randomuser.data.db.contacts.DbContact

interface ContactRepository{
    suspend fun getContactList(): List<ApiContact>
    suspend fun insertContactList(dbContactList:MutableList<DbContact>)
    suspend fun deleteAllContacts()
}