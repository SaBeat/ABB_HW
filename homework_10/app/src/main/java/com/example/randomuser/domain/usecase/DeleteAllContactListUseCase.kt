package com.example.randomuser.domain.usecase

import com.example.randomuser.common.Resource
import com.example.randomuser.data.api.contacts.ApiContact
import com.example.randomuser.domain.repository.ContactRepository
import kotlinx.coroutines.flow.flow

class DeleteAllContactListUseCase(
    private val repository: ContactRepository
) {
    operator fun invoke() = flow {
        try {
            emit(Resource.Loading<List<ApiContact>>())
            val result = repository.deleteAllContacts()
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }
}