package com.vholodynskyi.assignment.di

import android.content.Context
import androidx.room.Room
import com.vholodynskyi.assignment.common.Constants.BASE_URL
import com.vholodynskyi.assignment.common.Constants.DB_NAME
import com.vholodynskyi.assignment.data.api.contacts.ContactsService
import com.vholodynskyi.assignment.data.db.AppDatabase
import com.vholodynskyi.assignment.data.db.contacts.ContactsDao
import com.vholodynskyi.assignment.data.repository.ContactRepositoryImpl
import com.vholodynskyi.assignment.domain.repository.ContactRepository
import com.vholodynskyi.assignment.domain.usecase.DeleteAllContactListUseCase
import com.vholodynskyi.assignment.domain.usecase.GetContactListUseCase
import com.vholodynskyi.assignment.domain.usecase.InsertAllContactListUseCase
import com.vholodynskyi.assignment.domain.usecase.UseCases
import com.vholodynskyi.assignment.presentation.contactslist.ContactsListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule = module {

    fun provideRepository(
        contactsService: ContactsService,
        dao: ContactsDao
    ): ContactRepository =
        ContactRepositoryImpl(contactsService, dao)

    fun provideUseCase(repository: ContactRepository): UseCases = UseCases(
        GetContactListUseCase(repository = repository),
        DeleteAllContactListUseCase(repository = repository),
        InsertAllContactListUseCase(repository = repository)

    )

    viewModel {
        ContactsListViewModel(
            provideUseCase(provideRepository(get(), get())).getAllContactListUseCase,
            provideUseCase(provideRepository(get(), get())).insertAllContactListUseCase,
            provideUseCase(provideRepository(get(), get())).deleteAllContactListUseCase
        )
    }
}

val repositoryModule = module {

    single {
    }
}

val dbModule = module {
    fun provideDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DB_NAME)
            .allowMainThreadQueries().build()

    fun provideDatabaseDao(db: AppDatabase): ContactsDao = db.userDao()
    single {
        provideDatabase(get())
    }
    single {
        provideDatabaseDao(get())
    }
}

val networkModule = module {
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()

    fun provideApiService(retrofit: Retrofit): ContactsService =
        retrofit.create(ContactsService::class.java)

    single {
        provideRetrofit()
    }
    single {
        provideApiService(get())
    }
}



