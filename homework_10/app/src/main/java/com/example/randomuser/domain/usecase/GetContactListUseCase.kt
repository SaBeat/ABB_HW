package com.example.randomuser.domain.usecase

import com.example.randomuser.common.Resource
import com.example.randomuser.domain.repository.ContactRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetContactListUseCase(
    private val repository: ContactRepository
) {
    operator fun invoke() = flow {
        try {
            emit(Resource.Loading())
            val result = repository.getContactList()
            emit(Resource.Success(result))
        } catch (e: HttpException) {
            emit(e.localizedMessage?.let { Resource.Error(it) })
        } catch (e: IOException) {
            emit(e.localizedMessage?.let { Resource.Error(it) })
        }
    }
}