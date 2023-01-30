package com.example.randomuser.common

import com.example.randomuser.data.api.contacts.ApiContact

object Constants {
    const val BASE_URL = "https://randomuser.me/"
    const val DB_NAME = "app-database"
    const val TAG = "DEBUG"
    var apiContactSingletonList = mutableListOf<ApiContact>()
}