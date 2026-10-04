package com.latihan.belajarstorage

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Activity_Catatan : AppCompatActivity() {

    private lateinit var database: AppDatabase
    private lateinit var txtDaftarCatatan: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catatan)

        database = AppDatabase.getDatabase(this)

        val etJudul = findViewById<EditText>(R.id.etJudulCatatan)
        val etIsi = findViewById<EditText>(R.id.etIsiCatatan)
        val btnSimpan = findViewById<Button>(R.id.btnTambahCatatan)
        val btnReset = findViewById<Button>(R.id.btnResetDatabase)
        txtDaftarCatatan = findViewById(R.id.txtDaftarCatatan)

        muatData()

        btnSimpan.setOnClickListener {
            val judul = etJudul.text.toString().trim()
            val isi = etIsi.text.toString().trim()

            if (judul.isNotEmpty() && isi.isNotEmpty()) {
                val dataBaru = CatatanEntity(judulCatatan = judul, isiCatatan = isi)

                lifecycleScope.launch(Dispatchers.IO) {
                    database.catatanDao().insertCatatan(dataBaru)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@Activity_Catatan, "Catatan Berhasil Disimpan!", Toast.LENGTH_SHORT).show()
                        etJudul.text.clear()
                        etIsi.text.clear()
                        muatData()
                    }
                }
            } else {
                Toast.makeText(this, "Judul & Isi tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            }
        }

        btnReset.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                database.catatanDao().deleteAll()
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@Activity_Catatan, "Database Dikosongkan!", Toast.LENGTH_SHORT).show()
                    muatData()
                }
            }
        }
    }

    private fun muatData() {
        lifecycleScope.launch(Dispatchers.IO) {
            val list = database.catatanDao().getAllCatatan()
            withContext(Dispatchers.Main) {
                if (list.isEmpty()) {
                    txtDaftarCatatan.text = "Belum ada catatan tersimpan."
                } else {
                    val builder = StringBuilder()
                    for (item in list) {
                        builder.append("ID #${item.id} : ${item.judulCatatan}\n")
                        builder.append("${item.isiCatatan}\n\n")
                    }
                    txtDaftarCatatan.text = builder.toString()
                }
            }
        }
    }
}