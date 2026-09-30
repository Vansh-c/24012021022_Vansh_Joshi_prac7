package com.example.mad_24012021022_prac7

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VER) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableSQLQuery = "CREATE TABLE $TABLE_CONTACT (" +
                "$KEY_ID TEXT PRIMARY KEY, " +
                "$KEY_NAME TEXT, " +
                "$KEY_PHONE TEXT, " +
                "$KEY_GMAIL TEXT, " +
                "$KEY_ADDRESS TEXT, " +
                "$KEY_LATITUDE REAL, " +
                "$KEY_LONGITUDE REAL)"
        db?.execSQL(createTableSQLQuery)
        Log.i("DBHelper", "Database table created successfully")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACT")
        onCreate(db)
    }

    companion object {
        private const val DB_NAME = "ContactDatabase.db"
        private const val DB_VER = 3 // Upgraded version to guarantee fresh table schema
        private const val TABLE_CONTACT = "contacts"
        private const val KEY_ID = "id"
        private const val KEY_NAME = "name"
        private const val KEY_PHONE = "phone_no"
        private const val KEY_GMAIL = "gmail"
        private const val KEY_ADDRESS = "address"
        private const val KEY_LATITUDE = "latitude"
        private const val KEY_LONGITUDE = "longitude"
    }

    fun insertPerson(contact: Person): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(KEY_ID, contact.id)
            put(KEY_NAME, contact.name)
            put(KEY_PHONE, contact.phoneNo)
            put(KEY_GMAIL, contact.emailId)
            put(KEY_ADDRESS, contact.address)
            put(KEY_LATITUDE, contact.latitude)
            put(KEY_LONGITUDE, contact.longitude)
        }
        val result = db.insertWithOnConflict(TABLE_CONTACT, null, values, SQLiteDatabase.CONFLICT_REPLACE)
        Log.i("DBHelper", "Inserted contact: ${contact.name} with result: $result")
        db.close()
        return result
    }

    fun getContactByName(id: String): Person? {
        val db = readableDatabase
        var contact: Person? = null
        try {
            val cursor = db.query(
                TABLE_CONTACT,
                null,
                "$KEY_ID = ?",
                arrayOf(id),
                null, null, null
            )
            if (cursor != null && cursor.moveToFirst()) {
                contact = Person(
                    id = cursor.getString(cursor.getColumnIndexOrThrow(KEY_ID)),
                    name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME)),
                    phoneNo = cursor.getString(cursor.getColumnIndexOrThrow(KEY_PHONE)),
                    emailId = cursor.getString(cursor.getColumnIndexOrThrow(KEY_GMAIL)),
                    address = cursor.getString(cursor.getColumnIndexOrThrow(KEY_ADDRESS)),
                    latitude = cursor.getDouble(cursor.getColumnIndexOrThrow(KEY_LATITUDE)),
                    longitude = cursor.getDouble(cursor.getColumnIndexOrThrow(KEY_LONGITUDE))
                )
                cursor.close()
            }
        } catch (e: Exception) {
            Log.e("DBHelper", "getContactByName error", e)
        }
        return contact
    }

    fun deletePerson(person: Person): Int {
        val db = writableDatabase
        val result = db.delete(TABLE_CONTACT, "$KEY_ID = ?", arrayOf(person.id))
        Log.i("DBHelper", "Deleted id: ${person.id}, result: $result")
        db.close()
        return result
    }

    fun allperson(): ArrayList<Person> {
        val db = readableDatabase
        val personlist = ArrayList<Person>()
        try {
            val cursor = db.rawQuery("SELECT * FROM $TABLE_CONTACT", null)
            Log.i("DBHelper", "Total rows in DB: ${cursor.count}")
            if (cursor.moveToFirst()) {
                do {
                    val person = Person(
                        id = cursor.getString(cursor.getColumnIndexOrThrow(KEY_ID)),
                        name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME)),
                        phoneNo = cursor.getString(cursor.getColumnIndexOrThrow(KEY_PHONE)),
                        emailId = cursor.getString(cursor.getColumnIndexOrThrow(KEY_GMAIL)),
                        address = cursor.getString(cursor.getColumnIndexOrThrow(KEY_ADDRESS)),
                        latitude = cursor.getDouble(cursor.getColumnIndexOrThrow(KEY_LATITUDE)),
                        longitude = cursor.getDouble(cursor.getColumnIndexOrThrow(KEY_LONGITUDE))
                    )
                    personlist.add(person)
                } while (cursor.moveToNext())
            }
            cursor.close()
        } catch (e: Exception) {
            Log.e("DBHelper", "allperson error", e)
        }
        Log.i("DBHelper", "Returning list size: ${personlist.size}")
        return personlist
    }

    fun updateContact(contact: Person): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(KEY_NAME, contact.name)
            put(KEY_PHONE, contact.phoneNo)
            put(KEY_GMAIL, contact.emailId)
            put(KEY_ADDRESS, contact.address)
            put(KEY_LATITUDE, contact.latitude)
            put(KEY_LONGITUDE, contact.longitude)
        }
        val result = db.update(TABLE_CONTACT, values, "$KEY_ID = ?", arrayOf(contact.id))
        Log.i("DBHelper", "updateContact result: $result")
        db.close()
        return result
    }
}