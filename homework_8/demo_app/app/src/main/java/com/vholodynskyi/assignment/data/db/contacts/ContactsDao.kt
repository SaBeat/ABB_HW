package com.vholodynskyi.assignment.data.db.contacts

import androidx.room.*

@Dao
interface ContactsDao {
    @Query("SELECT * FROM Contact")
    fun getContacts(): MutableList<DbContact>

    @Update
    suspend fun update(contact: DbContact)

    @Insert
    suspend fun addAll(contact: MutableList<DbContact>)

    @Insert
    suspend fun addContact(contact: DbContact)

    @Query("DELETE FROM Contact WHERE id = (:contactId)")
    suspend fun deleteById(contactId: Int)

    @Query("DELETE FROM Contact")
    suspend fun deleteAll()
}