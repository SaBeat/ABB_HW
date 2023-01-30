package com.example.randomuser.data.api.contacts

import com.vholodynskyi.assignment.data.api.contacts.ApiContactResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ContactsService {
    @GET("api/")
    suspend fun getContacts(@Query("results") limit: Int = 30): ApiContactResponse
}