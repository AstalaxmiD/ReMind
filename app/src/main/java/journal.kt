package com.example.remind

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.core.content.edit
import java.text.SimpleDateFormat
import java.util.*

class JournalFragment : Fragment() {

    private lateinit var noteInput: EditText
    private lateinit var addNoteButton: Button
    private lateinit var notesList: ListView

    companion object {
        var notes = ArrayList<Note>()
        lateinit var adapter: ArrayAdapter<String>
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.journal_activity, container, false)

        noteInput = view.findViewById(R.id.noteInput)
        addNoteButton = view.findViewById(R.id.addNoteButton)
        notesList = view.findViewById(R.id.notesList)

        val prefs = requireActivity().getSharedPreferences("com.example.remind", Context.MODE_PRIVATE)

        // Load saved notes
        val set = prefs.getStringSet("notes", null)
        if (set != null) {
            notes = ArrayList(set.map { Note.fromStoredString(it) })
        }

        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, notes.map { it.toDisplayString() })
        notesList.adapter = adapter

        // Add new note
        addNoteButton.setOnClickListener {
            val noteText = noteInput.text.toString().trim()
            if (noteText.isNotEmpty()) {
                val date = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())
                val note = Note(noteText, date)
                notes.add(note)
                refreshAdapter()
                noteInput.text.clear()
                saveNotes(prefs)
            }
        }

        // Long press to delete note
        notesList.setOnItemLongClickListener { _, _, position, _ ->
            notes.removeAt(position)
            refreshAdapter()
            saveNotes(prefs)
            true
        }

        return view
    }

    private fun saveNotes(prefs: android.content.SharedPreferences) {
        val set = notes.map { it.toStoredString() }.toSet()
        prefs.edit {
            putStringSet("notes", set)
        }
    }

    private fun refreshAdapter() {
        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, notes.map { it.toDisplayString() })
        notesList.adapter = adapter
    }
}

// Data class for notes
data class Note(val text: String, val date: String) {
    fun toDisplayString(): String = "$date\n$text"
    fun toStoredString(): String = "$date||$text"

    companion object {
        fun fromStoredString(stored: String): Note {
            val parts = stored.split("||", limit = 2)
            return if (parts.size == 2) Note(parts[1], parts[0]) else Note(stored, "")
        }
    }
}
