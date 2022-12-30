package com.example.composeprofilesection.model

import com.example.composeprofilesection.R

data class Profile(
    val isAdmin: Boolean = true,
    val firstName: String = "Sabit",
    val lastName: String = "Sadigli",
    val email: String = "sabitsadiqli@gmail.com",
    val telephone: String = "+994505021584",
    val gender: String = "Male",
    val avatarUrl: Int = R.drawable.man,
    val customerNo: String = "23456789",
)
