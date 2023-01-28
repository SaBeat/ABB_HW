package com.vholodynskyi.assignment.data.db.contacts

import androidx.room.*

@Dao
interface ContactsDao {
    @Query("SELECT * FROM Contact")
    fun getContacts(): MutableList<DbContact>

    @Update
    fun update(contact: DbContact)

    @Insert
    fun addAll(contact: MutableList<DbContact>)

    @Insert
    fun addContact(contact: DbContact)

    @Query("DELETE FROM Contact WHERE id = (:contactId)")
    fun deleteById(contactId: Int)

    @Query("DELETE FROM Contact")
    fun deleteAll()
}