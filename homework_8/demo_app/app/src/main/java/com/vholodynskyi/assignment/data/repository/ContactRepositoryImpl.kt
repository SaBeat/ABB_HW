package com.vholodynskyi.assignment.data.repository

import com.vholodynskyi.assignment.data.api.contacts.ApiContact
import com.vholodynskyi.assignment.data.api.contacts.ContactsService
import com.vholodynskyi.assignment.data.db.contacts.ContactsDao
import com.vholodynskyi.assignment.data.db.contacts.DbContact
import com.vholodynskyi.assignment.domain.repository.ContactRepository

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