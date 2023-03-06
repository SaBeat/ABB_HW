package com.example.randomuser.data.db.contacts

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactsDao {
    @Query("SELECT * FROM Contact")
    fun getContacts(): Flow<List<DbContact>>

    @Update
    suspend fun update(contact: DbContact)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAll(contact: List<DbContact>)

    @Query("DELETE FROM Contact WHERE isDeleted = 0")
    suspend fun clearAllIsDeletedZero()

    @Query("SELECT (case when count(*) = 0 then 1 else 0 end) FROM Contact ct where isDeleted = 0")
    fun isDBEmpty(): Boolean

    @Query("SELECT * FROM Contact where userId == (:id) and isDeleted = 0")
    suspend fun getContactDetailById(id: Int):DbContact

    @Query("UPDATE  Contact SET isDeleted = 1 WHERE userId = (:id)")
    suspend fun deleteContactByID(id: Int)

}