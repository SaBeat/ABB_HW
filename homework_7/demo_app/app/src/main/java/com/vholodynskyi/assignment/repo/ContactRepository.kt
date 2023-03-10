package com.vholodynskyi.assignment.repo

import com.vholodynskyi.assignment.api.contacts.ApiContact
import com.vholodynskyi.assignment.api.contacts.ContactsService
import com.vholodynskyi.assignment.db.contacts.ContactsDao
import com.vholodynskyi.assignment.db.contacts.DbContact
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class ContactRepository(
    private val contactsService: ContactsService,
    private val dao: ContactsDao
) {

    suspend fun getContactList() = flow<List<ApiContact>> {
        try {
            val response = contactsService.getContacts(30).results
            if (response != null) {
                emit(response)
            }
        } catch (e: HttpException) {
            throw Exception("Http exception occurred")
        } catch (e: IOException) {
            throw Exception("IO exception occurred")
        }
    }

    suspend fun insertContactList(dbContactList: MutableList<DbContact>) = dao.addAll(dbContactList)
    suspend fun deleteAllContacts() = dao.deleteAll()
}