package com.example.android.codelabs.paging.data.local.keys

interface KeysRepository {
    suspend fun insertAll(remoteKey: List<RemoteKeys>)
    suspend fun remoteKeysRepoId(repoId: Long):RemoteKeys?
    suspend fun clearRemoteKeys()
}