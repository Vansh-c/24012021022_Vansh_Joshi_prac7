# 📱 Contact Manager & API Sync (Practical 7)

An Android application developed in Kotlin that demonstrates network API consumption (JSON parsing via background coroutines) and local offline persistence using an SQLite database with full CRUD (Create, Read, Update, Delete) operations.

---

## 🎯 Practical Objective / Aim
> **Aim:** Develop an Android application that retrieves person data in JSON format from an internet API and stores the retrieved data in an SQLite database.

---

## ✨ Features
- **🌐 Remote API Fetching:** Fetches live JSON contact datasets from a remote REST endpoint using `HttpsURLConnection` inside Kotlin Coroutines (`Dispatchers.IO`).
- **💾 Local SQLite Database (CRUD):**
  - **Create:** Add new contacts with custom ID, Name, Phone, Email, Address, Latitude, and Longitude.
  - **Read:** Display all stored local contacts in a scrollable `RecyclerView`.
  - **Update:** Edit existing contact details with a dedicated update screen.
  - **Delete:** Remove contacts directly from the database and UI via a floating action button.
- **🎨 Material 3 UI:** Clean user interface with `MaterialCardView`, `RecyclerView`, and `ConstraintLayout`.
- **⚡ Coroutine Concurrency:** Network operations run asynchronously in background threads without blocking the main UI thread.

---

## 🏗️ Architecture & Component Overview


---

## 🗄️ SQLite Database Schema

| Column Name | Data Type | Constraint | Description |
|---|---|---|---|
| `id` | `TEXT` | `PRIMARY KEY` | Unique Contact ID |
| `name` | `TEXT` | | Full Name |
| `phone_no` | `TEXT` | | Contact Phone Number |
| `gmail` | `TEXT` | | Email Address |
| `address` | `TEXT` | | Residential Address |
| `latitude` | `REAL` | | Geolocation Latitude |
| `longitude` | `REAL` | | Geolocation Longitude |

---

## 🚀 How to Run the Project

1. **Clone or Open in Android Studio:**
   - Open Android Studio and select **Open** ➔ choose the project directory.
2. **Sync Gradle:**
   - Allow Android Studio to sync dependencies and build tools.
3. **Permissions:**
   - Ensure `<uses-permission android:name="android.permission.INTERNET"/>` is present in `AndroidManifest.xml`.
4. **Run on Emulator / Device:**
   - Click **Run (`Shift + F10`)** or press the green Play button.

---

## 📱 How to Use the App

1. **Fetch from API:** Tap **`API Fetch`** to download remote contact data and automatically populate the local SQLite database.
2. **View Local Contacts:** Tap **`Local Contacts`** to load and display all records currently stored in SQLite.
3. **Add Contact:** Tap **`Add Contact`**, fill in the fields, and tap **`Save`**.
4. **Edit Contact:** Tap the **`Edit`** button on any card to modify its details.
5. **Delete Contact:** Tap the **`🗑️ Delete`** floating action button on any card to delete it permanently from SQLite.

---

## 🛠️ Built With
- **Language:** [Kotlin](https://kotlinlang.org/)
- **IDE:** [Android Studio](https://developer.android.com/studio)
- **Database:** SQLite (`SQLiteOpenHelper`)
- **Networking:** `HttpsURLConnection` + Kotlin Coroutines


