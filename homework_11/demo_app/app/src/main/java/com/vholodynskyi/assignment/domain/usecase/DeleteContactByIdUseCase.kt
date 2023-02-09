package com.vholodynskyi.assignment.domain.usecase

import com.vholodynskyi.assignment.common.Resource
import com.vholodynskyi.assignment.data.api.contacts.ApiContact
import com.vholodynskyi.assignment.domain.repository.ContactRepository
import kotlinx.coroutines.flow.flow

class DeleteContactByIdUseCase(
    private val repository: ContactRepository
) {
    operator fun invoke(id:Int) = flow {
        try {
            emit(Resource.Loading<List<ApiContact>>())
            val result = repository.deleteContactById(id)
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }
}