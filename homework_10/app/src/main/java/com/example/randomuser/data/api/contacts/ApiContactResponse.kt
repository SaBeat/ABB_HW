package com.example.randomuser.data.api.contacts

import com.google.gson.annotations.SerializedName


data class ApiContactResponse(
    val results: List<ApiContact>?
)