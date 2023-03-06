package com.example.randomuser.data.repository

import com.example.randomuser.data.api.contacts.ContactsService
import com.example.randomuser.data.api.contacts.toDbContact
import com.example.randomuser.data.db.contacts.ContactsDao
import com.example.randomuser.data.db.contacts.DbContact
import com.example.randomuser.domain.repository.ContactRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class ContactRepositoryImpl(
    private val contactsService: ContactsService,
    private val dao: ContactsDao
):ContactRepository {

    override suspend fun getDbContactList(): Flow<List<DbContact>> {
        val dbFlowList = dao.getContacts()
        return dbFlowList.map { listOfDbContacts ->
            listOfDbContacts.filter { dbContact ->
                !dbContact.isDeleted
            }
        }
    }

    override suspend fun refreshDbContacts() {
        try {
            val response = contactsService.getContacts()
            refreshDbData(response.results?.map { it.toDbContact() })
        }catch (e:Exception){
            throw e
        }
    }

    override suspend fun isDBEmpty(): Boolean {
        return dao.isDBEmpty()

    }

    override suspend fun getContactDetail(id: Int): Flow<DbContact> = flow {
        emit(dao.getContactDetailById(id))
    }

    override suspend fun deleteContactByID(id: Int) {
        dao.deleteContactByID(id)
    }

    private suspend fun refreshDbData(data: List<DbContact>?) {
        if (data != null) {
            dao.clearAllIsDeletedZero()
            dao.addAll(data)
        }
    }
}