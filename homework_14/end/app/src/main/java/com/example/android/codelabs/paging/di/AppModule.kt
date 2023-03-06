package com.example.android.codelabs.paging.di

import android.content.Context
import androidx.room.Room
import com.example.android.codelabs.paging.data.local.keys.KeysRepository
import com.example.android.codelabs.paging.data.local.keys.KeysRepositoryImpl
import com.example.android.codelabs.paging.data.local.keys.RemoteKeysDao
import com.example.android.codelabs.paging.data.local.repo.RepoDao
import com.example.android.codelabs.paging.data.local.repo.RepoRepository
import com.example.android.codelabs.paging.data.local.repo.RepoRepositoryImpl
import com.example.android.codelabs.paging.data.remote.GithubService
import com.example.android.codelabs.paging.data.local.RepoDatabase
import com.example.android.codelabs.paging.data.remote.GithubRepository
import com.example.android.codelabs.paging.data.remote.GithubRepositoryImpl
import com.example.android.codelabs.paging.domain.usecase.GithubRepositoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    @Provides
    @Singleton
    fun provideRetrofit():Retrofit {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BASIC

        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
        return Retrofit.Builder()
            .baseUrl(GithubService.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    @Provides
    @Singleton
    fun providesRetrofitServices(retrofit: Retrofit): GithubService {
        return retrofit.create(GithubService::class.java)
    }

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): RepoDatabase {
       return Room.databaseBuilder(
            context,
            RepoDatabase::class.java, "Github.db"
        )
            .build()

    }

    @Provides
    @Singleton
    fun providesRepoDao(repoDatabase: RepoDatabase):RepoDao {
        return repoDatabase.reposDao()
    }

    @Provides
    @Singleton
    fun providesKeysDao(repoDatabase: RepoDatabase):RemoteKeysDao {
        return repoDatabase.remoteKeysDao()
    }

    @Provides
    @Singleton
    fun providesRepoRepository(repoDao: RepoDao):RepoRepository {
        return RepoRepositoryImpl(repoDao)
    }

    @Provides
    @Singleton
    fun providesKeysRepository(remoteKeysDao: RemoteKeysDao):KeysRepository {
        return KeysRepositoryImpl(remoteKeysDao)
    }

    @Provides
    @Singleton
    fun providesGithubRepository(service:GithubService,keysRepository: KeysRepository,
                                 repoRepository: RepoRepository):GithubRepository {
        return GithubRepositoryImpl(service,keysRepository, repoRepository)
    }

    @Provides
    @Singleton
    fun providesUseCase(repository: GithubRepository):GithubRepositoryUseCase {
        return GithubRepositoryUseCase(repository)
    }
}