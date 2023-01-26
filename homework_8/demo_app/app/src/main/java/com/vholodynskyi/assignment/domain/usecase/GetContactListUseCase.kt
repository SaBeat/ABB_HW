package com.vholodynskyi.assignment.domain.usecase

import com.vholodynskyi.assignment.common.Resource
import com.vholodynskyi.assignment.data.api.contacts.ApiContact
import com.vholodynskyi.assignment.domain.repository.ContactRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetContactListUseCase(
    private val repository: ContactRepository
) {
    operator fun invoke() = flow<Resource<List<ApiContact>>> {
        try {
            emit(Resource.Loading<List<ApiContact>>())
            val result = repository.getContactList().map { it }
            emit(Resource.Success(result))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage))
        }
    }
}