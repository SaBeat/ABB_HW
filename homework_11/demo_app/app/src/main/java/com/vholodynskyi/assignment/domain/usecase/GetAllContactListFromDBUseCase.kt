package com.vholodynskyi.assignment.domain.usecase

import com.vholodynskyi.assignment.common.Resource
import com.vholodynskyi.assignment.domain.repository.ContactRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetAllContactListFromDBUseCase
    (private val repository: ContactRepository) {
    operator fun invoke() = flow {
        try {
            emit(Resource.Loading())
            val result = repository.getAllContactListFromDB()
            emit(Resource.Success(result))
        } catch (e: HttpException) {
            emit(e.localizedMessage?.let { Resource.Error(it) })
        } catch (e: IOException) {
            emit(e.localizedMessage?.let { Resource.Error(it) })
        }
    }
}