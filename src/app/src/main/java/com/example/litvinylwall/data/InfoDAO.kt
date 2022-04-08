package com.example.litvinylwall.data

import androidx.room.*

@Dao
interface InfoDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: Info)

    @Update
    fun update(item: Info)

    @Query("SELECT * from info_table WHERE id = :key")
    fun get(key: Int): Info?

    @Query("SELECT COUNT(0) from info_table WHERE imgTag != 'Blank'")
    fun count(): Int

    @Query("DELETE FROM info_table")
    fun clear()
}