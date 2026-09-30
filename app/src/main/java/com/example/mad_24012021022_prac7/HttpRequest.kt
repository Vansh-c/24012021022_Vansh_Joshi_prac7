package com.example.mad_24012021022_prac7

import android.util.Log
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import com.example.mad_24012021022_prac7.Person

class HttpRequest {
    val TAG = "HttpRequest"

    fun makeServiceCall(reqUrl: String, token: String): String? {
        var response: String? = null
        try {
            val url = URL(reqUrl)
            val conn = url.openConnection() as HttpsURLConnection
            conn.setRequestProperty("Authorization", "Bearer $token")
            conn.setRequestProperty("Content-Type", "application/json")
            conn.connectTimeout = 10000
            conn.readTimeout = 10000
            response = convertStreamToString(
                BufferedInputStream(conn.inputStream)
            )
        } catch (e: Exception) {
            Log.e(TAG, "makeServiceCall error: ${e.message}")
        }
        return response
    }

    private fun convertStreamToString(iss: BufferedInputStream): String? {
        val reader = BufferedReader(InputStreamReader(iss))
        val sb = StringBuilder()
        var line: String?
        try {
            while (reader.readLine().also { line = it } != null) {
                sb.append(line).append("\n")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                iss.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return sb.toString()
    }
}