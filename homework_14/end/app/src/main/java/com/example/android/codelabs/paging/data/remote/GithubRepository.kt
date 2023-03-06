package com.example.android.codelabs.paging.data.remote

import androidx.paging.PagingData
import com.example.android.codelabs.paging.data.local.repo.Repo
import kotlinx.coroutines.flow.Flow

interface GithubRepository {
    fun getSearchResultStream(query: String): Flow<PagingData<Repo>>
}