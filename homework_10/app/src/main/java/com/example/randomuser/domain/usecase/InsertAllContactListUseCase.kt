package com.example.randomuser.domain.usecase

import com.example.randomuser.common.Resource
import com.example.randomuser.data.api.contacts.ApiContact
import com.example.randomuser.data.db.contacts.DbContact
import com.example.randomuser.domain.repository.ContactRepository
import kotlinx.coroutines.flow.flow

class InsertAllContactListUseCase(
    private val repository: ContactRepository
) {
    operator fun invoke(dbContactList:MutableList<DbContact>) = flow {
        try {
            emit(Resource.Loading<List<ApiContact>>())
            val result = repository.insertContactList(dbContactList)
            emit(Resource.Success(result))
        }catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }
}