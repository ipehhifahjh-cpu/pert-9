package com.latihan.belajarstorage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CatatanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCatatan(catatan: CatatanEntity)

    @Query("SELECT * FROM tb_catatan ORDER BY id DESC")
    suspend fun getAllCatatan(): List<CatatanEntity>

    @Query("DELETE FROM tb_catatan")
    suspend fun deleteAll()
}