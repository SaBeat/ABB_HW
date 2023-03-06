package com.example.randomuser.presentation.contactdetail

import com.example.randomuser.data.db.contacts.DbContact

data class ContactDetailState(
    val contact: DbContact = DbContact(0,"","","",null,false),
    var isLoading:Boolean = false
)
