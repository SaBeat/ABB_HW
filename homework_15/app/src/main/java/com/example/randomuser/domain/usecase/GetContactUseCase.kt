package com.example.randomuser.domain.usecase

import com.example.randomuser.common.Resource
import com.example.randomuser.data.db.contacts.DbContact
import com.example.randomuser.domain.repository.ContactRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException

class GetContactUseCase(private val repository: ContactRepository) {

    suspend operator fun invoke(id: Int): Flow<Resource<DbContact>> = flow {
        try {
            emit(Resource.Loading<DbContact>())
            coroutineScope {
                repository.getContactDetail(id).map { Resource.Success(it) }.collect { emit(it) }
            }

        } catch (ex: HttpException) {
            emit(Resource.Error(ex.message.toString()))
        } catch (ex: IOException) {
            emit(Resource.Error(ex.message.toString()))
        }

    }

}