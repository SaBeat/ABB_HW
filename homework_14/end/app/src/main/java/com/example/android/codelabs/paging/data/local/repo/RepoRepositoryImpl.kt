package com.example.android.codelabs.paging.data.local.repo

import androidx.paging.PagingSource

class RepoRepositoryImpl(
    private val repoDao: RepoDao
) : RepoRepository {

    override suspend fun insertAll(repos: List<Repo>) {
        repoDao.insertAll(repos)
    }

    override fun reposByName(queryString: String): PagingSource<Int, Repo> {
        return repoDao.reposByName(queryString)
    }

    override suspend fun clearRepos() {
        repoDao.clearRepos()
    }
}