package com.example.randomuser.data.repository

import com.example.randomuser.data.api.contacts.ApiContact
import com.example.randomuser.data.api.contacts.ContactsService
import com.example.randomuser.data.db.contacts.ContactsDao
import com.example.randomuser.data.db.contacts.DbContact
import com.example.randomuser.domain.repository.ContactRepository

class ContactRepositoryImpl(
    private val contactsService: ContactsService,
    private val dao: ContactsDao
) : ContactRepository {
    override suspend fun getContactList(): List<ApiContact> {
        return contactsService.getContacts(30).results!!
    }

    override suspend fun insertContactList(dbContactList: MutableList<DbContact>) {
        dao.addAll(dbContactList)
    }

    override suspend fun deleteAllContacts() {
        dao.deleteAll()
    }
}