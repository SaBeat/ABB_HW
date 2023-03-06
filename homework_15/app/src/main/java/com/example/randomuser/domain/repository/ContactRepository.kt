package com.example.randomuser.domain.repository

import com.example.randomuser.data.db.contacts.DbContact
import kotlinx.coroutines.flow.Flow

interface ContactRepository{
    suspend fun getDbContactList(): Flow<List<DbContact>>
    suspend fun refreshDbContacts()
    suspend fun isDBEmpty(): Boolean
    suspend fun getContactDetail(id: Int): Flow<DbContact>
    suspend fun deleteContactByID(id:Int)

}