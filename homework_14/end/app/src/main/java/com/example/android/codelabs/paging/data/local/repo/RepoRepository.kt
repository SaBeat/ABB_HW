package com.example.android.codelabs.paging.data.local.repo

import androidx.paging.PagingSource

interface RepoRepository {
    suspend fun insertAll(repos: List<Repo>)
    fun reposByName(queryString: String):PagingSource<Int,Repo>
    suspend fun clearRepos()
}