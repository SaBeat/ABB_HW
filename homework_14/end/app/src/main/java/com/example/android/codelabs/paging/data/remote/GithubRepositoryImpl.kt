package com.example.android.codelabs.paging.data.remote

import android.util.Log
import androidx.paging.*
import com.example.android.codelabs.paging.data.GithubRemoteMediator
import com.example.android.codelabs.paging.data.local.repo.Repo
import com.example.android.codelabs.paging.data.local.RepoDatabase
import com.example.android.codelabs.paging.data.local.keys.KeysRepository
import com.example.android.codelabs.paging.data.local.repo.RepoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GithubRepositoryImpl(
    private val service: GithubService,
    private val keysRepository: KeysRepository,
    private val repoRepository: RepoRepository
) : GithubRepository {

    override fun getSearchResultStream(query: String): Flow<PagingData<Repo>> {
        Log.d("GithubRepository", "New query: $query")

        // appending '%' so we can allow other characters to be before and after the query string
        val dbQuery = "%${query.replace(' ', '%')}%"
        val pagingSourceFactory = { repoRepository.reposByName(dbQuery) }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = GithubRemoteMediator(
                query,
                service,
                keysRepository,
                repoRepository
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }
}