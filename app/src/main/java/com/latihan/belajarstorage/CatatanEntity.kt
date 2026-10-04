package com.latihan.belajarstorage

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_catatan")
data class CatatanEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val judulCatatan: String,
    val isiCatatan: String
)