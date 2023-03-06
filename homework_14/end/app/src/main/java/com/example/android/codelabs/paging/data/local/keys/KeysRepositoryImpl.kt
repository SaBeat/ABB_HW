package com.example.android.codelabs.paging.data.local.keys

class KeysRepositoryImpl(private val keysDao: RemoteKeysDao) : KeysRepository{

    override suspend fun insertAll(remoteKey: List<RemoteKeys>) {
        keysDao.insertAll(remoteKey)
    }

    override suspend fun remoteKeysRepoId(repoId: Long): RemoteKeys? {
        return keysDao.remoteKeysRepoId(repoId)
    }

    override suspend fun clearRemoteKeys() {
        keysDao.clearRemoteKeys()
    }
}