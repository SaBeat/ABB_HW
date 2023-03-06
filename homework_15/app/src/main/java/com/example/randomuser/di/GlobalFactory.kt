package com.example.randomuser.di

import android.content.Context
import androidx.room.Room
import com.example.randomuser.common.Constants.BASE_URL
import com.example.randomuser.common.Constants.DB_NAME
import com.example.randomuser.data.api.contacts.ContactsService
import com.example.randomuser.data.db.AppDatabase
import com.example.randomuser.data.db.contacts.ContactsDao
import com.example.randomuser.data.repository.ContactRepositoryImpl
import com.example.randomuser.domain.repository.ContactRepository
import com.example.randomuser.domain.usecase.GetContactListUseCase
import com.example.randomuser.domain.usecase.GetContactUseCase
import com.example.randomuser.domain.usecase.UseCases
import com.example.randomuser.presentation.contactdetail.ContactDetailViewModel
import com.example.randomuser.presentation.contactlist.ContactsListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
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
        GetContactUseCase(repository = repository)
    )

    scope(named("ContactDetailViewModel")) {
        scoped { provideUseCase(provideRepository(get(),get())).getContactUseCase }
    }

    viewModel {
        ContactsListViewModel(
            provideUseCase(provideRepository(get(), get())).getAllContactListUseCase,
            provideRepository(get(),get())
        )
    }

    viewModel {
        ContactDetailViewModel(
            provideRepository(get(),get())
        )
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