package com.example.mad_24012021022_prac7

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray

class MainActivity : AppCompatActivity() {
    var personlist = ArrayList<Person>()
    lateinit var db: DBHelper
    lateinit var personRecyleAdapter: ContactAdapter
    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        db = DBHelper(this)
        personRecyleAdapter = ContactAdapter(personlist, this)

        val recyclerView = findViewById<RecyclerView>(R.id.r_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = personRecyleAdapter

        // 1. API Fetch Button
        findViewById<Button>(R.id.button3).setOnClickListener {
            Toast.makeText(this, "Fetching from API...", Toast.LENGTH_SHORT).show()
            networkDb()
        }

        // 2. Local Contacts Button
        findViewById<Button>(R.id.localcontact).setOnClickListener {
            loadLocalContacts()
        }

        // 3. Add Contact Button
        findViewById<Button>(R.id.addcontact).setOnClickListener {
            startActivity(Intent(this, AddContact::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        // Automatically reloads database records whenever you come back to this screen
        loadLocalContacts()
    }

    private fun loadLocalContacts() {
        val list = db.allperson()
        personlist.clear()
        personlist.addAll(list)
        personRecyleAdapter.notifyDataSetChanged()
        Toast.makeText(this, "Loaded ${list.size} contacts", Toast.LENGTH_SHORT).show()
    }

    fun getPersonData(data: String) {
        Log.d(TAG, "API DATA = $data")
        try {
            val joinArray = JSONArray(data)
            for (i in 0 until joinArray.length()) {
                val person = Person(joinArray.getJSONObject(i))
                db.insertPerson(person)
            }
            loadLocalContacts()
        } catch (e: Exception) {
            Log.e(TAG, "getPersonData error", e)
            Toast.makeText(this, "JSON Parse Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun networkDb() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val data = HttpRequest().makeServiceCall(
                    "https://api.json-generator.com/templates/5rDXHcbgpo93/data",
                    "d7wrtfqywyhu7y2bcbsz3cgjpbfisuhnmbibvgvf"
                )
                withContext(Dispatchers.Main) {
                    if (!data.isNullOrEmpty()) {
                        getPersonData(data)
                    } else {
                        Toast.makeText(this@MainActivity, "API token expired or no internet", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Network error", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Network error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}