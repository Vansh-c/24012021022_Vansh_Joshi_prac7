package com.example.mad_24012021022_prac7

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mad_24012021022_prac7.Person

class AddContact : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update_contact)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.updatecontactac)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.btnSave).setOnClickListener {
            val id = findViewById<EditText>(R.id.editId).text.toString().trim()
            val name = findViewById<EditText>(R.id.editName).text.toString().trim()
            val phone = findViewById<EditText>(R.id.editPhone).text.toString().trim()
            val address = findViewById<EditText>(R.id.editAddress).text.toString().trim()
            val email = findViewById<EditText>(R.id.editEmail).text.toString().trim()
            val latitude = findViewById<EditText>(R.id.editLatitude).text.toString().toDoubleOrNull() ?: 0.0
            val longitude = findViewById<EditText>(R.id.editLongitude).text.toString().toDoubleOrNull() ?: 0.0

            if (id.isEmpty() || name.isEmpty()) {
                Toast.makeText(this, "Please enter ID and Name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val person = Person(
                id = id,
                name = name,
                emailId = email,
                phoneNo = phone,
                address = address,
                latitude = latitude,
                longitude = longitude
            )

            val db = DBHelper(this)
            val result = db.insertPerson(person)
            if (result != -1L) {
                Toast.makeText(this, "Contact Saved Successfully!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Failed to save contact", Toast.LENGTH_SHORT).show()
            }

            finish() // Simply finish to go back to MainActivity
        }
    }
}