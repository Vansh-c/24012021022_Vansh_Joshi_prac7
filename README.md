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
