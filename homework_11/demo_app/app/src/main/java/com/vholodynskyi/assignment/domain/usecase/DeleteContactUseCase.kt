package com.vholodynskyi.assignment.domain.usecase

import com.vholodynskyi.assignment.common.Resource
import com.vholodynskyi.assignment.data.api.contacts.ApiContact
import com.vholodynskyi.assignment.data.db.contacts.DbContact
import com.vholodynskyi.assignment.domain.repository.ContactRepository
import kotlinx.coroutines.flow.flow

class DeleteContactUseCase(
    private val repository: ContactRepository
) {
    operator fun invoke(dbContact: DbContact) = flow {
        try {
            emit(Resource.Loading<List<ApiContact>>())
            val result = repository.deleteContact(dbContact)
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }
}