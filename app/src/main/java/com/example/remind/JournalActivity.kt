package com.example.remind // Your package

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.remind.databinding.ActivityJournalBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date

class JournalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJournalBinding
    private lateinit var journalAdapter: JournalAdapter
    private val journalEntries = mutableListOf<JournalEntry>()

    // For SharedPreferences
    companion object {
        const val PREFS_JOURNAL = "JournalPrefs"
        const val KEY_JOURNAL_ENTRIES = "journalEntriesList"
    }
    private val gson = Gson() // For serializing/deserializing List of objects

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJournalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        loadJournalEntries() // Load entries when the activity starts

        binding.buttonSaveJournalEntry.setOnClickListener {
            saveNewEntry()
        }
    }

    private fun setupRecyclerView() {
        journalAdapter = JournalAdapter(journalEntries)
        binding.recyclerViewJournalEntries.apply {
            layoutManager = LinearLayoutManager(this@JournalActivity)
            adapter = journalAdapter
        }
    }

    private fun saveNewEntry() {
        val content = binding.editTextNewJournalEntry.text.toString().trim()
        if (content.isNotEmpty()) {
            val newEntry = JournalEntry(content = content, date = Date()) // Current date and time
            journalEntries.add(0, newEntry) // Add to the beginning for immediate display at top (before sorting)

            // Persist to SharedPreferences
            saveJournalEntriesToPrefs()

            // Update adapter (which will sort and refresh)
            journalAdapter.updateEntries(journalEntries.toList()) // Pass a copy
            binding.editTextNewJournalEntry.text.clear() // Clear input field
            Toast.makeText(this, "Entry saved!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Please write something.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveJournalEntriesToPrefs() {
        val sharedPreferences = getSharedPreferences(PREFS_JOURNAL, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val jsonEntries = gson.toJson(journalEntries) // Convert list to JSON string
        editor.putString(KEY_JOURNAL_ENTRIES, jsonEntries)
        editor.apply()
        Log.d("JournalActivity", "Saved to Prefs: $jsonEntries")
    }

    private fun loadJournalEntries() {
        val sharedPreferences = getSharedPreferences(PREFS_JOURNAL, MODE_PRIVATE)
        val jsonEntries = sharedPreferences.getString(KEY_JOURNAL_ENTRIES, null)
        Log.d("JournalActivity", "Loaded from Prefs: $jsonEntries")

        if (jsonEntries != null) {
            val type = object : TypeToken<MutableList<JournalEntry>>() {}.type
            try {
                val loadedEntries: MutableList<JournalEntry> = gson.fromJson(jsonEntries, type)
                journalEntries.clear()
                journalEntries.addAll(loadedEntries)
            } catch (e: Exception) {
                Log.e("JournalActivity", "Error loading entries from JSON", e)
                // Handle error, maybe clear corrupted prefs
                // getSharedPreferences(PREFS_JOURNAL, Context.MODE_PRIVATE).edit().remove(KEY_JOURNAL_ENTRIES).apply()
            }
        }
        // Update adapter (which will sort and refresh)
        journalAdapter.updateEntries(journalEntries.toList()) // Pass a copy
    }
}
