package com.example.mad_24012021022_prac7

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class UpdateContact : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update_contact)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.updatecontactac)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val db = DBHelper(this)
        val id = intent.getStringExtra("id") ?: ""
        val person = db.getContactByName(id)

        val etId = findViewById<EditText>(R.id.editId)
        val etName = findViewById<EditText>(R.id.editName)
        val etPhone = findViewById<EditText>(R.id.editPhone)
        val etAddress = findViewById<EditText>(R.id.editAddress)
        val etEmail = findViewById<EditText>(R.id.editEmail)
        val etLat = findViewById<EditText>(R.id.editLatitude)
        val etLong = findViewById<EditText>(R.id.editLongitude)

        etId.setText(person?.id ?: "")
        etId.isEnabled = false // Primary key should not be edited
        etName.setText(person?.name ?: "")
        etPhone.setText(person?.phoneNo ?: "")
        etAddress.setText(person?.address ?: "")
        etEmail.setText(person?.emailId ?: "")
        etLat.setText(person?.latitude?.toString() ?: "0.0")
        etLong.setText(person?.longitude?.toString() ?: "0.0")

        findViewById<Button>(R.id.btnSave).setOnClickListener {
            val updatedPerson = Person(
                id = id,
                name = etName.text.toString().trim(),
                emailId = etEmail.text.toString().trim(),
                phoneNo = etPhone.text.toString().trim(),
                address = etAddress.text.toString().trim(),
                latitude = etLat.text.toString().toDoubleOrNull() ?: 0.0,
                longitude = etLong.text.toString().toDoubleOrNull() ?: 0.0
            )

            db.updateContact(updatedPerson)
            Toast.makeText(this, "Updated Successfully!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}