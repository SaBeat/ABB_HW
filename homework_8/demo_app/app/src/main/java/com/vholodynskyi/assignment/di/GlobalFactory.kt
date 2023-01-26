package com.vholodynskyi.assignment.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.vholodynskyi.assignment.data.api.RetrofitServicesProvider
import com.vholodynskyi.assignment.data.api.contacts.ApiContact
import com.vholodynskyi.assignment.data.api.contacts.ContactsService
import com.vholodynskyi.assignment.data.db.AppDatabase
import com.vholodynskyi.assignment.data.repository.ContactRepositoryImpl
import com.vholodynskyi.assignment.domain.repository.ContactRepository
import com.vholodynskyi.assignment.domain.usecase.DeleteAllContactListUseCase
import com.vholodynskyi.assignment.domain.usecase.GetContactListUseCase
import com.vholodynskyi.assignment.domain.usecase.InsertAllContactListUseCase
import com.vholodynskyi.assignment.presentation.contactslist.ContactsListViewModel
import com.vholodynskyi.assignment.presentation.details.DetailsViewModel

object GlobalFactory : ViewModelProvider.Factory {

    var apiContactSingletonList = mutableListOf<ApiContact>()
    val service: ContactsService by lazy {
        RetrofitServicesProvider().contactsService
    }

    fun getRepo():ContactRepository{
        return ContactRepositoryImpl(service,db.userDao())
    }

    lateinit var db: AppDatabase



    fun init(context: Context) {
        db = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app-database"
        ).allowMainThreadQueries().build()
    }

    override fun <T : ViewModel?> create(
        modelClass: Class<T>
    ): T {
        return when (modelClass) {
            ContactsListViewModel::class.java -> ContactsListViewModel(
                GetContactListUseCase(getRepo()),
                InsertAllContactListUseCase(getRepo()),
                DeleteAllContactListUseCase(getRepo())
            )
            DetailsViewModel::class.java -> DetailsViewModel()
            else -> throw IllegalArgumentException("Cannot create factory for ${modelClass.simpleName}")
        } as T
    }
}
