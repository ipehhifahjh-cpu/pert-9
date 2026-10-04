package com.latihan.belajarstorage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val PREF_FILE = "AppUserData"
    private val KEY_USER_NAME = "user_fullname"

    private lateinit var inputPengguna: EditText
    private lateinit var txtStatus: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputPengguna = findViewById(R.id.inputPengguna)
        txtStatus = findViewById(R.id.txtStatus)

        val btnSimpan = findViewById<Button>(R.id.btnSimpanData)
        val btnBaca = findViewById<Button>(R.id.btnBacaData)
        val btnHapus = findViewById<Button>(R.id.btnHapusData)
        val btnPindah = findViewById<Button>(R.id.btnPindahLayarRoom)

        val prefs = getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)

        btnSimpan.setOnClickListener {
            val nama = inputPengguna.text.toString().trim()
            if (nama.isNotEmpty()) {
                prefs.edit().putString(KEY_USER_NAME, nama).apply()
                Toast.makeText(this, "Berhasil disimpan!", Toast.LENGTH_SHORT).show()
                inputPengguna.text.clear()
            } else {
                Toast.makeText(this, "Kolom nama tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            }
        }

        btnBaca.setOnClickListener {
            val hasil = prefs.getString(KEY_USER_NAME, "Data tidak ditemukan")
            txtStatus.text = hasil
        }

        btnHapus.setOnClickListener {
            prefs.edit().remove(KEY_USER_NAME).apply()
            txtStatus.text = "Belum ada data diset"
            Toast.makeText(this, "Data berhasil dihapus!", Toast.LENGTH_SHORT).show()
        }

        btnPindah.setOnClickListener {
            startActivity(Intent(this, Activity_Catatan::class.java))
        }

        val namaTersimpan = prefs.getString(KEY_USER_NAME, null)
        if (namaTersimpan != null) {
            txtStatus.text = "Selamat datang kembali, $namaTersimpan"
        }
    }
}