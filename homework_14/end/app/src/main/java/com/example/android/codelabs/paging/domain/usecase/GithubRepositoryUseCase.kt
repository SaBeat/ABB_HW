package com.example.android.codelabs.paging.domain.usecase

import androidx.paging.PagingData
import com.example.android.codelabs.paging.data.local.repo.Repo
import com.example.android.codelabs.paging.data.remote.GithubRepository
import kotlinx.coroutines.flow.Flow

class GithubRepositoryUseCase(
    private val repository: GithubRepository
) {
    fun getSearchResultStream(query: String): Flow<PagingData<Repo>> {
        return repository.getSearchResultStream(query)
    }
}